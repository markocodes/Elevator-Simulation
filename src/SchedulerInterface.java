public class SchedulerInterface {

    private SchedulerView schedulerView;

    public SchedulerInterface(){
    }

    public void addSchedulerView(SchedulerView schedulerView) {this.schedulerView = schedulerView;}

    public void updateOutput(String s){
        schedulerView.addOutput(s);
    }

    public void floorChange(int elevatorID, int currFloor){
        schedulerView.updateLiveTrackerLocation(elevatorID,currFloor);
    }

    public void updateDestination(int elevatorID, int destination){
        schedulerView.updateDestination(elevatorID, destination);
    }

    public void updateState(int elevatorID, String state){
        schedulerView.updateState(elevatorID, state);
    }

    public void updateDirection(int elevatorID, String direction){
        schedulerView.updateDirection(elevatorID, direction);
    }

    public void addErrorState(int elevatorID, int floorNum){
        schedulerView.addErrorState(elevatorID, floorNum);
    }

    public void removeErrorState(int elevatorID, int floorNum){
        schedulerView.removeErrorState(elevatorID, floorNum);
    }




}