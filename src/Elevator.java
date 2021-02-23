import java.util.*;


public class Elevator implements Runnable{
	enum State {
		DOORCLOSED,
		ACCELERATING,
		CRUISING,
		DECELERATING,
		STOPPED,
		DOOROPEN
	}
	

	/**
	 * Shared controller instance, used as the medium to pass data between threads
	 */
	private Controller controller;
	public static State currentState=State.DOOROPEN;
	
	public static long doorCloseTime = 0;
	public static int test = 1;
	/**
	 * The Floor constructor initializes an instance of Scheduler and assigns the shared Controller instance
	 */
	public Elevator(Controller controller) {
		this.controller = controller;
	}
	@Override
	public void run() {

		while(true) {
			ArrayList<PersonRequest> response = controller.getInstructions();
			System.out.println("4. Requests obtained by Elevator Thread!");
			controller.putElevatorResponses(response);
			System.out.println("5. Requests put by Elevator Thread!");
			try {
				Thread.sleep(1000);				
			}
			catch(InterruptedException e) {}
		}
		
		
	}
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
	
}
	