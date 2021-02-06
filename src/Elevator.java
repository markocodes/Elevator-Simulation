import java.util.ArrayList;

public class Elevator implements Runnable{

	/**
	 * Shared controller instance, used as the medium to pass data between threads
	 */
	private Controller controller;
	
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
	
}
	