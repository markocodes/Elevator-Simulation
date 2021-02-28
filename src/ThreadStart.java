
public class ThreadStart {

		public static void main(String[] args) {
			Thread  elevator, floor, scheduler;
			Controller control;
			
			control = new Controller();
			
			elevator = new Thread (new Elevator(control),"Elevator");
			scheduler = new Thread (new Scheduler(control),"Scheduler");
			floor = new Thread (new Floor(control),"Floor");
			elevator.start();
			scheduler.start();
			floor.start();

		}
	}






