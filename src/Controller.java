import java.util.*;

/**
 * Controller is the class that will deal with all the communications between the three threads: Floor, Scheduler and Elevator
 * It was send the array of PersonRequest objects around and signal booleans so threads know when it is their turn to execute
 * 
 * 
 * @author Group 5
 * @version February 6 2021
 *
 */

public class Controller {

	private boolean requestAvailable = false; //If there is a request from the patrons available this will become true
	private boolean elevatorFinished = false; //Once the elevator has finished its command this will become true
	private boolean arrivedAtFloor = false; //Once the scheduler has confirmed and sent the data to the floor at the end this will become true
	
	
	
	/**
	 *  putRequests, synchronized method that is called by floor and will check to see if the arraylist has been filled up or not and then notifies the scheduler 
	 *  and passes the arraylist to it
	 * 
	 * @param list this is the arraylist that is used to store the parsed text file
	 */
	
	
	public synchronized void putRequests(ArrayList<PersonRequest> list) {
        
		while(newRequest(list)) {
        try {
        	wait();
        }catch(InterruptedException e) {
        	System.err.println(e);
        	
        }
		}
        Schedule(list);
        System.out.println("ah");
		if(newRequest(list)) {
			notifyAll();
		}
        
	}
	
	/**
	 * newRequest, will check if the arraylist is empty or not
	 * 
	 * 
	 * @param list this is the arraylist that is used to store the parsed text file
	 * @return true if the arraylist is not empty return true
	 * @return false if the arraylist is empty return true
	 */
	private boolean newRequest(ArrayList<PersonRequest> list) {
		if(!list.isEmpty()) {
			return true;
			
		}
		return false;
	}
	
	
	/**
	 * Schedule, will go through the arraylist index by index and send them to the elevator
	 * 
	 * 
	 * @param list this is the arraylist that is used to store the parsed text file
	 */
	private void Schedule(ArrayList<PersonRequest> list) {
		for(PersonRequest Command:list) {
			notifyElevator(Command);
			
		}
		
	}
	
	public synchronized void schedulerToElevator() {
		
		while(requestAvailable==false) {
			try {
			wait();
		}catch(InterruptedException e) {}
			
			System.out.println("Elevator has been scheduled");
			requestAvailable=false;
			
			notifyAll();
		}
	}
	

public synchronized void ElevatorToScheduler() {
		
		while(elevatorFinished==false) {
			try {
				wait();
			
		
			
		}catch(InterruptedException e) {}
	}
		System.out.println("Elevator has been finished");
		elevatorFinished=false;
		notifyAll();
		
		
	}

public synchronized void SchedulerToFloor() {
	while(arrivedAtFloor==false) {
		try {
	
		wait();
	}catch(InterruptedException e) {}
		System.out.println("Elevator has arrived at the floor");
		arrivedAtFloor=false;
		notifyAll();
		
		
	
}
}

	
	
	
private void notifyElevator(PersonRequest Command) {
		
		if(!(Command == null)) {
			System.out.println("Elevator is sent " + Command.getTime() + Command.getFloor() + Command.isU_d() + Command.getCarButton());
			notifyScheduler(Command);
			
			requestAvailable = true;
			
		}
		else {
			requestAvailable = false;
		}
		
		
	}
	private void notifyScheduler(PersonRequest Command) {
		
		if(!(Command==null)){
			
			System.out.println("Elevator has completed " + Command.getTime() + Command.getFloor() + Command.isU_d() + Command.getCarButton());
			notifyFloor(Command);
		elevatorFinished=true;
		}
		else {
			elevatorFinished=false;
			
		}
	}
	private void notifyFloor(PersonRequest Command) {
		if(!(Command == null)) {
			System.out.println("Floor is now at " + Command.getTime() + Command.getFloor() + Command.isU_d() + Command.getCarButton());
			arrivedAtFloor=true;
		}
		else {
			arrivedAtFloor=false;
		}
	}
	

	

	

	
	

	
	
	
}

	
		
		
