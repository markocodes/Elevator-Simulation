import java.util.ArrayList;

public class Scheduler implements Runnable{

	/**
	 * Shared controller instance, used as the medium to pass data between threads
	 */
	private Controller controller;
	private State currentState;
	enum State {
		WAIT_FOR_FLOOR_REQUEST,
		SCHEDULING,
		SENDING_REQUEST_TO_ELEVATOR,
		WAIT_FOR_ELEVATOR_COMPLETION
	}

	private ArrayList<PersonRequest> instructions = null;
	private ArrayList<PersonRequest> elevatorResponses = null;


	
	/**
	 * The Floor constructor initializes an instance of Scheduler and assigns the shared Controller instance
	 */
	public Scheduler(Controller controller) {
		this.controller = controller;
		currentState = State.WAIT_FOR_FLOOR_REQUEST;
	}
	@Override
	public void run() {
			while(true) {

				if(currentState == State.WAIT_FOR_FLOOR_REQUEST) {
					instructions = controller.getRequests();
					System.out.println("2. Requests obtained by Scheduler Thread!");
					currentState = State.SCHEDULING;
				}
				else if(currentState == State.SCHEDULING) {
					controller.putInstructions(instructions);
					System.out.println("3. Requests put by Scheduler Thread!");
					currentState = State.SENDING_REQUEST_TO_ELEVATOR;
				}
				else if(currentState == State.SENDING_REQUEST_TO_ELEVATOR) {
					elevatorResponses = controller.getElevatorResponses();
					System.out.println("6. Requests obtained by Scheduler Thread!");
					currentState = State.WAIT_FOR_ELEVATOR_COMPLETION;
				}
				else if(currentState == State.WAIT_FOR_ELEVATOR_COMPLETION){
					controller.putResponses(elevatorResponses);
					System.out.println("7. Requests put by Scheduler Thread!");
					currentState = State.WAIT_FOR_FLOOR_REQUEST;
					//currentState = State.NONE;
				}

				try {
					Thread.sleep(1000);				
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
	}

	public State getCurrentState() {
		return currentState;
	}
}
	