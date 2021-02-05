
public class Scheduler implements Runnable{

	/**
	 * Shared controller instance, used as the medium to pass data between threads
	 */
	private Controller controller;
	
	/**
	 * The Floor constructer initializes an instance of Floor and assigns the shared Controller instance
	 */
	public Scheduler(Controller controller) {
		this.controller = controller;
	}
	@Override
	public void run() {

			while(true) {
				
				controller.schedulerToElevator
				try {
					
					Thread.sleep(1000);
					
				
			}
				catch(InterruptedException e) {}
		
		
	}
		
		
	}
	
}
	