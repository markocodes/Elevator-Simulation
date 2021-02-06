import java.util.*;

/**
 * Controller is the class that deals with the communications between the three threads: Floor, Scheduler and Elevator
 * The ArrayList of PersonRequest objects is passed between threads by writing the ArrayList to one of four variables.
 * Each variable is protected by a conditional variable that indicates if the variable is available to be read to or written from.
 * 
 * 
 * @author Group 5
 * @version February 6 2021
 *
 */

public class Controller {

	private boolean requestAvailable = false; //True when a thread is accessing the requests variable (critical region)
	private boolean instructionAvailable = false; //True when a thread is accessing the instructions variable (critical region)
	private boolean elevatorResponseAvailable = false; //True when a thread is accessing the elevatorResponses variable (critical region)
	private boolean responseAvailable = false; //True when a thread is accessing the responses variable (critical region)
	
	private ArrayList<PersonRequest> requests; //Stores data transferring from Floor -> Scheduler
	private ArrayList<PersonRequest> instructions;//Stores data transferring from scheduler -> Elevator
	private ArrayList<PersonRequest> elevatorResponses;//Stores data transferring from Elevator -> Floor
	private ArrayList<PersonRequest> responses;//Stores data transferring from Elevator -> Floor
	
	
	/**
	 *  putRequests, is a synchronized method called by the Floor thread that checks to see if the ArrayList has been filled up or not and then notifies the scheduler 
	 *  and passes the ArrayList to it
	 * 
	 * @param list this is the ArrayList that is used to store the parsed text file
	 */
	public synchronized void putRequests(ArrayList<PersonRequest> list) {
		while(requestAvailable) {
	        try {
	        	wait();
	        }catch(InterruptedException e) {
	        	System.err.println(e);
	        }
		}
		if(!list.isEmpty()) {
			requests = list;
			requestAvailable = true;
			notifyAll();
		}
	}
        
	/**
	 * getRequests, synchronized method invoked by the Scheduler thread that gets (returns and deletes) the requests list from the Controller object
	 * 
	 * @return returnRequests, an ArrayList of PersonRequet objects used to generate instructions for elevator scheduling
	 */
	public synchronized ArrayList<PersonRequest> getRequests() {
        while (!requestAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }
        ArrayList<PersonRequest> returnRequests = requests;
        requests = null;
        requestAvailable = false;
	    notifyAll();
	    return returnRequests;
    }
	
	/**
	 *  putInstructions, is a synchronized method called by the scheduler thread that checks to see if the arraylist has been filled up or not and then notifies all 
	 *  and passes the ArrayList to the Controller
	 * 
	 * @param list this is the ArrayList that is used to store the parsed text file
	 */
	public synchronized void putInstructions(ArrayList<PersonRequest> list) {
		while(instructionAvailable) {
	        try {
	        	wait();
	        } catch(InterruptedException e) {
	        	System.err.println(e);
	        }
		}
		if(!list.isEmpty()) {
			instructions = list;
			instructionAvailable = true;
			notifyAll();
		}
	}
        
	/**
	 * getInstructions, synchronized method invoked by the Elevator thread that gets (returns and deletes) the instructions list from the Controller object
	 * 
	 * @return returnInstructions, an ArrayList of PersonRequet objects used to generate response objects that will be returned to the Floor object
	 */
	public synchronized ArrayList<PersonRequest> getInstructions() {
        while (!instructionAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }
        ArrayList<PersonRequest> returnInstructions = instructions;
        instructions  = null;
        instructionAvailable = false;
	    notifyAll();
	    return returnInstructions;
    }
	
	/**
	 *  putElevatorResponses, is a synchronized method called by the Elevator thread that passes the input ArrayList to the Controller
	 * 
	 * @param list this is the ArrayList that is used to store the parsed text file
	 */
	public synchronized void putElevatorResponses(ArrayList<PersonRequest> list) {
		while(elevatorResponseAvailable) {
	        try {
	        	wait();
	        } catch(InterruptedException e) {
	        	System.err.println(e);
	        }
		}
		if(!list.isEmpty()) {
			elevatorResponses = list;
			elevatorResponseAvailable = true;
			notifyAll();
		}
	}
        
	/**
	 * getElevatorResponses, synchronized method invoked by the Scheduler thread that gets (returns and deletes) the responses list from the Controller object
	 * 
	 * @return returnElevatorResponses, an ArrayList of PersonRequest objects used to generate response objects
	 */
	public synchronized ArrayList<PersonRequest> getElevatorResponses() {
        while (!elevatorResponseAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }
        ArrayList<PersonRequest> returnElevatorResponses = elevatorResponses;
        elevatorResponses  = null;
        elevatorResponseAvailable = false;
	    notifyAll();
	    return returnElevatorResponses;
    }
	
	/**
	 *  putResponses, is a synchronized method called by the Scheduler thread that passes the input ArrayList to the Controller
	 * 
	 * @param list this is the ArrayList that is used to store the parsed text file
	 */
	public synchronized void putResponses(ArrayList<PersonRequest> list) {
		while(responseAvailable) {
	        try {
	        	wait();
	        } catch(InterruptedException e) {
	        	System.err.println(e);
	        }
		}
		if(!list.isEmpty()) {
			responses = list;
			responseAvailable = true;
			notifyAll();
		}
	}
	
	/**
	 * getResponses, synchronized method invoked by the Floor thread that gets (returns and deletes) the responses list from the Controller object
	 * 
	 * @return responses, an ArrayList of PersonRequest objects used to generate response objects that will be returned to the Floor object
	 */
	public synchronized ArrayList<PersonRequest> getResponses() {
       while (!responseAvailable) {
           try {
               wait();
           } catch (InterruptedException e) {
               return null;
           }
       }
       ArrayList<PersonRequest> returnResponses = responses;
       responses  = null;
       responseAvailable = false;
	   notifyAll();
	   return returnResponses;
   }
}
	