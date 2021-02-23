import java.util.*;


public class Elevator implements Runnable{
	enum State {
		DOORCLOSED,
		DOOROPEN,
		MOVING,
		STOPPED
	}
	

	/**
	 * Shared controller instance, used as the medium to pass data between threads
	 */
	private Controller controller;
	public State currentState=State.DOOROPEN;
	

	public ArrayList<PersonRequest> response;
	public ArrayList<Integer> destination;
	public int CurrentFloor=1;
	
	/**
	 * The Floor constructor initializes an instance of Scheduler and assigns the shared Controller instance
	 */
	public Elevator(Controller controller) {
		this.controller = controller;
	}
	@Override
	public void run() {
		destination = new ArrayList<Integer>();
		while(true) {
			if(currentState == State.DOOROPEN) {
				response = controller.getInstructions();
				System.out.println("4. Requests obtained by Elevator Thread!");
				if(response.get(0).getFloor() != CurrentFloor){
					
					destination.add(response.get(0).getFloor());
					
				}
			
					destination.add(response.get(0).getCarButton());
					System.out.println(destination.toString());
				
				
				currentState=State.DOORCLOSED;
				
				System.out.println("Doors are closing " );
				
			
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(currentState == State.DOORCLOSED ) {
				
				currentState = State.MOVING;
				System.out.println("              Elevator is moving");  
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(currentState == State.MOVING ) {
				
				currentState = State.STOPPED;
				System.out.println("              Elevator has stopped");  
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			
			
			
			if(currentState == State.STOPPED) {
				controller.putElevatorResponses(response);
				System.out.println("5. Requests put by Elevator Thread!");
				currentState=State.DOOROPEN;

				try {
					Thread.sleep(1000);				
				}
				catch(InterruptedException e) {}
			}
				
				
		}
}
}
		
			
		
	


	
	