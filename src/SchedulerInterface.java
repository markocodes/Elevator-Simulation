/**
 * Interface between the Elevator objects and the SchedulerView
 *
 * @version 2021-04-13
 */

public class SchedulerInterface {

    private SchedulerView schedulerView;

    /**
     * Add  a SchedulerView to the SchedulerInterface
     * @param schedulerView SchedulerView to be added
     */
    public void addSchedulerView(SchedulerView schedulerView) {this.schedulerView = schedulerView;}

    /**
     * Send message to SchedulerView to update console output
     * @param s output String
     */
    public void updateOutput(String s){
        schedulerView.addOutput(s);
    }

    /**
     * Send message to SchedulerView to update elevator location
     * @param elevatorID elevator to update
     * @param currFloor elevator location
     */
    public void floorChange(int elevatorID, int currFloor){
        schedulerView.updateLiveTrackerLocation(elevatorID,currFloor);
    }

    /**
     * Send message to SchedulerView to update elevator destination
     * @param elevatorID elevator to update
     * @param destination new destination
     */
    public void updateDestination(int elevatorID, int destination){
        schedulerView.updateDestination(elevatorID, destination);
    }

    /**
     * Send message to SchedulerView to update elevator state
     * @param elevatorID elevator to update
     * @param state new state
     */
    public void updateState(int elevatorID, String state){
        schedulerView.updateState(elevatorID, state);
    }

    /**
     * Send message to SchedulerView to update elevator direction
     * @param elevatorID elevator to update
     * @param direction new direction
     */
    public void updateDirection(int elevatorID, String direction){
        schedulerView.updateDirection(elevatorID, direction);
    }

    /**
     * Send message to SchedulerView to add error signal
     * @param elevatorID elevator to update
     * @param floorNum elevator location
     */
    public void addErrorState(int elevatorID, int floorNum){
        schedulerView.addErrorState(elevatorID, floorNum);
    }

    /**
     * Send message to SchedulerView to remove error signal
     * @param elevatorID elevator to update
     * @param floorNum elevator location
     */
    public void removeErrorState(int elevatorID, int floorNum){
        schedulerView.removeErrorState(elevatorID, floorNum);
    }




}