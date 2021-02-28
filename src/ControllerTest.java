import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Controller class
 *
 * @author Group 5
 * @version 2021-02-06
 */

class ControllerTest {
    Controller controller;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }


    /**
     * Tests getRequests and putRequests
     */
    @Test
    public void testRequests(){
        PersonRequest personRequest1 = new PersonRequest(new float[]{1}, 4, true, 2);
        PersonRequest personRequest2 = new PersonRequest(new float[]{2}, 5, true, 3);
        ArrayList<PersonRequest> personRequests = new ArrayList<>();

        personRequests.add(personRequest1);
        personRequests.add(personRequest2);

        controller.putRequests(personRequests);
        assertTrue(controller.isRequestAvailable());
        assertEquals(2, controller.getRequests().get(0).getCarButton());
    }

    /**
     * Tests putInstructions and getInstructions
     */
    @Test
    public void testInstructions(){
        controller.putInstructions(3);

        assertEquals(3, controller.getInstructions());
        assertFalse(controller.isInstructionAvailable());
    }

    /**
     * Tests putElevatorResponses and getElevatorResponses
     */
    @Test
    public void testElevatorResponses(){


        controller.putElevatorResponses(3);

        assertEquals(3, controller.getElevatorResponses());
        assertFalse(controller.isElevatorResponseAvailable());
    }


    /**
     * Tests putResponses and getResponses
     */
    @Test
    public void testResponses(){
        controller.putResponses(3);

        assertEquals(3, controller.getResponses());
        assertFalse(controller.isResponseAvailable());
    }

}