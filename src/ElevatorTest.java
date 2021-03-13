import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
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
    void portTest(){
        Thread  elevatorThread1,elevatorThread2,elevatorThread3,elevatorThread4, floor, scheduler_thread1, scheduler_thread2;

        Elevator elevator1 = new Elevator(1,24);
        Elevator elevator2 = new Elevator(1,25);
        Elevator elevator3 = new Elevator(1,26);
        Elevator elevator4 = new Elevator(1,27);

        assertEquals(elevator1.getPort(), 24);
        assertEquals(elevator2.getPort(), 25);
        assertEquals(elevator3.getPort(), 26);
        assertEquals(elevator4.getPort(), 27);
    }

    @Test
    /**
     * Test ElevatorSocket() method
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
