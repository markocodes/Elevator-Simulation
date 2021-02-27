import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ElevatorTest {
	
	@Test
	/**
	 * Test the states of the Elevator Class
	 * 
	 * @throws InterruptedException
	 */
	void StateTest() throws InterruptedException {
		Controller testController = new Controller();
		Elevator testElevator = new Elevator(testController);
		Thread elevatorThread = new Thread(testElevator);
		elevatorThread.start();
		
		float[] testTime = {14,5,(float) 15.2};
        PersonRequest personRequest1 = new PersonRequest(testTime, 4, true, 2);
        PersonRequest personRequest2 = new PersonRequest(testTime, 5, true, 3);
        ArrayList<PersonRequest> personRequests = new ArrayList<>();
        personRequests.add(personRequest1);
        personRequests.add(personRequest2);
		
		//Check first state or idle state at start up
		assertEquals(Elevator.State.DOOROPEN,testElevator.getcurState()); 
		testController.putInstructions(personRequests);
		Thread.sleep(1000);
		assertEquals(Elevator.State.DOORCLOSED,testElevator.getcurState());
		Thread.sleep(2500);
		assertEquals(Elevator.State.MOVING,testElevator.getcurState());
		Thread.sleep(1000);
		assertEquals(Elevator.State.STOPPED,testElevator.getcurState());
		Thread.sleep(1000);
		assertEquals(Elevator.State.DOOROPEN,testElevator.getcurState());
	}

}
