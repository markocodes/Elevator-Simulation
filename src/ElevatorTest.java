import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Elevator class
 *
 * @author Group 5
 * @version 2021-03-27
 */
class ElevatorTest {
    /* PLEASE NOTE: These tests CANNOT be run concurrently, and must be run individually due to port occupancy issues. */

    @Test
    /**
     * Test the ports of elevator are initialized properly.
     *
     * @throws InterruptedException
     */
    void portTest(){
        Elevator elevator1 = new Elevator(1,24,1, null);
        Elevator elevator2 = new Elevator(1,25,2, null);
        Elevator elevator3 = new Elevator(1,26,3 , null);
        Elevator elevator4 = new Elevator(1,27,4, null);

        assertEquals(24,elevator1.getPort());
        assertEquals(25,elevator2.getPort());
        assertEquals(26, elevator3.getPort());
        assertEquals(27,elevator4.getPort());


    }

    @Test
    /**
     * Test if config file is parsed and read correctly.
     */
    void parseConfigTest() throws FileNotFoundException {
        assertEquals(4, Elevator.parseConfig());
    }

    @Test
    /**
     * Tests if the quadratic method will return the correct roots.
     */
    void quadraticTest(){
        Elevator elevator = new Elevator(1, 24, 1, null);
        assertEquals(-3.0, elevator.quadratic(-1, -5, -6));
        assertEquals(-2.0, elevator.quadratic(0.5, 2, 2));
        assertEquals(-0.0, elevator.quadratic(2, 0, 0));
    }

    @Test
    /**
     * Tests if elevator registers a level 2 (Permanent) error.
     */
    void faultTestLevel2() throws IOException, InterruptedException {
        Elevator elevator = new Elevator(1,24, 1, null);
        Thread thread = new Thread(elevator);
        thread.start();
        String fault = "2 2";
        byte[] bytes = fault.getBytes();
        DatagramSocket sendSocket = new DatagramSocket();
        DatagramSocket receiveSocket = new DatagramSocket(22);
        DatagramPacket receivePacket = new DatagramPacket(new byte[10], 10);
        receiveSocket.receive(receivePacket);
        DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 24);
        sendSocket.send(sendPacket);
        Thread.sleep(5000);
        assertEquals(2,elevator.getError());
        sendSocket.close();
        receiveSocket.close();
    }

    @Test
    /**
     * Tests if elevator registers a level 1 (transient) error.
     */
    void faultTestLevel1() throws IOException, InterruptedException {
        Elevator elevator = new Elevator(1,24, 1, null);
        Thread thread = new Thread(elevator);
        thread.start();
        String fault = "2 1";
        byte[] bytes = fault.getBytes();
        DatagramSocket sendSocket = new DatagramSocket();
        DatagramSocket receiveSocket = new DatagramSocket(22);
        DatagramPacket receivePacket = new DatagramPacket(new byte[10], 10);
        receiveSocket.receive(receivePacket);
        DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 24);
        sendSocket.send(sendPacket);
        Thread.sleep(2000);
        assertEquals(1,elevator.getError());
        sendSocket.close();
        receiveSocket.close();
    }

    @Test
    /**
     * Tests that elevator does not register false errors.
     */
    void faultTestLevel0() throws IOException, InterruptedException {
        Elevator elevator = new Elevator(1,24, 1, null);
        Thread thread = new Thread(elevator);
        thread.start();
        String fault = "2 0";
        byte[] bytes = fault.getBytes();
        DatagramSocket sendSocket = new DatagramSocket();
        DatagramSocket receiveSocket = new DatagramSocket(22);
        DatagramPacket receivePacket = new DatagramPacket(new byte[10], 10);
        receiveSocket.receive(receivePacket);
        DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 24);
        sendSocket.send(sendPacket);
        Thread.sleep(1000);
        assertEquals(0,elevator.getError());
        sendSocket.close();
        receiveSocket.close();

    }
}
