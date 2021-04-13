public class FloorInterface {

    private FloorView floorView;

    public FloorInterface(){}

    public void addFloorView(FloorView floorView){
        this.floorView = floorView;
    }

    public void addLamps(PersonRequest personRequest) {
        if (personRequest.isU_d()) {
            floorView.addLamps(personRequest.getFloor(), 1);
        }else{
            floorView.addLamps(personRequest.getFloor(), 0);
        }
    }

    public void removeLamps(int floor, int upOrDown){
        floorView.removeLamps(floor, upOrDown);
    }

    public void updateTotalTime(long time){
        floorView.updateTotalTime(time);
    }

}
