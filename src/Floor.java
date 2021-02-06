import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;  
import java.util.Scanner; 

/**
 * Instances of the Floor class are threads that represents floors of an elevator system
 * 
 * @author
 *
 */
public class Floor implements Runnable{

	/**
	 * Shared controller instance, used as the medium to pass data between threads
	 */
	private Controller controller;
	
	/**
	 * The Floor constructor initializes an instance of Floor and assigns the shared Controller instance
	 */
	public Floor(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * The run method initiates thread execution.
	 * In this thread, the Floor reads a text file and passes parsed requests to the Controller so that it can be accessed by the Scheduler thread.
	 */
	public void run() {
		ArrayList<PersonRequest> dataLines = readFile();
		
		System.out.println("Initial messages:");
		for (PersonRequest line : dataLines) {
			System.out.println(line.toString());
		}
		System.out.println("\n");
		
		sendDataToScheduler(dataLines);
		ArrayList<PersonRequest> responses = getFromElevator();
		
		System.out.println("\nReturned messages:");		
		for (PersonRequest line : responses) {
			System.out.println(line.toString());
		}
		System.out.println("");

		System.out.println("done");		
	}
	
	/**
	 * Parses a text file and creates an ArrayList of PersonRequest objects 
	 * 
	 * @return and ArrayList of PersonRequest objects
	 */
	public ArrayList<PersonRequest> readFile(){
		ArrayList<PersonRequest> dataLines = new ArrayList<PersonRequest>(); 
		try {
			File file = new File("./src/input.txt");
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
	public void sendDataToScheduler(ArrayList<PersonRequest> dataLines) {
		controller.putRequests(dataLines);
		System.out.println("1. Requests put by Floor Thread!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
	}
	
	public ArrayList<PersonRequest> getFromElevator() {
		ArrayList<PersonRequest> responses = null;
		try {
			responses = controller.getResponses();
			System.out.println("8. Requests obtained by Floor Thread");
            Thread.sleep(100);
        } catch (InterruptedException e) {}
		return responses;
	}
}
