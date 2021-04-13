import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SchedulerView extends JFrame {

    private JScrollPane consoleScrollPane;
    private JScrollPane requestScrollPane;

    private DefaultListModel<String> requests = new DefaultListModel<>();
    private JList<String> requestList;

    private DefaultListModel<String> consoleOutput = new DefaultListModel<>();
    private JList<String> consoleList;

    private JPanel consoleOutputPanel;

    private JPanel liveTrackingPanelGraphic;
    private JPanel liveTrackingPanel;
    private JPanel consolePanel;

    private JPanel liveTrackingPanelHeader;

    private JPanel timingPanel;
    private JTextField doorCloseTime;
    private JTextField totalTime;

    private ArrayList<JPanel> elevator1;
    private ArrayList<JPanel> elevator2;
    private ArrayList<JPanel> elevator3;
    private ArrayList<JPanel> elevator4;

    private ArrayList<JPanel> lamps;

    private JTextField elevator1Dest;
    private JTextField elevator2Dest;
    private JTextField elevator3Dest;
    private JTextField elevator4Dest;

    private JTextField elevator1State;
    private JTextField elevator2State;
    private JTextField elevator3State;
    private JTextField elevator4State;

    private JTextField elevator1Direction;
    private JTextField elevator2Direction;
    private JTextField elevator3Direction;
    private JTextField elevator4Direction;


    public SchedulerView(){
        super("Elevator System Control Panel");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        requestScrollPane = new JScrollPane();
        requestList = new JList<>(requests);

        for(String s: readFile()){
            requests.addElement(s);
        }

        requestScrollPane.getViewport().add(requestList);
        requestScrollPane.setSize(new Dimension(700, 500));
        requestScrollPane.setMaximumSize(new Dimension(700, 500));



        consoleScrollPane = new JScrollPane();
        //consoleScrollPane.setLayout(new ScrollPaneLayout());

        consoleList = new JList<>(consoleOutput);
        consoleList.setSize(new Dimension(700, 500));
        consoleList.setMaximumSize(new Dimension(700, 500));

        consoleScrollPane.getViewport().add(consoleList);
        consoleScrollPane.setSize(new Dimension(700, 500));
        consoleScrollPane.setMaximumSize(new Dimension(700, 500));

        doorCloseTime = new JTextField();
        totalTime = new JTextField();
        doorCloseTime.setEditable(false);
        totalTime.setEditable(false);

        timingPanel = new JPanel();
        timingPanel.setLayout(new BoxLayout(timingPanel, BoxLayout.Y_AXIS));

        timingPanel.add(new JLabel("Elevator Door Close Time:"));
        timingPanel.add(doorCloseTime);
        timingPanel.add(new JLabel("Total execution time:"));
        timingPanel.add(totalTime);



        consoleOutputPanel = new JPanel();
        consoleOutputPanel.add(new JLabel("CONSOLE OUTPUT"));

        consoleOutputPanel.setLayout(new BoxLayout(consoleOutputPanel, BoxLayout.Y_AXIS));
        //consoleOutputPanel.setMaximumSize(new Dimension(700, 500));
        consoleOutputPanel.add(consoleScrollPane);
        consoleOutputPanel.add(new JLabel(" "));
        consoleOutputPanel.add(new JLabel("INPUT FILE"));
        consoleOutputPanel.add(requestScrollPane);
        consoleOutputPanel.add(timingPanel);


        liveTrackingPanelHeader = new JPanel();

        liveTrackingPanel = new JPanel();
        liveTrackingPanel.setLayout(new BoxLayout(liveTrackingPanel, BoxLayout.Y_AXIS));

        consolePanel = new JPanel();
        liveTrackingPanelGraphic = new JPanel();

        liveTrackingPanelGraphic.setLayout(new FlowLayout());
        //liveTrackingPanelGraphic.setSize(new Dimension(450, 1000));
        //liveTrackingPanelGraphic.setMaximumSize(new Dimension(450, 1000));

        liveTrackingPanelHeader.add(new JLabel("ELEVATOR LIVE TRACKER"));

        liveTrackingPanel.add(liveTrackingPanelHeader);
        liveTrackingPanel.add(liveTrackingPanelGraphic);

        JPanel elevator1Panel = new JPanel();
        JPanel elevator2Panel = new JPanel();
        JPanel elevator3Panel = new JPanel();
        JPanel elevator4Panel = new JPanel();
        JPanel lampsPanel = new JPanel();

//        liveTrackingPanel.add(new JLabel("ELEVATOR LIVE TRACKING"));
//        consolePanel.add(new JLabel("CONSOLE OUTPUT"));

//        liveTrackingPanel.add(elevator1Panel);
//        liveTrackingPanel.add(elevator2Panel);
//        liveTrackingPanel.add(elevator2Panel);
//        liveTrackingPanel.add(elevator2Panel);


        JLabel destLabel1 = new JLabel("\nDestination:");
        JLabel stateLabel1 = new JLabel("\nState:");
        JLabel directionLabel1 = new JLabel("\nDirection");

        JLabel destLabel2 = new JLabel("\nDestination:");
        JLabel stateLabel2 = new JLabel("\nState:");
        JLabel directionLabel2 = new JLabel("\nDirection");

        JLabel destLabel3 = new JLabel("\nDestination:");
        JLabel stateLabel3 = new JLabel("\nState:");
        JLabel directionLabel3 = new JLabel("\nDirection");

        JLabel destLabel4 = new JLabel("\nDestination:");
        JLabel stateLabel4 = new JLabel("\nState:");
        JLabel directionLabel4 = new JLabel("\nDirection");


        JLabel blankSpace1 = new JLabel(" ");
        JLabel blankSpace2 = new JLabel(" ");
        JLabel blankSpace3 = new JLabel(" ");
        JLabel blankSpace4 = new JLabel(" ");
        JLabel blankSpace5 = new JLabel(" ");
        JLabel blankSpace6 = new JLabel(" ");
        JLabel blankSpace7 = new JLabel(" ");
        JLabel blankSpace8 = new JLabel(" ");
        JLabel blankSpace9 = new JLabel(" ");
        JLabel blankSpace10 = new JLabel(" ");
        JLabel blankSpace11 = new JLabel(" ");
        JLabel blankSpace12 = new JLabel(" ");


        elevator1Dest = new JTextField();
        elevator2Dest = new JTextField();
        elevator3Dest = new JTextField();
        elevator4Dest = new JTextField();

        elevator1Dest.setText("None");
        elevator2Dest.setText("None");
        elevator3Dest.setText("None");
        elevator4Dest.setText("None");

        elevator1State = new JTextField();
        elevator2State = new JTextField();
        elevator3State = new JTextField();
        elevator4State = new JTextField();

        elevator1State.setText("Doors Open");
        elevator2State.setText("Doors Open");
        elevator3State.setText("Doors Open");
        elevator4State.setText("Doors Open");

        elevator1Direction = new JTextField();
        elevator2Direction = new JTextField();
        elevator3Direction = new JTextField();
        elevator4Direction = new JTextField();

        elevator1Dest.setEditable(false);
        elevator2Dest.setEditable(false);
        elevator3Dest.setEditable(false);
        elevator4Dest.setEditable(false);

        elevator1State.setEditable(false);
        elevator2State.setEditable(false);
        elevator3State.setEditable(false);
        elevator4State.setEditable(false);

        elevator1Direction.setEditable(false);
        elevator2Direction.setEditable(false);
        elevator3Direction.setEditable(false);
        elevator4Direction.setEditable(false);



        elevator1Panel.setLayout(new BoxLayout(elevator1Panel, BoxLayout.Y_AXIS));
        elevator2Panel.setLayout(new BoxLayout(elevator2Panel, BoxLayout.Y_AXIS));
        elevator3Panel.setLayout(new BoxLayout(elevator3Panel, BoxLayout.Y_AXIS));
        elevator4Panel.setLayout(new BoxLayout(elevator4Panel, BoxLayout.Y_AXIS));
        lampsPanel.setLayout(new BoxLayout(lampsPanel, BoxLayout.Y_AXIS));

        elevator1Panel.add(new JLabel("Elevator 1"));
        elevator2Panel.add(new JLabel("Elevator 2"));
        elevator3Panel.add(new JLabel("Elevator 3"));
        elevator4Panel.add(new JLabel("Elevator 4"));

        liveTrackingPanelGraphic.add(elevator1Panel);
        liveTrackingPanelGraphic.add(elevator2Panel);
        liveTrackingPanelGraphic.add(elevator3Panel);
        liveTrackingPanelGraphic.add(elevator4Panel);
        liveTrackingPanelGraphic.add(lampsPanel);



//        this.add(elevator1Panel);
//        this.add(elevator2Panel);
//        this.add(elevator3Panel);
//        this.add(elevator4Panel);


        elevator1= new ArrayList<>();
        elevator2= new ArrayList<>();
        elevator3= new ArrayList<>();
        elevator4= new ArrayList<>();

        lamps = new ArrayList<>();

        for(int i = 0; i < 22; i++){
            elevator1.add(new JPanel());
            elevator2.add(new JPanel());
            elevator3.add(new JPanel());
            elevator4.add(new JPanel());
            lamps.add(new JPanel());

            elevator1.get(i).setBackground(Color.white);
            elevator2.get(i).setBackground(Color.white);
            elevator3.get(i).setBackground(Color.white);
            elevator4.get(i).setBackground(Color.white);
            lamps.get(i).setBackground(Color.white);
        }

        for(int i = 22; i > 0; i--){
            elevator1.get(i-1).add(new JLabel(String.valueOf(i)));
            elevator2.get(i-1).add(new JLabel(String.valueOf(i)));
            elevator3.get(i-1).add(new JLabel(String.valueOf(i)));
            elevator4.get(i-1).add(new JLabel(String.valueOf(i)));
            lamps.get(i-1).add(new JLabel(String.valueOf(i)));
        }

        for(int i = 21; i >= 0; i--){
            elevator1Panel.add(elevator1.get(i));
            elevator2Panel.add(elevator2.get(i));
            elevator3Panel.add(elevator3.get(i));
            elevator4Panel.add(elevator4.get(i));
            lampsPanel.add(lamps.get(i));
        }

        elevator1.get(0).setBackground(Color.yellow);
        elevator2.get(0).setBackground(Color.yellow);
        elevator3.get(0).setBackground(Color.yellow);
        elevator4.get(0).setBackground(Color.yellow);

        elevator1Panel.add(blankSpace1);
        elevator1Panel.add(destLabel1);
        elevator1Panel.add(elevator1Dest);
        elevator1Panel.add(blankSpace2);
        elevator1Panel.add(stateLabel1);
        elevator1Panel.add(elevator1State);
        elevator1Panel.add(blankSpace9);
        elevator1Panel.add(directionLabel1);
        elevator1Panel.add(elevator1Direction);



        elevator2Panel.add(blankSpace3);
        elevator2Panel.add(destLabel2);
        elevator2Panel.add(elevator2Dest);
        elevator2Panel.add(blankSpace4);
        elevator2Panel.add(stateLabel2);
        elevator2Panel.add(elevator2State);
        elevator2Panel.add(blankSpace10);
        elevator2Panel.add(directionLabel2);
        elevator2Panel.add(elevator2Direction);


        elevator3Panel.add(blankSpace5);
        elevator3Panel.add(destLabel3);
        elevator3Panel.add(elevator3Dest);
        elevator3Panel.add(blankSpace6);
        elevator3Panel.add(stateLabel3);
        elevator3Panel.add(elevator3State);
        elevator3Panel.add(blankSpace11);
        elevator3Panel.add(directionLabel3);
        elevator3Panel.add(elevator3Direction);

        elevator4Panel.add(blankSpace7);
        elevator4Panel.add(destLabel4);
        elevator4Panel.add(elevator4Dest);
        elevator4Panel.add(blankSpace8);
        elevator4Panel.add(stateLabel4);
        elevator4Panel.add(elevator4State);
        elevator4Panel.add(blankSpace12);
        elevator4Panel.add(directionLabel4);
        elevator4Panel.add(elevator4Direction);


        //consoleList.setVisible(true);

        this.add(consoleOutputPanel);
        this.add(liveTrackingPanel);

        this.setSize(1000,1000);
        this.setVisible(true);
    }

    public void updateLiveTrackerLocation(int elevatorID, int floorNum){
        if(elevatorID == 1){
            for(int i = 0; i < 22; i++){
                elevator1.get(i).setBackground(Color.white);
            }
            elevator1.get(floorNum-1).setBackground(Color.yellow);
        }
        else if(elevatorID == 2){
            for(int i = 0; i < 22; i++){
                elevator2.get(i).setBackground(Color.white);
            }
            elevator2.get(floorNum-1).setBackground(Color.yellow);
        }
        else if(elevatorID == 3){
            for(int i = 0; i < 22; i++){
                elevator3.get(i).setBackground(Color.white);
            }
            elevator3.get(floorNum-1).setBackground(Color.yellow);
        }
        else if(elevatorID == 4){
            for(int i = 0; i < 22; i++){
                elevator4.get(i).setBackground(Color.white);
            }
            elevator4.get(floorNum-1).setBackground(Color.yellow);

        }
    }

    public void addFloorRequest(){

    }

    public void removeFloorRequest(){

    }

    public void addOutput(String output){
//        consoleScrollPane.getViewport().add(new JLabel(output));
//        consoleScrollPane.revalidate();
        consoleOutput.addElement(output);

    }

    public ArrayList<String> readFile() {
        ArrayList<String> dataLines = new ArrayList<>();
        try {
            File file = new File("src/input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                dataLines.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return dataLines;
    }


    public static void main(String[] args) {
        new SchedulerView();
    }

    public void updateDestination(int elevatorID, int destination){
        if(elevatorID == 1){
            elevator1Dest.setText(String.valueOf(destination));
        }
        else if(elevatorID == 2){
            elevator2Dest.setText(String.valueOf(destination));
        }
        else if(elevatorID == 3){
            elevator3Dest.setText(String.valueOf(destination));
        }
        else{
            elevator4Dest.setText(String.valueOf(destination));
        }
    }

    public void updateState(int elevatorID, String state){
        if(elevatorID == 1){
            elevator1State.setText(state);
        }
        else if(elevatorID == 2){
            elevator2State.setText(state);
        }
        else if(elevatorID == 3){
            elevator3State.setText(state);
        }
        else{
            elevator4State.setText(state);
        }
    }

    public void updateDirection(int elevatorID, String direction){
        if(elevatorID == 1){
            elevator1Direction.setText(direction);
        }
        else if(elevatorID == 2){
            elevator2Direction.setText(direction);
        }
        else if(elevatorID == 3){
            elevator3Direction.setText(direction);
        }
        else{
            elevator4Direction.setText(direction);
        }
    }

    public void addLamps(int floor){
        //lamps.get(floor-1).setBackground(Color.RED);
        elevator1.get(floor-1).setBackground(Color.CYAN);
        elevator2.get(floor-1).setBackground(Color.CYAN);
        elevator3.get(floor-1).setBackground(Color.CYAN);
        elevator4.get(floor-1).setBackground(Color.CYAN);

    }

    public void removeLamps(int floor){
        // lamps.get(floor-1).setBackground(Color.WHITE);
        elevator1.get(floor-1).setBackground(Color.WHITE);
        elevator2.get(floor-1).setBackground(Color.WHITE);
        elevator3.get(floor-1).setBackground(Color.WHITE);
        elevator4.get(floor-1).setBackground(Color.WHITE);
    }

    public void updateDoorTime(long time){
        doorCloseTime.setText(String.valueOf(time));
    }

    public void updateTotalTime(long time){
        totalTime.setText(String.valueOf(time));
    }


}