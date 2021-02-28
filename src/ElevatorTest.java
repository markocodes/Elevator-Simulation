import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {

    @BeforeEach
    void setUp() {
    }

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

        int personRequests = 2;

        //Check first state or idle state at start up
        assertEquals(Elevator.State.DOOROPEN,testElevator.getCurrentState());
        testController.putInstructions(personRequests);
        Thread.sleep(1000);
        assertEquals(Elevator.State.DOORCLOSED,testElevator.getCurrentState());
        Thread.sleep(2500);
        assertEquals(Elevator.State.MOVING,testElevator.getCurrentState());
        Thread.sleep(1000);
        assertEquals(Elevator.State.STOPPED,testElevator.getCurrentState());
        Thread.sleep(1000);
        assertEquals(Elevator.State.DOOROPEN,testElevator.getCurrentState());
    }

}
