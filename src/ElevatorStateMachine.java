import java.util.*;
public class ElevatorStateMachine {

	
	enum State {
		DOORCLOSED,
		ACCELERATING,
		CRUISING,
		DECELERATING,
		STOPPED,
		DOOROPEN
	}
	

	public static State currentState=State.DOOROPEN;
	
	
	
	public static void stateMachine(){
				
		if("There is a request") {
			currentState=State.DOORCLOSED;
		}
		else if("Time has passed" && currentState == State.DOORCLOSED) {
			currentState=State.ACCELERATING;
		}
		else if("Time has passed" && currentState == State.ACCELERATING) {
			currentState=State.CRUISING;
			
			
			
		}
		else if("Time has passed" && currentState == State.CRUISING) {
			currentState=State.DECELERATING;
			
			
		}
		else if("Time has passed" && currentState == State.DECELERATING) {
			currentState=State.STOPPED;
		}
		else if(currentState == State.STOPPED) {
			currentState=State.DOOROPEN;
			
	}
		
	}
	
	
	public static void main(String args[]) {
		
	stateMachine();
	
}
}
