import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Graphical User interface showing floors lamps being illuminated in real-time.
 *
 * @version 2021-04-13
 */

public class FloorView extends JFrame{
    private final ArrayList<JPanel> lampsUp;
    private final ArrayList<JPanel> lampsDown;
    private final JTextField totalTime;

    public FloorView(){
        super("Floor Request Monitoring Panel");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create view sections
        JPanel listPanel = new JPanel();
        JPanel timePanel = new JPanel();
        listPanel.setLayout(new FlowLayout());
        timePanel.setLayout(new FlowLayout());

        totalTime = new JTextField();
        totalTime.setEditable(false);

        timePanel.add(totalTime);

        lampsUp = new ArrayList<>();
        lampsDown = new ArrayList<>();
        ArrayList<JPanel> floorNums = new ArrayList<>();
        JPanel lampUpPanel = new JPanel();
        JPanel lampDownPanel = new JPanel();
        JPanel floorNumsPanel = new JPanel();

        lampUpPanel.setLayout(new BoxLayout(lampUpPanel, BoxLayout.Y_AXIS));
        lampDownPanel.setLayout(new BoxLayout(lampDownPanel, BoxLayout.Y_AXIS));
        floorNumsPanel.setLayout(new BoxLayout(floorNumsPanel, BoxLayout.Y_AXIS));

        // Adding headers
        floorNumsPanel.add(new JLabel("Floor"));
        lampUpPanel.add(new JLabel(" "));
        lampDownPanel.add(new JLabel(" "));

        // Creating floor and lamp display
        for(int i = 0; i < 22; i++) {
            floorNums.add(new JPanel());
            floorNums.get(i).setBackground(Color.WHITE);
            floorNums.get(i).add(new JLabel(String.valueOf(22-i)));
            floorNumsPanel.add(floorNums.get(i));

            lampsUp.add(new JPanel());
            lampsUp.get(i).setBackground(Color.WHITE);
            lampsUp.get(i).add(new JLabel("UP"));
            lampUpPanel.add(lampsUp.get(i));

            lampsDown.add(new JPanel());
            lampsDown.get(i).setBackground(Color.WHITE);
            lampsDown.get(i).add(new JLabel("DOWN"));
            lampDownPanel.add(lampsDown.get(i));
        }

        // Add elements to list panel
        listPanel.add(floorNumsPanel);
        listPanel.add(lampUpPanel);
        listPanel.add(lampDownPanel);

        // Add elements to main view
        this.add(new JLabel("FLOOR LAMP MONITORING SYSTEM"));
        this.add(listPanel);
        this.add(new JLabel(" "));
        this.add(new JLabel("TOTAL EXECUTION TIME (Seconds): "));
        this.add(timePanel);
        this.setSize(500,1000);
        this.setVisible(true);
    }

    /**
     * Illuminate a specified floor/direction lamps
     * @param floor floor of lamp
     * @param upOrDown direction of illuminated lamp
     */
    public void addLamps(int floor, int upOrDown){
        if(upOrDown == 1){
            lampsUp.get(22-floor).setBackground(Color.CYAN);
        }else{
            lampsDown.get(22-floor).setBackground(Color.CYAN);
        }
    }

    /**
     * Turn off specified floor/direction lamp
     * @param floor floor of lamp
     * @param upOrDown direction of illuminated lamp
     */
    public void removeLamps(int floor, int upOrDown){
        if(upOrDown == 1){
            lampsUp.get(22-floor).setBackground(Color.WHITE);
        }else{
            lampsDown.get(22-floor).setBackground(Color.WHITE);
        }
    }

    /**
     * Update the total execution time field
     * @param time String containing total execution time
     */
    public void updateTotalTime(String time){
        totalTime.setText(time);
    }

    public static void main(String[] args) {
        new FloorView();
    }
}
