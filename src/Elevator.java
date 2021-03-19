import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;


/**
 * Implements the Elevator class which represents an elevator car in the system.
 *
 * @author Group 5
 * @version 2021-03-13
 */

public class Elevator implements Runnable{
	enum State {
		DOORCLOSED,
		DOOROPEN,
		MOVING,
		STOPPED
	}

	/**
	 * Shared controller instance, used as the medium to pass data between threads.
	 */

	private State currentState=State.DOOROPEN;
	
	private boolean up;
	private int response;
	private ArrayList<Integer> destination;
	private int currentFloor;
	private int port;
	
	/**
	 * The Floor constructor initializes an instance of Scheduler and assigns the shared Controller instance
	 */
	public Elevator(int curFloor,int port) {
		this.port = port;
		this.currentFloor = curFloor;
		this.up = false;
	}

	public State getCurrentState() {
		return currentState;
	}

	@Override
	public void run() {
		destination = new ArrayList<Integer>();
		try {
			DatagramSocket socket = new DatagramSocket(port);	//Creates socket bound to each elevators port
			while(true) {
			if (currentState == State.DOOROPEN) {
				byte[] requestByteArray = "request".getBytes();
				boolean receieved = false; //defines a flag to check for receieving a actual packet vs a nothing to report packet ("null")
				DatagramPacket recievedPacket = new DatagramPacket(new byte[17], 17);	//Creates a packet to recieve into
				DatagramPacket requestPacket = new DatagramPacket(requestByteArray, requestByteArray.length, InetAddress.getLocalHost(), 22);

				while(!receieved) {	//Loop until a non null packet is recieved
//					printPacket(requestPacket, true);
					socket.send(requestPacket);	//Send a request to the intermediate server
					socket.receive(recievedPacket);	//Receive the response
//					printPacket(recievedPacket, false);
					if(!(new String(recievedPacket.getData()).trim().equals("NA"))) {//If the response is not null, ie. a actual response
						receieved=true;	//Break out of loop
					}
					Thread.sleep(1000);
				}
				byte[] temp = recievedPacket.getData();
				int dest = Integer.parseInt((new String(temp)).replaceAll("[^\\d.]", ""));
				if(currentFloor < dest) {
//					System.out.println("From: "+dest);
//					System.out.println("To: "+currentFloor);
					up = true;
				}
				else if(currentFloor > dest) {
					up = false;
				}
				
				System.out.println("4. Requests obtained by Elevator Thread!");
				
					if (response != currentFloor) {
						destination.add(response);
					destination.sort(null);
				}
				currentState = State.DOORCLOSED;

				System.out.println("              Doors are closing ");

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (currentState == State.DOORCLOSED) {

				currentState = State.MOVING;
				System.out.println("              Elevator is moving");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (currentState == State.MOVING) {
				boolean stop = false;
				while(!stop) {	
					int destFloor = currentFloor;
					byte[] requestByteArray = String.valueOf(destFloor).getBytes();
					DatagramPacket recievedPacket = new DatagramPacket(new byte[17], 17);	//Creates a packet to recieve into
					DatagramPacket requestPacket = new DatagramPacket(requestByteArray, requestByteArray.length, InetAddress.getLocalHost(), 22);
					//Loop until a non null packet is received
//					printPacket(requestPacket, true);
					socket.send(requestPacket);	//Send a request to the intermediate server
					socket.receive(recievedPacket);	//Receive the response
//					printPacket(recievedPacket, false);
					if((new String(recievedPacket.getData()).trim().equals("stop"))) {//If the response is not null, ie. a actual response
						stop=true;	//Break out of loop
						break;
					}
					Thread.sleep(1000);
					if (up) {
						currentFloor++;
					}
					else if(!up) {
						currentFloor--;
					}
				}
				
				currentState = State.STOPPED;
				System.out.println("              Elevator has stopped");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (currentState == State.STOPPED) {
				//controller.putElevatorResponses(response);
				System.out.println("5. Requests put by Elevator Thread!");
				currentState = State.DOOROPEN;

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
		
			}
			}
		}catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
	
}
	
	/**
	 * This method prints the information in receivedPacket, formatted according to if it was sent or recieved
	 * @param receivedPacket takes in the packet to be printed
	 * @param sending Boolean value that indicates if the packet is to be sent, or was recieved
	 */
	public static void printPacket(DatagramPacket receivedPacket, boolean sending) {
		if(!sending) {	//If the packet was received
		System.out.println("Server: Received the following packet (String): " + new String(receivedPacket.getData())); //Print data as string (Binary values will not appear correctly in the string,
		System.out.println("Received the following packet (Bytes): ");											//but this is what the assignment said to do)
		for (int z = 0; z < receivedPacket.getData().length - 1; z++) {					//Prints the byte array one index at a time
			System.out.print(receivedPacket.getData()[z] + ", ");
		}
		System.out.println(receivedPacket.getData()[receivedPacket.getData().length - 1]);
		System.out.println("From:" + receivedPacket.getAddress() + " on port: "+ receivedPacket.getPort()); 	//Prints the address and port the packet was recieved on
		System.out.println("");	//Adds a newline between packet sending and receiving
		}else {			//The packet is being sent
			System.out.println("Server: Sending the following packet (String): " + new String(receivedPacket.getData()));//Print data as string (Binary values will not appear correctly in the string,
			System.out.println("Sending the following packet (Bytes): ");										//but this is what the assignment said to do)
			for (int z = 0; z < receivedPacket.getData().length - 1; z++) {			//Prints the byte array one index at a time
				System.out.print(receivedPacket.getData()[z] + ", ");
			}
			System.out.println(receivedPacket.getData()[receivedPacket.getData().length - 1]);
			System.out.println("To:" + receivedPacket.getAddress() + " on port: "+ receivedPacket.getPort());	//Prints the address and port the packet is being sent to
			System.out.println("");	//Adds a newline between packet sending and receiving

		}
	}
	

	public int getPort() {
		return port;
	}
	public static void main(String[] args) {
		
		Thread  elevator1, elevator2,elevator3,elevator4;
		elevator1 = new Thread (new Elevator(1,24),"Elevator");
		elevator2 = new Thread (new Elevator(1,25),"Elevator");
		elevator3 = new Thread (new Elevator(1,26),"Elevator");
		elevator4 = new Thread (new Elevator(1,27),"Elevator");
		elevator1.start();
		elevator2.start();
		elevator3.start();
		elevator4.start();
	}
	}


		
			
		
	


	
	