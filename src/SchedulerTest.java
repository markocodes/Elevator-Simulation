import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SchedulerTest class tests the Scheduler
 * 
 * This class implements 
 *
 */
class SchedulerTest {

    @Test
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