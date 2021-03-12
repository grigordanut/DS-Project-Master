package supplying;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import jmDNS.RegistrationService;
import supplying.SupplyingServiceGrpc.SupplyingServiceImplBase;

public class SupplyingServer extends SupplyingServiceImplBase {
	//Ports
	static int supplyingPort;

	public static void main(String[] args) {
		
		
	}
	
	
	
	public static void startGrpc() {
		
		RegistrationService regServ = new RegistrationService();
		
		
		
		try {
			regServ.jmDNSRegister(supplyingPort);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SupplyingServer supplyingServer = new SupplyingServer();
		
		
		Properties prop = supplyingServer.getProperties();
		
		int port = 50051;
		
		try {
			Server server = ServerBuilder.forPort(port)
										.addService(supplyingServer)
										.build()
										.start();
			
			System.out.println("Supplying server started, listening on port: "+port);
			
			server.awaitTermination();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
		
	
	private Properties getProperties() {
		
		Properties prop = null;
		
		//Defined the input properties path
		try (InputStream input = new FileInputStream("src/main/resources/supplying.properties")){
			
			prop = new Properties();
			
			//load a properties file
			prop.load(input);
			
			// get the property value and print it out
            System.out.println("Supplying Service properies ...");
            System.out.println("\t service_type: " + prop.getProperty("service_type"));
            System.out.println("\t service_name: " +prop.getProperty("service_name"));
            System.out.println("\t service_description: " +prop.getProperty("service_description"));
	        System.out.println("\t service_port: " +prop.getProperty("service_port"));
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}	
		
		return prop;
	}
	
	@Override
	public void turnOnSupply(SupplyRequest request, StreamObserver<SupplyResponse> responseObserver) {
		
		System.out.println("Receiving supplying status request: ");
		
		SupplyResponse response = SupplyResponse.newBuilder()
				.setSupplyStatus("The supplying service is turned "+ request.getUpdateStatus())
				.build();
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();	
	}

}
