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
				
				controller.ElevatorToScheduler();
				
				
				try {
					
					Thread.sleep(1000);
					
				
			}
				catch(InterruptedException e) {}
		
		
	}
		
		
	}
	
}
	