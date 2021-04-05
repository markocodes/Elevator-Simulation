import javax.swing.*;
import java.awt.*;

public class ElevatorView extends JFrame {
    private Elevator elevator;
    private DefaultListModel<String> consoleOutput = new DefaultListModel<>();
    private JList<String> consoleList;

    public ElevatorView(Elevator elevator, int elevatorID){
        super("Elevator " + elevatorID);
        this.elevator = elevator;

        consoleList = new JList<>(consoleOutput);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        this.add(consoleList);

        //consoleOutput.addElement("THIS IS AA TEST");

        this.setSize(300,300);
        this.setVisible(true);
    }

    public void addOutput(String output){
        consoleOutput.addElement(output);
    }
}
