import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner; 

/**
 * Implements the floor class which represents the floors of the system.
 * 
 * @author Group 5
 * @version 2021-03-13
 */
public class Floor implements Runnable{

	/**
	 * The Floor constructor initializes an instance of Floor
	 */
	public Floor() {}

	/**
	 * The run method initiates thread execution.
	 * In this thread, the Floor reads a text file and passes parsed requests to the Controller so that it can be accessed by the Scheduler thread.
	 */
	public void run() {
		ArrayList<PersonRequest> dataLines = readFile();

		try {
			int numberOfSuccessfulPackets = 0;
			DatagramSocket socket = new DatagramSocket(5000); //Creates a new socket. This will be used for sending and recieving packets
			//			socket.setSoTimeout(5000); //Sets the timeout value to 5 seconds. If 5 seconds elapses and no packet arrives on receive, an exception will be thrown
			InetAddress local = InetAddress.getLocalHost(); //Gets the local address of the computer 


			for (PersonRequest request : dataLines) {

				byte[] dataArray = generateByteArray(request);

				DatagramPacket packetToSend = new DatagramPacket(dataArray, dataArray.length, local, 23); //Creates a packet from the dataArray, to be sent to the intermediate host
				DatagramPacket replyPacket = new DatagramPacket(new byte[20], 20); //Creates a packet to recieve the acknowledgement in.

				printPacket(packetToSend, true); //Prints the contents of the packet to be sent

				socket.send(packetToSend); //Sends the packetToSend
				socket.receive(replyPacket); //Receive the ack from the intermediate host
				printPacket(replyPacket, false);
				boolean receieved = false; //defines a flag to check for receieving a actual packet vs a nothing to report packet ("null")
				DatagramPacket receivedPacket = new DatagramPacket(new byte[20], 20); //Creates a new packet for receiving
//				byte[] requestByteArray = "request".getBytes(); //Convert "request" into a byte array to send
//				while (!receieved) { //Loop until a not null packet is recieved
//					DatagramPacket requestPacket = new DatagramPacket(requestByteArray, requestByteArray.length, local, 24);
//					socket.send(requestPacket); //Send a request to the intermediate server
//					//					printPacket(requestPacket, true);
//					socket.receive(receivedPacket); //Recieve the response
//					//					printPacket(receivedPacket, false);
//					if (!(new String(receivedPacket.getData()).trim().equals("NA"))) {//If the response is not null, ie. a actual response
//						printPacket(receivedPacket, false);
//						numberOfSuccessfulPackets++;
//						System.out.println("Floor has received " + numberOfSuccessfulPackets + " packets so far");
//						receieved = true; //Break out of loop
//					}
//
//					Thread.sleep(1000);
//				}
				//printPacket(receivedPacket, false); //Prints the packet recieved
			}
			socket.close(); //Close the socket
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses a text file and creates an ArrayList of PersonRequest objects 
	 * 
	 * @return and ArrayList of PersonRequest objects
	 */
	public  ArrayList<PersonRequest> readFile(){
		ArrayList<PersonRequest> dataLines = new ArrayList<>();
		try {
			File file = new File("src/input.txt");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				dataLines.add(parseLine(line));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}			
		return dataLines;
	}

	/**
	 * Parses a line of the input request file
	 * 
	 * @param line is a single unparsed line (String) from the input file
	 * 
	 * @return a PersonRequest object that is parsed from a line from the input file, representing a single request
	 */
	public PersonRequest parseLine(String line) {
		//System.out.println(line);

		//Split the line into an array of substrings
		//Each substring is parsed below
		String[] elements = line.split(" "); 

		//Parse the date substring into an array of floats {<hours>, <minutes>, <seconds>}
		String time_string = elements[0];
		String[] time_string_array = time_string.split(":");
		float[] time = new float[3];
		time[0] = Float.parseFloat(time_string_array[0]);
		time[1] = Float.parseFloat(time_string_array[1]);
		time[2] = Float.parseFloat(time_string_array[2]);

		//The second substring represents the floor number
		String floor_string = elements[1];
		int floor = Integer.parseInt(floor_string);

		//The third substring is either "Up" or "Down" and corresponds to boolean values of 1 or 0 respectively
		String isUp_string = elements[2];
		boolean isUp = true;
		if (isUp_string.equals("Up")) {
			isUp = true;
		} else if (isUp_string.equals("Down")) {
			isUp = true;
		} else {
			System.out.println("ERROR: Failed to parse file!!!");
		}

		//The fourth substring represents the car button pressed 
		//i.e. the floor that the passenger wants to go to
		String carButton_string = elements[3];
		int carButton = Integer.parseInt(carButton_string);

		PersonRequest nextLine = new PersonRequest(time, floor, isUp, carButton);
		//System.out.println(nextLine.getTime()[0] + ":" + nextLine.getTime()[1] + ":" + nextLine.getTime()[2]);
		//System.out.println(nextLine.getFloor());
		//System.out.println(nextLine.isU_d());
		//System.out.println(nextLine.getCarButton());

		return nextLine;
	}

	/**
	 * Write the data obtained from the input requests file to the Controller so that it can be read by the Scheduler thread
	 * 
	 * @param dataLines is the ArrayList of PersonRequest objects that are read and parsed from the input file
	 */
//	public void sendDataToScheduler(ArrayList<PersonRequest> dataLines) {
//		controller.putRequests(dataLines);
//		System.out.println("1. Requests put by Floor Thread!");
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {}
//	}



//	 public int getFromElevator() {
//		int responses = 0;
//		try {
//			responses = controller.getResponses();
//			System.out.println("8. Requests obtained by Floor Thread");
//            Thread.sleep(100);
//        } catch (InterruptedException e) {}
//		return responses;
//	}


	/**
	 * This method prints the information in recievedPacket, formatted according to if it was sent or recieved
	 * 
	 * @param receivedPacket takes in the packet to be printed
	 * @param sending        Boolean value that indicates if the packet is to be sent, or was recieved
	 */

	public static void printPacket(DatagramPacket receivedPacket, boolean sending) {
		if (!sending) { //If the packet was recieved
			System.out.println("Floor: Received the following packet (String): " + new String(receivedPacket.getData())); //Print data as string (Binary values will not appear correctly in the string, 
			System.out.println("Recived the following packet (Bytes): "); //but this is what the assignment said to do)
			for (int z = 0; z < receivedPacket.getData().length - 1; z++) { //Prints the byte array one index at a time
				System.out.print(receivedPacket.getData()[z] + ", ");
			}
			System.out.println(receivedPacket.getData()[receivedPacket.getData().length - 1]);
			System.out.println("From:" + receivedPacket.getAddress() + " on port: " + receivedPacket.getPort()); //Prints the address and port the packet was recieved on
			System.out.println(""); //Adds a newline between packet sending and receiving
		} else { //The packet is being sent
			System.out.println("Floor: Sending the following packet (String): " + new String(receivedPacket.getData()));//Print data as string (Binary values will not appear correctly in the string, 
			System.out.println("Sending the following packet (Bytes): "); //but this is what the assignment said to do)
			for (int z = 0; z < receivedPacket.getData().length - 1; z++) { //Prints the byte array one index at a time
				System.out.print(receivedPacket.getData()[z] + ", ");
			}
			System.out.println(receivedPacket.getData()[receivedPacket.getData().length - 1]);
			System.out.println("To:" + receivedPacket.getAddress() + " on port: " + receivedPacket.getPort()); //Prints the address and port the packet is being sent to
			System.out.println(""); //Adds a newline between packet sending and receiving

		}
	}

	/**
	 * Convert person request to an array of bytes
	 *
	 * @param req Request being sent
	 * @return request converted to array of bytes
	 */
	public static byte[] generateByteArray(PersonRequest req) {
		byte[] arr = req.toString().getBytes();
		return arr;
	}
	public static void main(String[] args) {
		Thread floor;
		floor = new Thread (new Floor(),"Floor");
		floor.start();
	
	}
}
