package supplying;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SupplyingServiceGUI {

	private JFrame frame;
	
	//Ports
	int supplyingPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplyingServiceGUI window = new SupplyingServiceGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SupplyingServiceGUI() throws IOException , InterruptedException{
		initialize();
		
		
		
		SupplyingServer.startGrpc();
		
		
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 718, 562);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Supply Status");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(51, 38, 126, 36);
		frame.getContentPane().add(lblNewLabel);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(462, 46, 126, 28);
		frame.getContentPane().add(textArea);
		
		final JToggleButton supplyOnOff_tgl = new JToggleButton("On");
		supplyOnOff_tgl.setSelected(true);
		supplyOnOff_tgl.setBounds(190, 48, 115, 21);
		frame.getContentPane().add(supplyOnOff_tgl);
		supplyOnOff_tgl.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (supplyOnOff_tgl.isSelected()) {
					supplyOnOff_tgl.setText("On");
					//onOff(true,"Supply");
				}
				
				else {
					
				}				
				
			}
			
			
			
		});
	}
}
