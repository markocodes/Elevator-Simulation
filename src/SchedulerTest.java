import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {

    Controller controller;
    Scheduler scheduler;
    Thread schedulerThread;

    @BeforeEach
    void setUp() {
        controller = new Controller();
        scheduler = new Scheduler(controller);

        schedulerThread = new Thread(scheduler);
        schedulerThread.start();
    }

    @Test
    public void stateOne(){
        float[]  time1 = new float[]{15, 2, 1};
        float[]  time2 = new float[]{15, 3, 1};
        float[]  time3 = new float[]{15, 4, 1};
        PersonRequest personRequest1 = new PersonRequest(time1, 2, true, 4);
        PersonRequest personRequest2 = new PersonRequest(time2, 3, true, 6);
        PersonRequest personRequest3 = new PersonRequest(time3, 7, false, 6);

        ArrayList<PersonRequest> requests = new ArrayList<>();
        requests.add(personRequest1);
        requests.add(personRequest2);
        requests.add(personRequest3);

        //controller.putRequests(requests);
        assertEquals(scheduler.getCurrentState(), Scheduler.State.WAIT_FOR_FLOOR_REQUEST);


        //ArrayList<PersonRequest> responses;

//        try {
//            responses = controller.getResponses();
//            System.out.println("8. Requests obtained by Floor Thread");
//        } catch (InterruptedException e) {}
    }

    @Test
    public void stateTwo() throws InterruptedException {
        float[]  time1 = new float[]{15, 2, 1};
        float[]  time2 = new float[]{15, 3, 1};
        float[]  time3 = new float[]{15, 4, 1};
        PersonRequest personRequest1 = new PersonRequest(time1, 2, true, 4);
        PersonRequest personRequest2 = new PersonRequest(time2, 3, true, 6);
        PersonRequest personRequest3 = new PersonRequest(time3, 7, false, 6);

        ArrayList<PersonRequest> requests = new ArrayList<>();
        requests.add(personRequest1);
        requests.add(personRequest2);
        requests.add(personRequest3);

        controller.putRequests(requests);

        Thread.sleep(10000);

        assertEquals(scheduler.getCurrentState(), Scheduler.State.SCHEDULING);
    }

    @Test
    public void stateThree() throws InterruptedException {
        float[]  time1 = new float[]{15, 2, 1};
        float[]  time2 = new float[]{15, 3, 1};
        float[]  time3 = new float[]{15, 4, 1};
        PersonRequest personRequest1 = new PersonRequest(time1, 2, true, 4);
        PersonRequest personRequest2 = new PersonRequest(time2, 3, true, 6);
        PersonRequest personRequest3 = new PersonRequest(time3, 7, false, 6);

        ArrayList<PersonRequest> requests = new ArrayList<>();
        requests.add(personRequest1);
        requests.add(personRequest2);
        requests.add(personRequest3);

        controller.putRequests(requests);

        Thread.sleep(2000);

        ArrayList<PersonRequest> instructions = controller.getInstructions();
        //controller.putInstructions(instructions);

        assertEquals( Scheduler.State.SENDING_REQUEST_TO_ELEVATOR, scheduler.getCurrentState());
    }

    @Test
    public void stateFour() throws InterruptedException {
        float[]  time1 = new float[]{15, 2, 1};
        float[]  time2 = new float[]{15, 3, 1};
        float[]  time3 = new float[]{15, 4, 1};
        PersonRequest personRequest1 = new PersonRequest(time1, 2, true, 4);
        PersonRequest personRequest2 = new PersonRequest(time2, 3, true, 6);
        PersonRequest personRequest3 = new PersonRequest(time3, 7, false, 6);

        ArrayList<PersonRequest> requests = new ArrayList<>();
        requests.add(personRequest1);
        requests.add(personRequest2);
        requests.add(personRequest3);

        controller.putRequests(requests);

        Thread.sleep(2000);

        ArrayList<PersonRequest> instructions = controller.getInstructions();
        controller.putResponses(instructions);


        assertEquals( Scheduler.State.WAIT_FOR_ELEVATOR_COMPLETION, scheduler.getCurrentState());
    }



}