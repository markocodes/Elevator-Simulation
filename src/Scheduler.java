
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
				
				controller.schedulerToElevator();
				controller.SchedulerToFloor();
				System.out.println("fnis..");
				try {
					
					Thread.sleep(1000);
					
				
			}
				catch(InterruptedException e) {}
		
		
	}
		
		
	}
	
}
	