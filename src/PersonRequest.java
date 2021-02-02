/**
 * Class implementing a request made by a Person for an elevator.
 */
public class PersonRequest {
    private int[] time;
    private int floor;
    private boolean u_d;
    private int carButton;


    /**
     * Constructor for a PersonRequest object. Initializes all fields.
     * @param time time request is made
     * @param floor floor on which request is made
     * @param u_d whether the request is to go up or down
     * @param carButton floor button within the elevator which is providing service to the passenger
     */
    public PersonRequest(int[] time, int floor, boolean u_d, int carButton) {
        this.time = time;
        this.floor = floor;
        this.u_d = u_d;
        this.carButton = carButton;
    }

    // Getters

    public int[] getTime() {
        return time;
    }

    public int getFloor() {
        return floor;
    }

    public boolean isU_d() {
        return u_d;
    }

    public int getCarButton() {
        return carButton;
    }
}
