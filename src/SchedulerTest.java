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
 * @version 2021-03-13
 */
class SchedulerTest {

    @Test
    void parseLineTest(){
        Scheduler scheduler = new Scheduler(23, "Scheduler1");
        float[] arr = new float[]{Float.parseFloat("14"), Float.parseFloat("5"), Float.parseFloat("15")};
        PersonRequest personRequest = new PersonRequest(arr, 2, true, 4, 0);
        assertEquals(personRequest.getTime()[0],scheduler.parseLine("14:05:15.0 2 Up 4 0").getTime()[0]);
    }

    @Test
    void runTest(){
        
    }

    @Test
    /**
     * Test that scheduler sockets work properly.
     */
    void schedulerSocketTest() {
        boolean result = false;
        try {
            // ServerSocket try to open a LOCAL port
            new ServerSocket(22).close();
            // local port can be opened, it's available
            assertFalse(result);
        } catch (IOException e) {
            result = true;
        }
        try {
            new ServerSocket(23).close();

            // local port can be opened, it's available
            assertFalse(result);
        } catch (IOException e) {
            result = true;
        }
    }
}