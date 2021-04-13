import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FloorView extends JFrame{
    private ArrayList<JPanel> lampsUp;
    private ArrayList<JPanel> lampsDown;
    private ArrayList<JPanel> floorNums;
    private JPanel lampUpPanel;
    private JPanel lampDownPanel;
    private JPanel floorNumsPanel;
    private JTextField totalTime;

    public FloorView(){
        super("Floor Request Monitoring Panel");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel listPanel = new JPanel();
        JPanel timePanel = new JPanel();
        listPanel.setLayout(new FlowLayout());
        timePanel.setLayout(new FlowLayout());
        //timePanel.setMaximumSize(new Dimension(70,50));

        totalTime = new JTextField();
        totalTime.setEditable(false);

        totalTime.setText("TEST");

        timePanel.add(totalTime);

        lampsUp = new ArrayList<>();
        lampsDown = new ArrayList<>();
        floorNums = new ArrayList<>();
        lampUpPanel = new JPanel();
        lampDownPanel = new JPanel();
        floorNumsPanel = new JPanel();

        lampUpPanel.setLayout(new BoxLayout(lampUpPanel, BoxLayout.Y_AXIS));
        lampDownPanel.setLayout(new BoxLayout(lampDownPanel, BoxLayout.Y_AXIS));
        floorNumsPanel.setLayout(new BoxLayout(floorNumsPanel, BoxLayout.Y_AXIS));

        floorNumsPanel.add(new JLabel("Floor"));
        lampUpPanel.add(new JLabel(" "));
        lampDownPanel.add(new JLabel(" "));

        for(int i = 0; i < 22; i++) {
            floorNums.add(new JPanel());
            floorNums.get(i).setBackground(Color.WHITE);
            floorNums.get(i).add(new JLabel(String.valueOf(22-i)));
            //floorNums.get(i).add(new JLabel("UP"));
            floorNumsPanel.add(floorNums.get(i));

            lampsUp.add(new JPanel());
            lampsUp.get(i).setBackground(Color.WHITE);
            //lampsUp.get(i).add(new JLabel(String.valueOf(22-i)));
            lampsUp.get(i).add(new JLabel("UP"));
            lampUpPanel.add(lampsUp.get(i));


            lampsDown.add(new JPanel());
            lampsDown.get(i).setBackground(Color.WHITE);
//            lampsDown.get(i).add(new JLabel(String.valueOf(22-i)));
            lampsDown.get(i).add(new JLabel("DOWN"));
            lampDownPanel.add(lampsDown.get(i));




        }

        listPanel.add(floorNumsPanel);
        listPanel.add(lampUpPanel);
        listPanel.add(lampDownPanel);


        this.add(new JLabel("Floor Lamp Monitoring System"));
        this.add(listPanel);
        this.add(new JLabel(" "));
        this.add(new JLabel("Total Execution Time"));
        this.add(timePanel);
        this.setSize(500,1000);
        this.setVisible(true);

    }

    public void addLamps(int floor, int upOrDown){
        if(upOrDown == 1){
            lampsUp.get(22-floor).setBackground(Color.CYAN);
        }else{
            lampsDown.get(22-floor).setBackground(Color.CYAN);
        }
    }

    public void removeLamps(int floor, int upOrDown){
        if(upOrDown == 1){
            lampsUp.get(22-floor).setBackground(Color.WHITE);
        }else{
            lampsDown.get(22-floor).setBackground(Color.WHITE);
        }
    }

    public void updateTotalTime(long time){
        totalTime.setText(String.valueOf(time));
    }

    public static void main(String[] args) {
        new FloorView();
    }
}
