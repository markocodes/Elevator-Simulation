/**
 * Interface between the Scheduler objects and the FloorView
 *
 * @version 2021-04-13
 */

public class FloorInterface {

    private FloorView floorView;

    /**
     * Add FloorView to the FloorInterface
     * @param floorView FloorView to be added
     */
    public void addFloorView(FloorView floorView){
        this.floorView = floorView;
    }

    /**
     * Send message to FloorView to illuminate lamp
     * @param personRequest PersonRequest causing lamp to be illuminated
     */
    public void addLamps(PersonRequest personRequest) {
        if (personRequest.isU_d()) {
            floorView.addLamps(personRequest.getFloor(), 1);
        }else{
            floorView.addLamps(personRequest.getFloor(), 0);
        }
    }

    /**
     * Send message to FloorView to turn off lamp
     * @param floor floor of lamp
     * @param upOrDown lamp direction
     */
    public void removeLamps(int floor, int upOrDown){
        floorView.removeLamps(floor, upOrDown);
    }

    /**
     * Send message to FloorView to update total execution time
     * @param time String containing total execution time
     */
    public void updateTotalTime(String time){
        floorView.updateTotalTime(time);
    }

}
