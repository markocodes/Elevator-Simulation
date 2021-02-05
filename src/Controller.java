import java.util.*;


public class Controller {

	
	
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
	
	private void Schedule(ArrayList<PersonRequest> list) {
		for(PersonRequest Command:list) {
			notifyElevator(Command);
			
		}
		
	}
private boolean notifyElevator(PersonRequest Command) {
		
		if(!(Command == null)) {
			
		 System.out.println("Elevator is sent " + Command.getTime() + Command.getFloor() + Command.isU_d() + Command.getCarButton());
			return true;
			
		}
		return false;
		
		
	}
	
	private boolean newRequest(ArrayList<PersonRequest> list) {
		if(!list.isEmpty()) {
			return true;
			
		}
		return false;
	}
	
	
	public synchronized void SchedulerToElevator() {
	
		while(true) {
			try {
			wait();
		}catch(InterruptedException e) {
			
		}
		
		
		
			notifyAll();
		}
	}
	private boolean Scheduled() {
		if(true) {
			return true;
		}
		return false;
	}

		
	
	public synchronized void ElevatorToScheduler() {
		
		while(true) {
			try {
				wait();
			
		
			
		}catch(InterruptedException e) {}
	}
		
	}
	
	private boolean ElevatorFinished(){
		if(true) {
		return true;
	}
	return false;
}
		
	
	public synchronized void SchedulerToFloor() {
		while(true) {
			try {
		
			wait();
		}catch(InterruptedException e) {
		}
		
	}
	}
	private boolean SchduledForFloor() {
		if(true) {
			return true;
		}
		return false;
		
	
	
		
		
	}
}

	
		
		
