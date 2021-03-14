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

public class SupplyingGUI {

	private JFrame frame;
	
	private static SupplyingServiceBlockingStub blockingStub;
	private static SupplyingServiceStub asyncStub;
	
	private ServiceInfo supplyServiceInfo;

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
		
		String supply_service_type = "_supply._tcp.local.";
		discoverySupplyService(supply_service_type);
		
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
	
	public void discoverySupplyService(String service_type) {
		
		try {
			//Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
