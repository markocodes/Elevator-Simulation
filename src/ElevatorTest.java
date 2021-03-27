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

        assertEquals(elevator1.getPort(), 24);
        assertEquals(elevator2.getPort(), 25);
        assertEquals(elevator3.getPort(), 26);
        assertEquals(elevator4.getPort(), 27);
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
    @Test
     /**
     * Test that elevators quadratic formula works properly.
     * @param <quadratic>
     */
    void quadraticTest() {
    	
    	//Elevator roots = new Elevator(0, 0, 0 );
    	
    	assertEquals(-1, Elevator.quadratic(1,2,1)); 
    }
    
    @Test
	/**
	 * Test that number of floors match the config file
	 */
	void ConfigTest() {
		try {
			assertEquals(4, Elevator.parseConfig());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    @Test
    /**
    * Test that elevators errors work properly.
    *
    */
    void ElevatorErrorTest() {
    	Elevator error = new Elevator(0, 0, 0);
    	//assert
    }
    @Test
    /**
    * Test that elevators doors are open and working properly, before starting
    *
    */
    void CurrentStateTest() {
    	Elevator state = new Elevator(0, 0, 0);
    	assertEquals(Elevator.State.DOOROPEN,state.getCurrentState());
    	
    }
    
}
