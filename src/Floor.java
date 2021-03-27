import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

 /* Implements the floor class which represents the floors of the system.
 * 
 * @author Group 5
 * @version 2021-03-13
 */
public class Floor implements Runnable {
	private DatagramPacket receivedPacket;
	private DatagramPacket ackPacket;
	private ArrayList<DatagramPacket> responses;
	private int floor;
	private int port;


	/**
	 * The Floor constructor initializes an instance of Floor
	 */
	public Floor(int floor,int port) {	
		this.floor=floor;
		this.port=port;
	}

	/**
	 * The run method initiates thread execution. In this thread, the Floor reads a
	 * text file and passes parsed requests to the Controller so that it can be
	 * accessed by the Scheduler thread.
	 */
	public void run() {
		
		
		
		 ArrayList<PersonRequest> dataLines = readFile();
			
		
		
		try {
			int numberOfSuccessfulPackets = 0;
			DatagramSocket socket = new DatagramSocket(port); // Creates a new socket. This will be used for sending and recieving packets
			InetAddress local = InetAddress.getLocalHost(); // Gets the local address of the computer

			for (PersonRequest request : dataLines) {

				byte[] dataArray = generateByteArray(request);

				DatagramPacket packetToSend = new DatagramPacket(dataArray, dataArray.length, local, 23); // Creates a packet from the dataArray, to be sent to the intermediate host.
				DatagramPacket replyPacket = new DatagramPacket(new byte[21], 21); // Creates a packet to recieve the acknowledgement in.
				printPacket(packetToSend, true); // Prints the contents of the packet to be sent
				socket.send(packetToSend); // Sends the packetToSend
				socket.receive(replyPacket); // Receive the ack from the intermediate host
				printPacket(replyPacket, false);
				boolean receieved = false; // defines a flag to check for receieving a actual packet vs a nothing to report packet ("null")
				DatagramPacket receivedPacket = new DatagramPacket(new byte[20], 20); // Creates a new packet for receiving
			}
			// get requests from the scheduler
			receivedPacket = new DatagramPacket(new byte[17], 17);
			socket.receive(receivedPacket);// Receive a packet
			// System.out.println(receivedPacket.getData());
			printPacket(receivedPacket, false);
			responses.add(receivedPacket);
			
			byte[] ackData = "ack".getBytes();
			ackPacket = new DatagramPacket(ackData, ackData.length, local, receivedPacket.getPort());
			socket.send(ackPacket);// acknowledge that packet
			
			socket.close(); // Close the socket
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		
		
		}

		
		
		
	

	/**
	 * Parses a text file and creates an ArrayList of PersonRequest objects
	 * 
	 * @return and ArrayList of PersonRequest objects
	 */
	public ArrayList<PersonRequest> readFile() {
		ArrayList<PersonRequest> dataLines = new ArrayList<>();
		try {
			File file = new File("input.txt");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				PersonRequest ah = 	parseLine(line);
				if(ah.getFloor()==floor) {
					dataLines.add(ah);
					
			
				}
				
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
	 * @return a PersonRequest object that is parsed from a line from the input
	 *         file, representing a single request
	 */
	public PersonRequest parseLine(String line) {
		// System.out.println(line);

		// Split the line into an array of substrings
		// Each substring is parsed below
		String[] elements = line.split(" ");

		// Parse the date substring into an array of floats {<hours>, <minutes>, <seconds>}
		String time_string = elements[0];
		String[] time_string_array = time_string.split(":");
		float[] time = new float[3];
		time[0] = Float.parseFloat(time_string_array[0]);
		time[1] = Float.parseFloat(time_string_array[1]);
		time[2] = Float.parseFloat(time_string_array[2]);

		// The second substring represents the floor number
		String floor_string = elements[1];
		int floor = Integer.parseInt(floor_string);

		// The third substring is either "Up" or "Down" and corresponds to boolean values of 1 or 0 respectively
		String isUp_string = elements[2];
		boolean isUp = true;
		if (isUp_string.equals("Up")) {
			isUp = true;
		} else if (isUp_string.equals("Down")) {
			isUp = false;
		} else {
			System.out.println("ERROR: Failed to parse file!!!");
		}

		// The fourth substring represents the car button pressed i.e. the floor that the passenger wants to go to
		String carButton_string = elements[3];
		int carButton = Integer.parseInt(carButton_string);
		
		String error_String = elements[4];
		int error = Integer.parseInt(error_String);

		PersonRequest nextLine = new PersonRequest(time, floor, isUp, carButton, error);

		return nextLine;
	}


	/**
	 * This method prints the information in recievedPacket, formatted according to
	 * if it was sent or recieved
	 * 
	 * @param receivedPacket takes in the packet to be printed
	 * @param sending        Boolean value that indicates if the packet is to be
	 *                       sent, or was recieved
	 */

	public void printPacket(DatagramPacket receivedPacket, boolean sending) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
		if (!sending) { // If the packet was recieved
			System.out.println(java.time.LocalTime.now().format(dtf)+ "Floor" + floor +":"+ "Received the following packet (String): " + new String(receivedPacket.getData()));
			//System.out.println("Recived the following packet (Bytes): "); // but this is what the assignment said to do)
			for (int z = 0; z < receivedPacket.getData().length - 1; z++) { // Prints the byte array one index at a time
				System.out.print(receivedPacket.getData()[z] + ", ");
			}
			System.out.println(receivedPacket.getData()[receivedPacket.getData().length - 1]);
			System.out.println("From:" + receivedPacket.getAddress() + " on port: " + receivedPacket.getPort());
			System.out.println(""); // Adds a newline between packet sending and receiving
		} else { // The packet is being sent
			System.out.println(java.time.LocalTime.now().format(dtf)+ "Floor" + floor + ": Sending the following packet (String): " + new String(receivedPacket.getData()));
			//System.out.println("Sending the following packet (Bytes): "); // but this is what the assignment said to do)
			for (int z = 0; z < receivedPacket.getData().length - 1; z++) { // Prints the byte array one index at a time
				System.out.print(receivedPacket.getData()[z] + ", ");
			}
			System.out.println(receivedPacket.getData()[receivedPacket.getData().length - 1]);
			System.out.println("To:" + receivedPacket.getAddress() + " on port: " + receivedPacket.getPort());
			System.out.println(""); // Adds a newline between packet sending and receiving
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

	/**
	 * Parse config file to determine amount of floors to create
	 * @return Number of floors needed
	 * @throws FileNotFoundException
	 */
	public static int parseConfig() throws FileNotFoundException {
		ArrayList<String> configLines = new ArrayList<>();

		File file = new File("building.config.txt");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			configLines.add(line);
		}
		scanner.close();
		String[] splitLine = configLines.get(4).split(" ");
		return Integer.parseInt(splitLine[1]);
	}


	public static void main(String[] args) throws FileNotFoundException {
		int floorCount = parseConfig();

		for(int i = 1; i <= floorCount; i++){
			(new Thread(new Floor(i, 4999+i), "Floor " + i)).start();
		}
	}
}
