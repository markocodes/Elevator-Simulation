public class ElevatorInterface {
    //private Elevator elevator;
    private ElevatorView elevatorView;

    public ElevatorInterface(){
    }

    public void addView(ElevatorView elevatorView){
        this.elevatorView = elevatorView;
    }

    public void updateOutput(String s){
        elevatorView.addOutput(s);
    }
}
