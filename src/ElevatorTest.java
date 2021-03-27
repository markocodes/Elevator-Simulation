import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Elevator class
 *
 * @author Group 5
 * @version 2021-03-13
 */
class ElevatorTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    /**
     * Test the ports of elevator are initialized properly.
     *
     * @throws InterruptedException
     */
    void portTest(){
        Elevator elevator1 = new Elevator(1,24,1);
        Elevator elevator2 = new Elevator(1,25,2);
        Elevator elevator3 = new Elevator(1,26,3);
        Elevator elevator4 = new Elevator(1,27,4);

        assertEquals(24,elevator1.getPort());
        assertEquals(25,elevator2.getPort());
        assertEquals(26, elevator3.getPort());
        assertEquals(27,elevator4.getPort());
    }

    @Test
    void parseConfigTest() throws FileNotFoundException {
        assertEquals(4, Elevator.parseConfig());
    }

    @Test
    void quadraticTest(){
        Elevator elevator = new Elevator(1, 24, 1);
        assertEquals(-3.0, elevator.quadratic(-1, -5, -6));
        assertEquals(-2.0, elevator.quadratic(0.5, 2, 2));
        assertEquals(-0.0, elevator.quadratic(2, 0, 0));
    }



    @Test
    /**
     * Test that elevator sockets are working properly.
     */

    void ElevatorSocketTest() {
        boolean result = false;
        try {
            // ServerSocket try to open a LOCAL port
            new ServerSocket(24).close();
            // local port can be opened, it's available
            assertFalse(result);
        } catch (IOException e) {
            result = true;
        }
        try {
            new ServerSocket(25).close();

            // local port can be opened, it's available
            assertFalse(result);
        } catch (IOException e) {
            result = true;
        }
        try {
            new ServerSocket(26).close();
            // local port can be opened, it's available
            assertFalse(result);
        } catch (IOException e) {
            result = true;
        }
        try {
            new ServerSocket(27).close();
            // local port can be opened, it's available
            assertFalse(result);
        } catch (IOException e) {
            result = true;
        }

    }
}
