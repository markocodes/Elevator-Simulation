import java.util.ArrayList;

public class Scheduler implements Runnable{

	/**
	 * Shared controller instance, used as the medium to pass data between threads
	 */
	private Controller controller;
	
	/**
	 * The Floor constructor initializes an instance of Scheduler and assigns the shared Controller instance
	 */
	public Scheduler(Controller controller) {
		this.controller = controller;
	}
	@Override
	public void run() {

			while(true) {
				ArrayList<PersonRequest> instructions = controller.getRequests();
				System.out.println("2. Requests obtained by Scheduler Thread!");
				controller.putInstructions(instructions);
				System.out.println("3. Requests put by Scheduler Thread!");
				ArrayList<PersonRequest> elevatorResponses = controller.getElevatorResponses();
				System.out.println("6. Requests obtained by Scheduler Thread!");
				controller.putResponses(elevatorResponses);
				System.out.println("7. Requests put by Scheduler Thread!");

				try {
					Thread.sleep(1000);				
				}
				catch(InterruptedException e) {}
			}
	}
	
}
	