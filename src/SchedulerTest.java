import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Scheduler class
 *
 * @author Group 5
 * @version 2021-03-27
 */
class SchedulerTest {

    @Test
    /**
     * Tests if line is parsed correctly.
     */
    void parseLineTest(){
        Scheduler scheduler = new Scheduler(23, "Scheduler1", null);
        float[] arr = new float[]{Float.parseFloat("14"), Float.parseFloat("5"), Float.parseFloat("15")};
        PersonRequest personRequest = new PersonRequest(arr, 2, true, 4, 0);
        assertEquals(personRequest.getTime()[0],scheduler.parseLine("14:05:15.0 2 Up 4 0").getTime()[0]);
    }
}