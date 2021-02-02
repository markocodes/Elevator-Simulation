import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;  
import java.util.Scanner; 

public class Floor {

	public static void main(String[] args) {
		readFile();
	}
	//TODO REMOVE STATIC FROM THIS AND MAKE IT SYNCHRONIZED
	public static ArrayList<PersonRequest> readFile(){ //If deleting the contents of the file as well -> make this method synchronized
		ArrayList<PersonRequest> dataLines = new ArrayList<PersonRequest>(); 
		try {
			File file = new File("E:\\OneDrive - Carleton University\\Documents\\Riley\\Carleton Winter 2021\\SYSC 3303\\SYSC3303_Project\\src\\input.txt");
		    Scanner scanner = new Scanner(file);
		    while (scanner.hasNextLine()) {
		    	String line = scanner.nextLine();
		    	parseLine(line);
		    }
		    scanner.close();
		    } catch (FileNotFoundException e) {
		    	System.out.println("An error occurred.");
		    	e.printStackTrace();
		    }	
		
		return dataLines;
	}
	
	public static PersonRequest parseLine(String line) {
		System.out.println(line);
		
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
		System.out.println(nextLine.getTime()[0] + ":" + nextLine.getTime()[1] + ":" + nextLine.getTime()[2]);
		System.out.println(nextLine.getFloor());
		System.out.println(nextLine.isU_d());
		System.out.println(nextLine.getCarButton());

		
		return nextLine;
	}
	
	public void sendDataToScheduler(ArrayList<PersonRequest> dataLines) {
		
	}
}
