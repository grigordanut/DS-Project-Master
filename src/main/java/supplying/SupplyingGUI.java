package supplying;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.swing.JFrame;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import supplying.SupplyingServiceGrpc.SupplyingServiceBlockingStub;
import supplying.SupplyingServiceGrpc.SupplyingServiceStub;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.type.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SupplyingGUI {

	private JFrame frame;
	
	private static SupplyingServiceBlockingStub blockingStub;
	private static SupplyingServiceStub asyncStub;
	
	private ServiceInfo supplyServiceInfo;
	
	public JLabel supplyInfo_Status;
	private JTextField txtEnterProductName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplyingGUI window = new SupplyingGUI();
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
	public SupplyingGUI() {
				
//		String math_service_type = "_maths._tcp.local.";		
//		discoverMathService(math_service_type);		
//		String host = mathServiceInfo.getHostAddresses()[0];
//		System.out.println(host);
//		int port = mathServiceInfo.getPort();
		
		//SupplyingServer.main(null);
		
		discoverySupplyService();
		
		@SuppressWarnings("deprecation")
		String host = supplyServiceInfo.getHostAddress();
		int port = supplyServiceInfo.getPort();
		
		ManagedChannel channel = ManagedChannelBuilder
								.forAddress(host, port)
								.usePlaintext()
								.build();
		
		blockingStub = SupplyingServiceGrpc.newBlockingStub(channel);
		asyncStub = SupplyingServiceGrpc.newStub(channel);		
		
		initialize();
	}
	
	public void discoverySupplyService() {
		
		try {
			//Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			String service_type = "_supplying._tcp.local.";
			
			jmdns.addServiceListener(service_type, new ServiceListener(){

				@SuppressWarnings("deprecation")
				@Override
				public void serviceResolved(ServiceEvent event) {
					System.out.println("Supply Service resolved: " + event.getInfo());
					
					supplyServiceInfo = event.getInfo();
					
					int port = supplyServiceInfo.getPort();
					
					System.out.println("Resolving " + service_type + " with properties ...");
					System.out.println("\t port: " + port);
					System.out.println("\t type: " + event.getType());
					System.out.println("\t name: " + event.getName());
					System.out.println("\t description/properties: " + supplyServiceInfo.getNiceTextString());
					System.out.println("\t host: " + supplyServiceInfo.getHostAddress());
									
				}
				
				@Override
				public void serviceRemoved(ServiceEvent event) {
					System.out.println("Supply Service removed: " + event.getInfo());
					
				}
				
				@Override
				public void serviceAdded(ServiceEvent event) {
					System.out.println("Supply Service added: " + event.getInfo());
					
				}				
				
			});
			
			Thread.sleep(2000);
			
			jmdns.close();
				
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 10));
		frame.setBounds(100, 100, 726, 431);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Main service Label
		JLabel lblSupplyService = new JLabel("Supply Service");
		lblSupplyService.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSupplyService.setBounds(260, 10, 160, 40);
		frame.getContentPane().add(lblSupplyService);
		
		JLabel lblNewLabel = new JLabel("Change Status");
		lblNewLabel.setBackground(java.awt.Color.WHITE);
		lblNewLabel.setForeground(java.awt.Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(40, 80, 150, 25);
		frame.getContentPane().add(lblNewLabel);
		
		//Service Status Info	
		JLabel lblNewLabel_1 = new JLabel("Service Info");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel_1.setBounds(50, 231, 144, 25);
		frame.getContentPane().add(lblNewLabel_1);
		
		//Show Service Status	
		supplyInfo_Status = new JLabel("Status: Idle");
		supplyInfo_Status.setFont(new Font("Tahoma", Font.BOLD, 16));
		supplyInfo_Status.setBounds(67, 266, 290, 25);
		frame.getContentPane().add(supplyInfo_Status);
		
		
		JLabel lblNewLabel_2 = new JLabel("Add More Products");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(180, 115, 120, 25);
		frame.getContentPane().add(lblNewLabel_2);
		
		//Text field enter product name
		txtEnterProductName = new JTextField();
		txtEnterProductName.setText("Enter Product Name");
		txtEnterProductName.setBounds(50, 155, 144, 25);
		frame.getContentPane().add(txtEnterProductName);
		txtEnterProductName.setColumns(10);
		txtEnterProductName.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				txtEnterProductName.setText(" ");
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		JButton btn_AddProduct = new JButton("Add Product");
		btn_AddProduct.setBackground(java.awt.Color.WHITE);
		btn_AddProduct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_AddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btn_AddProduct.setBounds(215, 155, 110, 25);
		frame.getContentPane().add(btn_AddProduct);
		
		
		
		//Toggle button turn service On/Off		
		final JToggleButton supplyOnOff_tgl = new JToggleButton("On");
		supplyOnOff_tgl.setBounds(214, 80, 100, 25);
		frame.getContentPane().add(supplyOnOff_tgl);
		supplyOnOff_tgl.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if(supplyOnOff_tgl.isSelected()) {
					supplyOnOff_tgl.setText("On");
					String status = "On";
					SupplyRequest requestOn = SupplyRequest.newBuilder().setUpdateStatus(status).build();
					SupplyResponse responseOn= blockingStub.turnOnSupply(requestOn);
					supplyInfo_Status.setText("Status: " +status);	
					System.out.println(responseOn.getSupplyStatus());
				}
				
				else {
					supplyOnOff_tgl.setText("Off");
					String status = "Off";
					SupplyRequest requestOff = SupplyRequest.newBuilder().setUpdateStatus(status).build();
					SupplyResponse responseOff= blockingStub.turnOnSupply(requestOff);
					supplyInfo_Status.setText("Status: " +status);	
					System.out.println(responseOff.getSupplyStatus());
				
				}				
			}
			
		});
	}
}
