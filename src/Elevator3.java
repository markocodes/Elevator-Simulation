import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;

public class ELEVATOR3 implements ActionListener {
	JFrame frame = new JFrame();
	JButton backbutton = new JButton("BACK");
	ELEVATOR3(){
		backbutton.setBounds(0,0,100,50);
		backbutton.setFocusable(false);
		frame.add(backbutton);
		backbutton.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 420);
		frame.setLayout(null);
		frame.setVisible(true);
	}
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if(action.equals("BACK")) {
				Interface myInterface = new Interface();
				Interface.main(null);
				frame.dispose();
			}
			}
			
		}
		


