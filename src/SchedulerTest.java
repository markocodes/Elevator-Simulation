import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SchedulerTest class tests the Scheduler
 * 
 * This class implements 
 *
 */
class SchedulerTest {

    Controller controller;
    Scheduler scheduler;
    Thread schedulerThread;
    ArrayList<PersonRequest> requests; 
    
    @BeforeEach
    void setUp() {

        controller = new Controller();
        scheduler = new Scheduler(controller);

        
        schedulerThread = new Thread(scheduler);
        schedulerThread.start();
        
        
        float[]  time1 = new float[]{15, 2, 1};
        float[]  time2 = new float[]{15, 3, 1};
        float[]  time3 = new float[]{15, 4, 1};
        PersonRequest personRequest1 = new PersonRequest(time1, 2, true, 4);
        PersonRequest personRequest2 = new PersonRequest(time2, 3, true, 6);
        PersonRequest personRequest3 = new PersonRequest(time3, 7, false, 6);

        requests = new ArrayList<>();
        requests.add(personRequest1);
        requests.add(personRequest2);
        requests.add(personRequest3);
    }

    @Test
    /**
     * Test the execution of the scheduler state machine.
     */
    public void testStates() throws InterruptedException {
        assertEquals(Scheduler.State.WAIT_FOR_FLOOR_REQUEST, scheduler.getCurrentState());

        //floor puts
        controller.putRequests(requests);
        Thread.sleep(2000);

        //elevator gets
        ArrayList<PersonRequest> instructions = controller.getInstructions();
        assertEquals( Scheduler.State.SENDING_REQUEST_TO_ELEVATOR, scheduler.getCurrentState());

        //elevator puts elevator responses 
        controller.putElevatorResponses(instructions);
        Thread.sleep(2000);
        
        //floor gets responses
        ArrayList<PersonRequest> responses = controller.getResponses();
        
        assertEquals( Scheduler.State.WAIT_FOR_FLOOR_REQUEST, scheduler.getCurrentState());
        
        System.out.println("Scheduler Test Successful!!!");
    }
}