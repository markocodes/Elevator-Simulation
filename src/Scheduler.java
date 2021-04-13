import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements the scheduler which facilitates communication between floors and
 * elevators and dispatches elevators to complete requests.
 *
 * @author Group 5
 * @version 2021-03-27
 */

public class Scheduler implements Runnable {

	private State currentState;

	enum State {
		WAIT_FOR_FLOOR_REQUEST, SCHEDULING, WAIT_FOR_ELEVATOR_COMPLETION, SENDING_REQUEST_TO_FLOOR,
	}

	private DatagramSocket receiveSocket;
	private Queue<DatagramPacket> queue;
	private InetAddress local;
	private DatagramPacket receivedPacket, receivedResponsePacket, ackPacket;
	private int elevatorResponses = -1;
	private ArrayList<PersonRequest> requests = null;
	private static ArrayList<ArrayList<Integer>> floors = new ArrayList<ArrayList<Integer>>();
	private static ArrayList<ArrayList<Integer>> floorsInProgress = new ArrayList<ArrayList<Integer>>();
	private static ArrayList<PersonRequest> unscheduledFloors = new ArrayList<PersonRequest>();
	private static int numElevators;
	private static int currentFloors[] = { 1, 1, 1, 1 };
	private static ArrayList<String> elevatorDirection = new ArrayList<String>();
	private static int elevatorError[] = { 0, 0, 0, 0 };
	private static int elevatorStuck[] = { 0, 0, 0, 0 };
	private static boolean started = false;
	private static boolean ended = false;
	private static long startTime = 0;
	private static long endTime = 0;
	private static long timeElapsed;

