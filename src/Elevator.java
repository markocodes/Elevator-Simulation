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
	private State currentState=State.DOOROPEN;
	

	private ArrayList<PersonRequest> response;
	private ArrayList<Integer> destination;
	private int CurrentFloor=1;
	
	/**
	 * The Floor constructor initializes an instance of Scheduler and assigns the shared Controller instance
	 */
	public Elevator(Controller controller) {
		this.controller = controller;
	}

	public State getCurrentState() {
		return currentState;
	}

	@Override
	public void run() {
		destination = new ArrayList<Integer>();
		while (true) {
			if (currentState == State.DOOROPEN) {
				response = controller.getInstructions();
				System.out.println("4. Requests obtained by Elevator Thread!");
				for (PersonRequest request : response) {
					if (request.getFloor() != CurrentFloor) {
						destination.add(request.getFloor());
					}
					destination.add(request.getCarButton());
					destination.sort(null);
				}
				System.out.println(destination.toString());
				currentState = State.DOORCLOSED;

				System.out.println("Doors are closing ");

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (currentState == State.DOORCLOSED) {

				currentState = State.MOVING;
				System.out.println("              Elevator is moving");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (currentState == State.MOVING) {

				currentState = State.STOPPED;
				System.out.println("              Elevator has stopped");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (currentState == State.STOPPED) {
				controller.putElevatorResponses(response);
				System.out.println("5. Requests put by Elevator Thread!");
				currentState = State.DOOROPEN;

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}

		
			
		
	


	
	