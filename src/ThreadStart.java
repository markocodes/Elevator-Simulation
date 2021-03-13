
public class ThreadStart {

		public static void main(String[] args) {
			Thread  elevator1,elevator2,elevator3,elevator4, floor, scheduler_thread1, scheduler_thread2;
						
			elevator1 = new Thread (new Elevator(1),"Elevator");
			elevator2 = new Thread (new Elevator(2),"Elevator");
			elevator3 = new Thread (new Elevator(3),"Elevator");
			elevator4 = new Thread (new Elevator(4),"Elevator");
			scheduler_thread1 = new Thread (new Scheduler(23),"Scheduler_thread1");
			scheduler_thread2 = new Thread (new Scheduler(22),"Scheduler_thread2");
			floor = new Thread (new Floor(),"Floor");
			
			elevator1.start();
			elevator2.start();
			elevator3.start();
			elevator4.start();
			scheduler_thread1.start();
			scheduler_thread2.start();
			floor.start();
		}
	}