	/**
	 * The Floor constructor initializes an instance of Scheduler and initializes
	 * necessary fields.
	 */
	public Scheduler(int portNumber, String name) {
		queue = new LinkedList<>();
		floors.add(new ArrayList<>());
		floors.add(new ArrayList<>());
		floors.add(new ArrayList<>());
		floors.add(new ArrayList<>());
		floorsInProgress.add(new ArrayList<>());
		floorsInProgress.add(new ArrayList<>());
		floorsInProgress.add(new ArrayList<>());
		floorsInProgress.add(new ArrayList<>());
		elevatorDirection.add("NA");
		elevatorDirection.add("NA");
		elevatorDirection.add("NA");
		elevatorDirection.add("NA");
		numElevators = 4;

		if (portNumber == 23) {
			currentState = State.WAIT_FOR_FLOOR_REQUEST;
		} else if (portNumber == 22) {
			currentState = State.WAIT_FOR_ELEVATOR_COMPLETION;
		}

		try {
			receiveSocket = new DatagramSocket(portNumber);// Initialize a Datagram socket
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
			System.out.println(java.time.LocalTime.now().format(dtf) + " " + name + " is running on port: " + portNumber);
		} catch (SocketException se) {
			// The program exits if socket creation failed
			se.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		if (currentState == State.WAIT_FOR_FLOOR_REQUEST) {
			floorToElevatorFSM();
		} else if (currentState == State.WAIT_FOR_ELEVATOR_COMPLETION) {
			elevatorToFloorFSM();
		}
	}

	/**
	 * Implements the state cycle that occurs when there is communication going from
	 * the floor to elevator.
	 */
	public void floorToElevatorFSM() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
		try {
			local = InetAddress.getLocalHost(); // Creates inetaddress containing localhost
			byte[] ackData = "ack".getBytes(); // Defines ack byte array
			byte[] negAck = "NA".getBytes();
			int currentElevator = 0;
			while (true) {
				if (currentState == State.WAIT_FOR_FLOOR_REQUEST) {
					// get requests from the floor
					receivedPacket = new DatagramPacket(new byte[18], 18);
					receiveSocket.receive(receivedPacket);// Receive a packet
					// printPacket(receivedPacket, false);
					if (new String(receivedPacket.getData()).trim().equals("request")) { // If the receivedPacket was a
						// request
						if (queue.isEmpty()) { // If there are no packets to forward
							ackPacket = new DatagramPacket(negAck, negAck.length, local, receivedPacket.getPort());
							// printPacket(ackPacket, true);
							receiveSocket.send(ackPacket);// acknowledge that packet
						} else {
							System.out.println(Thread.currentThread().getName() + ": Request Receieved");
							// printPacket(queue.peek(), true);
							receiveSocket.send(queue.remove()); // Send the first packet waiting
						}
					} else { // if the receivedPacket was not a request, it must have been data
						ackPacket = new DatagramPacket(ackData, ackData.length, local, receivedPacket.getPort());
						printPacket(ackPacket, true);
						receiveSocket.send(ackPacket);// acknowledge that packet

						// Add another request to the list of floor that need to be assigned to
						// elevators
						unscheduledFloors.add(parseLine((new String(receivedPacket.getData()))));
						if(!started) {
							started = true;
							startTime = System.nanoTime();
						}
					}
					if (unscheduledFloors.isEmpty()) {
						continue;
					}
					//System.out.println("Request obtained by Scheduler Thread!" );
					currentState = State.SCHEDULING;
				} else if (currentState == State.SCHEDULING) {
					// Determine the optimal sequence of floors to visit
					String direction = "NA";
					int currentFloor, destinationFloor, elevError;
					// Find an elevator that is available to handle the new request (and all other
					// unscheduled requests)
					ArrayList<PersonRequest> scheduledElements = new ArrayList<>();
					for (PersonRequest request : unscheduledFloors) {
						System.out.println(request.toString());
						currentFloor = request.getFloor();
						destinationFloor = request.getCarButton();
						elevError = request.getError();
						if (request.isU_d()) {
							direction = "Up";
						} else {
							direction = "Down";
						}
						for (int i = 0; i < numElevators; i++) {
							if (elevatorStuck[i] != 1) {
								if (elevatorDirection.get(i).equals("NA")) {
									// add the request to this elevator
									floors.get(i).add(currentFloor);
									floors.get(i).add(destinationFloor);
									elevatorError[i] = elevError;
									//System.out.println(floors);
									elevatorDirection.set(i, direction);
									scheduledElements.add(request);
									break;
								} else if (direction.equals(elevatorDirection.get(i))) {
									// Check if the elevator is going in
									if (elevatorDirection.get(i).equals("Up")) {
										// Now check if the elevator has already passed the currentFloor
										if (currentFloor > currentFloors[i]) {
											if (!floors.get(i).contains(currentFloor)) {
												floors.get(i).add(currentFloor);
											}
											if (!floors.get(i).contains(destinationFloor)) {
												floors.get(i).add(destinationFloor);
											}
											elevatorError[i] = elevError;
											elevatorDirection.set(i, direction);
											Collections.sort(floors.get(i));
											scheduledElements.add(request);
											break;
										}
									} else if (elevatorDirection.get(i).equals("Down")) {
										// Now check if the elevator has already passed the currentFloor
										if (currentFloor < currentFloors[i]) {
											floors.get(i).add(currentFloor);
											floors.get(i).add(destinationFloor);
											elevatorError[i] = elevError;
											elevatorDirection.set(i, direction);
											Collections.reverse(floors.get(i));
											scheduledElements.add(request);
											break;
										}
									}
								}
							}
						}
					}
					for (PersonRequest request : scheduledElements) {
						unscheduledFloors.remove(request);
					}
					System.out.println("Floors To visit: " + floors);
					currentState = State.WAIT_FOR_FLOOR_REQUEST;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Implements the state cycle that occurs when there is communication from the
	 * elevator to the floor.
	 */
	public void elevatorToFloorFSM() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
		try {
			local = InetAddress.getLocalHost(); // Creates inetaddress containing localhost
			byte[] ackData = "ack".getBytes(); // Defines ack byte array
			byte[] negAck = "NA".getBytes();
			int floorSensor;
			DatagramPacket responsePacket;
			while (true) {
				int whichQueue = 0;
				if (currentState == State.WAIT_FOR_ELEVATOR_COMPLETION) {

					// get response from elevator

					// if response is a request return data then send message to the floor -->
					// switch states here

					// get requests from the elevator

					receivedResponsePacket = new DatagramPacket(new byte[17], 17);
					receiveSocket.receive(receivedResponsePacket);// Receive a packet
					// printPacket(receivedResponsePacket, false);
					// printPacket(receivedResponsePacket, false);
					if (new String(receivedResponsePacket.getData()).trim().equals("request")) {
						if (receivedResponsePacket.getPort() == 24) {
							whichQueue = 0;
						} else if (receivedResponsePacket.getPort() == 25) {
							whichQueue = 1;
						} else if (receivedResponsePacket.getPort() == 26) {
							whichQueue = 2;
						} else if (receivedResponsePacket.getPort() == 27) {
							whichQueue = 3;
						}

						if (floors.get(whichQueue).isEmpty()) { // If there are no packets to forward
							elevatorDirection.set(whichQueue, "NA");
							ackPacket = new DatagramPacket(negAck, negAck.length, local,
									receivedResponsePacket.getPort());
							// printPacket(ackPacket, true);
							receiveSocket.send(ackPacket);// acknowledge that packet
						} else {
							StringBuilder stringReq = new StringBuilder();
							System.out.println(Thread.currentThread().getName() + ": Request Receieved");
							floorsInProgress.get(whichQueue).add(floors.get(whichQueue).get(0));
							stringReq.append(String.valueOf(floors.get(whichQueue).get(0)));
							stringReq.append(" ").append(elevatorError[whichQueue]);
							responsePacket = new DatagramPacket(stringReq.toString().getBytes(),
									stringReq.toString().getBytes().length, local, receivedResponsePacket.getPort());
							elevatorError[whichQueue] = 0;
							floors.get(whichQueue).remove(0);
							System.out.println("Sending response to elevator");
							printPacket(responsePacket, true);
							receiveSocket.send(responsePacket); // Send the first packet waiting
						}
					} else if (new String(receivedResponsePacket.getData()).trim().equals("Help")) {
						if (receivedResponsePacket.getPort() == 24) {
							whichQueue = 0;
						} else if (receivedResponsePacket.getPort() == 25) {
							whichQueue = 1;
						} else if (receivedResponsePacket.getPort() == 26) {
							whichQueue = 2;
						} else if (receivedResponsePacket.getPort() == 27) {
							whichQueue = 3;
						}
						elevatorStuck[whichQueue] = 1;
						ackPacket = new DatagramPacket(ackData, ackData.length, local,
								receivedResponsePacket.getPort());
						printPacket(ackPacket, true);
						receiveSocket.send(ackPacket);// acknowledge that packet
						System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + (whichQueue + 1)
								+ " is broken send help");
						floors.get(whichQueue).clear();
					} else if (new String(receivedResponsePacket.getData()).trim().equals("fixed")) {
						if (receivedResponsePacket.getPort() == 24) {
							whichQueue = 0;
						} else if (receivedResponsePacket.getPort() == 25) {
							whichQueue = 1;
						} else if (receivedResponsePacket.getPort() == 26) {
							whichQueue = 2;
						} else if (receivedResponsePacket.getPort() == 27) {
							whichQueue = 3;
						}
						floorsInProgress.get(whichQueue).clear();
						elevatorStuck[whichQueue] = 0;
						ackPacket = new DatagramPacket(ackData, ackData.length, local,
								receivedResponsePacket.getPort());
						printPacket(ackPacket, true);
						receiveSocket.send(ackPacket);// acknowledge that packet
						System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + (whichQueue + 1)
								+ " is fixed, can send requests again");
					} else {// if the receivedResponsePacket was not a request, it must have been data
						if (receivedResponsePacket.getPort() == 24) {
							whichQueue = 0;
						} else if (receivedResponsePacket.getPort() == 25) {
							whichQueue = 1;
						} else if (receivedResponsePacket.getPort() == 26) {
							whichQueue = 2;
						} else if (receivedResponsePacket.getPort() == 27) {
							whichQueue = 3;
						}

						// Determine whether or not the elevator should stop here
						floorSensor = Integer
								.parseInt((new String(receivedResponsePacket.getData())).replaceAll("[^\\d.]", ""));
						System.out.println(java.time.LocalTime.now().format(dtf) + "  Floor sensor triggered for floor "
								+ floorSensor + "!");
						System.out.println(java.time.LocalTime.now().format(dtf) + "  FloorSensor:  " + floorSensor);
						if (floorsInProgress.get(whichQueue).contains(floorSensor)) {
							ackData = "stop".getBytes();
							floorsInProgress.get(whichQueue)
									.remove(floorsInProgress.get(whichQueue).indexOf(floorSensor));
						System.out.println(floorsInProgress);
						} else {
							ackData = "no".getBytes();
						}
						ackPacket = new DatagramPacket(ackData, ackData.length, local,
								receivedResponsePacket.getPort());
						printPacket(ackPacket, true);
						receiveSocket.send(ackPacket);// acknowledge that packet
						queue.add(receivedResponsePacket); // Enqueue the packet
						currentFloors[whichQueue] = floorSensor;
						System.out.println(java.time.LocalTime.now().format(dtf) + " Requests obtained by Scheduler Thread!");
					}
					if (!ended) {
						if (started) {
							boolean floorsEmpty = true;
							boolean progressEmpty = true;
							if (unscheduledFloors.isEmpty()) {
								for (ArrayList<Integer> list : floors) {
									if (!list.isEmpty()) {
										floorsEmpty = false;
										break;
									}
								}
								if (floorsEmpty) {
									for (ArrayList<Integer> list : floorsInProgress) {
										if (!list.isEmpty()) {
											progressEmpty = false;
											break;
										}
									}
									if (progressEmpty) {
										endTime = System.nanoTime();
										timeElapsed = ((endTime - startTime) / 1000000); // in seconds
										double tempTime = (double)timeElapsed;
										System.out.println("\n########################################");
										System.out.println("\nTime to complete input file: " + String.format("%.02f",tempTime/1000) + " seconds");
										System.out.println("\n########################################");
										ended = true;
									}
								}
							}
						}
					}
					currentState = State.SENDING_REQUEST_TO_FLOOR;
				} else if (currentState == State.SENDING_REQUEST_TO_FLOOR) {
					// Send response to floor
					// System.out.println(java.time.LocalTime.now().format(dtf) + " Requests put by
					// Scheduler Thread!");
					currentState = State.WAIT_FOR_ELEVATOR_COMPLETION;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method prints the information in receivedPacket, formatted according to
	 * if it was sent or received
	 * 
	 * @param packet  takes in the packet to be printed
	 * @param sending Boolean value that indicates if the packet is to be sent, or
	 *                was received
	 */
	public void printPacket(DatagramPacket packet, boolean sending) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
		if (!sending) { // If the packet was received
			System.out.println(java.time.LocalTime.now().format(dtf) + " " + Thread.currentThread().getName()
					+ " : Received the following packet (String): " + new String(packet.getData())); 
			System.out.println("  Recived the following packet (Bytes): "); 
			for (int z = 0; z < packet.getData().length - 1; z++) { // Prints the byte array one index at a time
				System.out.print(packet.getData()[z] + ", ");
			}
			System.out.println(packet.getData()[packet.getData().length - 1]);
			System.out.println(" From:" + packet.getAddress() + " on port: " + packet.getPort());
			System.out.println(""); // Adds a newline between packet sending and receiving
		} else { // The packet is being sent
			System.out.println(Thread.currentThread().getName() + ": Sending the following packet (String): "
					+ new String(packet.getData()));// Print data as string (Binary values will not appear correctly in the string,
			System.out.println(java.time.LocalTime.now().format(dtf) + "  Sending the following packet (Bytes): ");
			for (int z = 0; z < packet.getData().length - 1; z++) { // Prints the byte array one index at a time
				System.out.print(packet.getData()[z] + ", ");
			}
			System.out.println(packet.getData()[packet.getData().length - 1]);
			System.out.println(" To:" + packet.getAddress() + " on port: " + packet.getPort());
			System.out.println(""); // Adds a newline between packet sending and receiving
		}
	}

	/**
	 * Parses a line of the input request file
	 * 
	 * @param line is a single unparsed line (String) from the input file
	 * @return a PersonRequest object that is parsed from a line from the input
	 *         file, representing a single request
	 */
	public PersonRequest parseLine(String line) {
		// Split the line into an array of substring. Each substring is parsed below
		String[] elements = line.split(" ");
		// Parse the date substring into an array of floats {<hours>, <minutes>,
		// <seconds>}
		String time_string = elements[0];
		String[] time_string_array = time_string.split(":");
		float[] time = new float[3];
		time[0] = Float.parseFloat(time_string_array[0]);
		time[1] = Float.parseFloat(time_string_array[1]);
		time[2] = Float.parseFloat(time_string_array[2]);

		// The second substring represents the floor number
		String floor_string = elements[1];
		int floor = Integer.parseInt(floor_string);

		// The third substring is either "Up" or "Down" and corresponds to boolean
		// values of 1 or 0 respectively
		String isUp_string = elements[2];
		boolean isUp = true;
		if (isUp_string.equals("Up")) {
			isUp = true;
		} else if (isUp_string.equals("Down")) {
			isUp = false;
		} else {
			System.out.println("ERROR: Failed to parse file!!!");
		}

		// The fourth substring represents the car button pressed
		// i.e. the floor that the passenger wants to go to
		String carButton_string = elements[3];
		int carButton = Integer.parseInt(carButton_string);

		String error_String = elements[4].replaceAll("[^\\d.]", "");
		int error = Integer.parseInt(error_String);

		PersonRequest nextLine = new PersonRequest(time, floor, isUp, carButton, error);

		return nextLine;
	}

	/**
	 * timeParse converts a float array to a printable string time
	 * 
	 * @param arr is a float array containing the time to be converted
	 * @return String printable time
	 */
	private String timeParse(float[] arr) {
		StringBuilder printObject = new StringBuilder();
		float[] time = arr;
		for (float elem : time) {
			if (!(elem == time[time.length - 1])) {
				printObject.append(String.valueOf((int) elem));
				printObject.append(":");
			} else {
				printObject.append(String.valueOf((int) elem));
			}
		}
		return printObject.toString();
	}

	public static void main(String[] args) {
		Thread scheduler_thread1, scheduler_thread2;
		scheduler_thread1 = new Thread(new Scheduler(23, "Scheduler Thread 1"), "Scheduler Thread 1");
		scheduler_thread2 = new Thread(new Scheduler(22, "Scheduler Thread 2"), "Scheduler Thread 2");
		System.out.println("\n");
		scheduler_thread1.start();
		scheduler_thread2.start();
	}
}
