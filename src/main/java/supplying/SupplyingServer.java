package supplying;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Logger;

import javax.jmdns.JmDNS;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import supplying.SupplyingServiceGrpc.SupplyingServiceImplBase;

public class SupplyingServer extends SupplyingServiceImplBase{
	
	String supplyStatus;
	String supplyResponse;
	
	private SupplyResponse response;
	
	int currentSuppling = 50;
	
	private static final Logger logger = Logger.getLogger(SupplyingServer.class.getName());
	

	public static void main(final String[] args) throws IOException, InterruptedException{
	
		SupplyingServer supplyingServer = new SupplyingServer();
		
		int port = 50051;
				
		
		try {
			Server server = ServerBuilder.forPort(port)
					.addService(supplyingServer)
					.build()
					.start();
			
			System.out.println("Supplying server started, listening on "+port);
			
			server.awaitTermination();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//Create a JmDNS instance
		JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
		
		//Add a service listener
		//jmdns.addServiceListener("_http._tcp.local.", new SampleListener());
		
		System.out.println("Listening; ");
		
		//Wait a bit 		
		Thread.sleep(10000);		

	}
	
	
	@Override
	public void turnOnSupply(final SupplyRequest request, 
			final StreamObserver<SupplyResponse> responseObserver) {
		supplyStatus = "On";
		supplyResponse = "The supply service have been turned on! ";
		
		SupplyResponse response = SupplyResponse.newBuilder()
				.setText(supplyResponse)
				.build();
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();		
		
	}
	
	
	

}
