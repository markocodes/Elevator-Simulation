//package com.javacodegeeks.snippets.desktop;
 
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


 
public class Interface extends JFrame implements ActionListener {
 
   private static final long serialVersionUID = 1L;
 
    public Interface() {
 
        // set flow layout for the frame
        this.getContentPane().setLayout(new FlowLayout());
        
        /*
        JLabel label1 = new JLabel("Image and Text",
                JLabel.CENTER);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        
        add(label1);*/ //added label could potentially use
        
        JButton elevator1 = new JButton("ELEVATOR 1");
        JButton elevator2 = new JButton("ELEVATOR 2");
        JButton elevator3 = new JButton("ELEVATOR 3");
        JButton elevator4 = new JButton("ELEVATOR 4");
        JButton exit = new JButton("EXIT");
        //set action listeners for buttons
        elevator1.addActionListener(this);
        elevator2.addActionListener(this);
        elevator3.addActionListener(this);
        elevator4.addActionListener(this);
        
        exit.addActionListener(this);
 
        //add buttons to the frame
        add(elevator1);
        add(elevator2);
        add(elevator3);
        add(elevator4);
        add(exit);
    }
 
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("ELEVATOR 1")) {
            dispose();
        	NEwWindow myWindow = new NEwWindow();
        }
        else if (action.equals("ELEVATOR 2")) {
        	dispose();
        	ELEVATOR2 myWindow = new ELEVATOR2();
            
        }else if (action.equals("ELEVATOR 3")) {
        	dispose();
        	ELEVATOR3 myWindow = new ELEVATOR3();
            
        }else if (action.equals("ELEVATOR 4")) {
        	dispose();
        	ELEVATOR4 myWindow = new ELEVATOR4();
            
        }else if (action.equals("EXIT")) {
        	System.exit(0);
        }
    }
 
    private static void createAndShowGUI() {
 
  //Create and set up the window.
 
  JFrame frame = new Interface();
 
  //Display the window.
  frame .setBounds(10,10,700,600);
  frame.setTitle("Elevator Control");
  frame.setResizable(false);
  //frame.pack();
 
  frame.setVisible(true);
 
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
    }
 
    public static void main(String[] args) {
 
    	
    	
  //Schedule a job for the event-dispatching thread:
 
  //creating and showing this application's GUI.
 
  javax.swing.SwingUtilities.invokeLater(new Runnable() {
 
public void run() {
 
    createAndShowGUI(); 
 
}
 
  });
    }
 
}
