package supplying;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import jmDNS.Discovery.SampleListener;
import supplying.SupplyingServiceGrpc.SupplyingServiceImplBase;

public class SupplyingServer extends SupplyingServiceImplBase {
	
	private static final Logger logger = Logger.getLogger(SupplyingServer.class.getName());
	
	private static class SampleListener implements ServiceListener {

		@Override
		public void serviceAdded(ServiceEvent event) {
			System.out.println("Service added: "+event.getInfo().getPort());
			
		}

		@Override
		public void serviceRemoved(ServiceEvent event) {
			System.out.println("Service removed: "+event.getInfo());
			
		}

		@Override
		public void serviceResolved(ServiceEvent event) {
			System.out.println("Service resolved: "+event.getInfo());
			
			SupplyingServer supplyingServer = new SupplyingServer();
			
			int port = 50051;
			
			try {
				Server server = ServerBuilder.forPort(event.getInfo().getPort())
						.addService(supplyingServer)
						.build()
						.start();
				
				logger.info("Supplying server started, listening on port: "+port);
				
				server.awaitTermination();	
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}

	
	
	public static void main(final String[] args) throws InterruptedException {
		
//		SupplyingServer supplyingServer = new SupplyingServer();
//		
//		int port = 50051;
//		
//		try {
//			Server server = ServerBuilder.forPort(port)
//										.addService(supplyingServer)
//										.build()
//										.start();
//			
//			//System.out.println("Supplying server started, listening on port: "+port);
//			
//			//server.awaitTermination();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		
		//get an instance from JmDNS
		try {
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			//Add service listener
			jmdns.addServiceListener("_http._tcp.local.", new SampleListener());
			System.out.println("Listening: ");
			
			//Wait a bit			
			Thread.sleep(10000);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void turnOnSupply(final SupplyRequest request, 
			final StreamObserver<SupplyResponse> responseObserver) {
		
		System.out.println("receiving message, The status is: " +request.getUpdateStatus());
		
		SupplyResponse response = SupplyResponse.newBuilder()
				.setSupplyStatus("The supplying service is turned "+request.getUpdateStatus())
				.build();
				
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();	
		
	}

}
