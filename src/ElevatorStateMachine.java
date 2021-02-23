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
	
	//Trial
	public static State currentState=State.DOOROPEN;
	
	public static long doorCloseTime = 0;
	public static int test = 1;
	public static void stateMachine(){
	while (true) {			
		if(test==1) {
			currentState=State.DOORCLOSED;
			test =0;
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(currentState == State.DOORCLOSED ) {
			currentState=State.ACCELERATING;
			System.out.println("ah");  
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
		if(currentState == State.ACCELERATING) {
			currentState=State.CRUISING;
			System.out.println("ahh");  
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		if(currentState == State.CRUISING) {
			currentState=State.DECELERATING;
			System.out.println("ahhh");  
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(currentState == State.DECELERATING) {
			currentState=State.STOPPED;
			System.out.println("ahhhh");  
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(currentState == State.STOPPED) {
			currentState=State.DOOROPEN;
			System.out.println("ahhhhh");  
			
			
	} 
	}
		
	}
	
	
	public static void main(String args[]) {
		
	stateMachine();
	
}
}
