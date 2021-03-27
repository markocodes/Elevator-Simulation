import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Implements the Elevator class which represents an elevator car in the system.
 *
 * @author Group 5
 * @version 2021-03-27
 */

public class Elevator implements Runnable {
    enum State {
        DOORCLOSED,
        DOOROPEN,
        MOVING,
        STOPPED
    }

    private State currentState = State.DOOROPEN;
    private boolean up;
    private int response;
    private ArrayList<Integer> destination;
    private int currentFloor;
    private int port;
    private int id;
    private double currentVelocity = 0;
    private int error = 0;

    /**
     * The Floor constructor initializes an instance of Scheduler, and initializes all fields.
     */
    public Elevator(int curFloor, int port, int id) {
        this.port = port;
        this.currentFloor = curFloor;
        this.up = false;
        this.id = id;
    }

    @Override
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
        destination = new ArrayList<>();

        try {
            DatagramSocket socket = new DatagramSocket(port);    //Creates socket bound to each elevators port

            while (true) {
                if (currentState == State.DOOROPEN) {
                    Thread.sleep(4750);
                    System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Doors are open");
                    byte[] requestByteArray = "request".getBytes();
                    boolean receieved = false; //defines a flag to check for receieving a actual packet vs a nothing to report packet ("null")
                    DatagramPacket recievedPacket = new DatagramPacket(new byte[18], 18);    //Creates a packet to recieve into
                    DatagramPacket requestPacket = new DatagramPacket(requestByteArray, requestByteArray.length, InetAddress.getLocalHost(), 22);

                    while (!receieved) {    //Loop until a non null packet is recieved
                        socket.send(requestPacket);    //Send a request to the intermediate server
                        socket.receive(recievedPacket);    //Receive the response
                        if (!(new String(recievedPacket.getData()).trim().equals("NA"))) {//If the response is not null, ie. a actual response
                            receieved = true;    //Break out of loop
                        }
                    }
                    byte[] temp = recievedPacket.getData();
                    String tempDest = (new String(temp));
                    String tempArr[] = tempDest.split(" ");
                    int dest = Integer.parseInt(tempArr[0]);
                    error = Integer.parseInt(tempArr[1].replaceAll("[^\\d.]", ""));
                    if (currentFloor < dest) {
                        up = true;
                    } else if (currentFloor > dest) {
                        up = false;
                    }

                    System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Requests obtained by Elevator to floor " + dest);

                    if (response != currentFloor) {
                        destination.add(response);
                        destination.sort(null);
                    }
                    currentState = State.DOORCLOSED;


                    System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Doors are closing ");
                }
                if (currentState == State.DOORCLOSED) {
                    long startTime = System.nanoTime();
                    try {
                        Thread.sleep(4750);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (error == 1) {
                        Thread.sleep(5000);
                        error = 0;
                    }
                    long endTime = System.nanoTime();
                    long elapsedTime = (endTime - startTime) / 1000000;
                    if (elapsedTime > 9000) {
                        System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Doors are blocked (Transient Error)!");
                        currentState = State.DOORCLOSED;
                        System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Doors are closing again");
                    } else {
                        System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Doors are closed ");
                        currentState = State.MOVING;
                        System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Moving");
                    }

                }
                if (currentState == State.MOVING) {
                    boolean stop = false;
                    boolean firstLoop = true;
                    while (!stop) {
                        int destFloor = currentFloor;
                        String help = "Help";
                        DatagramPacket recievedPacket = new DatagramPacket(new byte[17], 17);    //Creates a packet to recieve into
                        if (error == 2) {
                            byte[] requestByteArray = String.valueOf(help).getBytes();
                            DatagramPacket requestPacket = new DatagramPacket(requestByteArray, requestByteArray.length, InetAddress.getLocalHost(), 22);
                            //Loop until a non null packet is received
                            socket.send(requestPacket);    //Send a request to the intermediate server
                            socket.receive(recievedPacket);    //Receive the response
                        }
                        byte[] requestByteArray = String.valueOf(destFloor).getBytes();
                        DatagramPacket requestPacket = new DatagramPacket(requestByteArray, requestByteArray.length, InetAddress.getLocalHost(), 22);
                        //Loop until a non null packet is received
                        socket.send(requestPacket);    //Send a request to the intermediate server
                        socket.receive(recievedPacket);    //Receive the response
                        long startTime = System.nanoTime();

                        if ((new String(recievedPacket.getData()).trim().equals("stop"))) {//If the response is not null, ie. a actual response
                            //Break out of loop
                            if (firstLoop) {
                                Thread.sleep(7303);
                            } else {
                                Thread.sleep(5186);
                            }
                            stop = true;
                            break;
                        }
                        Thread.sleep(2832);
                        //determine start time

                        if (error == 2) {
                            Thread.sleep(10000);
                        }
                        if (up) {
                            currentFloor++;

                            System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Arrival sensor triggered - arriving at floor " + currentFloor + " next");
                        } else if (!up) {
                            currentFloor--;
                            System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Arrival sensor triggered - arriving at floor " + currentFloor + " next");
                        }
                        long endTime = System.nanoTime();
                        long elapsedTime = (endTime - startTime) / 1000000;
                        if (elapsedTime > 9000) {
                            stop = true;
                        }
                        firstLoop = false;
                    }

                    currentState = State.STOPPED;
                    System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Stopped");
                }
                if (currentState == State.STOPPED) {
                    if (error == 2) {
                        System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": Stuck (Permanent Error)!.");
                        Thread.sleep(60000);
                        String fixed = "fixed";
                        byte[] requestByteArray = String.valueOf(fixed).getBytes();
                        DatagramPacket recievedPacket = new DatagramPacket(new byte[17], 17);    //Creates a packet to recieve into
                        DatagramPacket requestPacket = new DatagramPacket(requestByteArray, requestByteArray.length, InetAddress.getLocalHost(), 22);
                        //Loop until a non null packet is received
                        socket.send(requestPacket);    //Send a request to the intermediate server
                        socket.receive(recievedPacket);    //Receive the response
                        System.out.println(java.time.LocalTime.now().format(dtf) + "  Elevator " + this.id + ": is being repaired.");
                        currentState = State.DOOROPEN;
                        error = 0;
                    } else {
                        currentState = State.DOOROPEN;
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method prints the information in receivedPacket, formatted according to if it was sent or recieved
     * @param receivedPacket takes in the packet to be printed
     * @param sending Boolean value that indicates if the packet is to be sent, or was recieved
     */
    public static void printPacket(DatagramPacket receivedPacket, boolean sending) {
        if (!sending) {    //If the packet was received
            System.out.println("Server: Received the following packet (String): " + new String(receivedPacket.getData())); //Print data as string (Binary values will not appear correctly in the string, but this is what the assignment said to do)
            System.out.println("Received the following packet (Bytes): ");
            for (int z = 0; z < receivedPacket.getData().length - 1; z++) {    //Prints the byte array one index at a time
                System.out.print(receivedPacket.getData()[z] + ", ");
            }
            System.out.println(receivedPacket.getData()[receivedPacket.getData().length - 1]);
            System.out.println("From:" + receivedPacket.getAddress() + " on port: " + receivedPacket.getPort()); //Prints the address and port the packet was recieved on
            System.out.println("");    //Adds a newline between packet sending and receiving
        } else {            //The packet is being sent
            System.out.println("Server: Sending the following packet (String): " + new String(receivedPacket.getData()));//Print data as string (Binary values will not appear correctly in the string, but this is what the assignment said to do)
            System.out.println("Sending the following packet (Bytes): ");
            for (int z = 0; z < receivedPacket.getData().length - 1; z++) {    //Prints the byte array one index at a time
                System.out.print(receivedPacket.getData()[z] + ", ");
            }
            System.out.println(receivedPacket.getData()[receivedPacket.getData().length - 1]);
            System.out.println("To:" + receivedPacket.getAddress() + " on port: " + receivedPacket.getPort());    //Prints the address and port the packet is being sent to
            System.out.println("");    //Adds a newline between packet sending and receiving

        }
    }

    /**
     * Parse config file to determine amount of elevators to create
     * @return Number of elevators needed
     * @throws FileNotFoundException
     */
    public static int parseConfig() throws FileNotFoundException {
        ArrayList<String> configLines = new ArrayList<>();

        File file = new File("building.config.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            configLines.add(line);

        }
        scanner.close();
        String[] splitLine = configLines.get(5).split(" ");
        return Integer.parseInt(splitLine[1]);
    }


    /**
     * Return largest of two roots from quadratic equation.
     * @param a value of a from quadratic eq.
     * @param b value of b from quadratic eq.
     * @param c value of c from quadratic eq.
     * @return largest root
     */
    public double quadratic(double a, double b, double c) {

        // value a, b, and c
        double root1 = 0, root2 = 0;

        // calculate the determinant (b2 - 4ac)
        double determinant = b * b - 4 * a * c;

        // check if determinant is greater than 0
        if (determinant > 0) {

            // two real and distinct roots
            root1 = (-b + Math.sqrt(determinant)) / (2 * a);
            root2 = (-b - Math.sqrt(determinant)) / (2 * a);
        }

        // check if determinant is equal to 0
        else if (determinant == 0) {

            // two real and equal roots
            // determinant is equal to 0
            // so -b + 0 == -b
            root1 = root2 = -b / (2 * a);
        }
        // if determinant is less than zero
        else {
            // roots are complex number and distinct
            double real = -b / (2 * a);
            double imaginary = Math.sqrt(-determinant) / (2 * a);
        }
        return root1;
    }

    // Getters

    public int getError() {
        return error;
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int elevatorCount = parseConfig();

        for (int i = 1; i <= elevatorCount; i++) {
            (new Thread(new Elevator(1, 23 + i, i), "Elevator " + i)).start();
        }

    }
}


		
			
		
	


	
	
