import java.util.*;


public class Controller {

	private boolean requestAvailable = false;
	private boolean elevatorFinished = false;
	private boolean arrivedAtFloor = false;
	
	
	public synchronized void putRequests(ArrayList<PersonRequest> list) {
        
		while(newRequest(list)) {
        try {
        	wait();
        }catch(InterruptedException e) {
        	System.err.println(e);
        	
        }
		}
        Schedule(list);
        System.out.println("AHHHHH");
		if(newRequest(list)) {
			notifyAll();
		}
        
	}
	
	private void reset() {
		
	}
	
	private void Schedule(ArrayList<PersonRequest> list) {
		for(PersonRequest Command:list) {
			notifyElevator(Command);
			
		}
		
	}
private void notifyElevator(PersonRequest Command) {
		
		if(!(Command == null)) {
			System.out.println("Elevator is sent " + Command.getTime() + Command.getFloor() + Command.isU_d() + Command.getCarButton());
			
		 
			requestAvailable = true;
			
		}
		else {
			requestAvailable = false;
		}
		
		
	}
	
	private boolean newRequest(ArrayList<PersonRequest> list) {
		if(!list.isEmpty()) {
			return true;
			
		}
		return false;
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
			notifyAll();
			
			
		
	}
	}
	
	
	
	private boolean Scheduled() {
		if(true) {
			return true;
		}
		return false;
	}

		
	

	
	private boolean ElevatorFinished(){
		if(true) {
		return true;
	}
	return false;
}
		
	
	
	private boolean SchduledForFloor() {
		if(true) {
			return true;
		}
		return false;
		
	
	
		
		
	}
}

	
		
		
