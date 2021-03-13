
public class ThreadStart {

		public static void main(String[] args) {
			Thread  elevator, floor, scheduler_thread1, scheduler_thread2;
						
			elevator = new Thread (new Elevator(1),"Elevator");
			scheduler_thread1 = new Thread (new Scheduler(23),"Scheduler_thread1");
			scheduler_thread2 = new Thread (new Scheduler(22),"Scheduler_thread2");
			
			floor = new Thread (new Floor(),"Floor");
			elevator.start();
			scheduler_thread1.start();
			scheduler_thread2.start();
			floor.start();
		}
	}






