/**
 * Class implementing a request made by a Person for an elevator.
 */
public class PersonRequest {
    private final float[] time;
    private final int floor;
    private final boolean u_d;
    private final int carButton;
    private final int error;


    /**
     * Constructor for a PersonRequest object. Initializes all fields.
     * @param time time request is made
     * @param floor floor on which request is made
     * @param u_d whether the request is to go up or down
     * @param carButton floor button within the elevator which is providing service to the passenger
     */
    public PersonRequest(float[] time, int floor, boolean u_d, int carButton, int error) {
        this.time = time;
        this.floor = floor;
        this.u_d = u_d;
        this.carButton = carButton;
        this.error = error;
    }

    // Getters

    public float[] getTime() {
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
    public int getError() {
        return error;
    }
    
    public String toString() {
    	//Print the time
    	StringBuilder printObject = new StringBuilder();
    	float[] time = this.time;
    	for (float elem : time) {
    		if (!(elem == time[time.length - 1])) {
    			printObject.append(String.valueOf((int) elem));
    			printObject.append(":");
    		} else {
    			printObject.append(String.valueOf((int) elem));
    		}
    	}
    	
    	//Print the floor number
    	printObject.append(" ").append(getFloor());
    	
    	//Print Up or Down to represent desired direction
    	printObject.append(" ").append((isU_d()) ? "Up" : "Down");
    	
    	//Print the elevator 
    	printObject.append(" ").append(getCarButton());
    	
    	//Print the error
    	printObject.append(" ").append(getError());
    	
    	return printObject.toString();
    }
}
