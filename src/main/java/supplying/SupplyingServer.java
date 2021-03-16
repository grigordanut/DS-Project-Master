package supplying;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import supplying.SupplyingServiceGrpc.SupplyingServiceImplBase;

public class SupplyingServer extends SupplyingServiceImplBase {

	public static void main(String[] args) {
		
		SupplyingServer supplyServer = new SupplyingServer();
		
		Properties prop = supplyServer.getProperties();
		
		supplyServer.registerService(prop);
		
		int port = Integer.valueOf( prop.getProperty("service_port")); //#50051
		
		
		try {
			Server server = ServerBuilder.forPort(port)
					.addService(supplyServer)
					.build().start();
			
			System.out.println("Supplying Server started listening on port: " +port);
			
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
		
		//Define the input properties path
		try (InputStream input = new FileInputStream("src/main/resources/supplying.properties")){
			
			prop = new Properties();
			
			//load a properties file
			prop.load(input);
			
			//get the properties value and print it out
			System.out.println("Supplying Setvice properties ...");
			System.out.println("\t service_type: " +prop.getProperty("service_type"));
			System.out.println("\t service_name: " + prop.getProperty("service_name"));
			System.out.println("\t servive_description: " + prop.getProperty("service_description"));
			System.out.println("\t service_port: " + prop.getProperty("service_port"));
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return prop;		
	}
	
	public void registerService(Properties prop) {
		
		try {
			//Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			/*
			 * Setting service information - prepare parameters for creating the ServiceInfo
			 */
			//Assume that there is registering an http server
			String service_type = prop.getProperty("service_type"); //"_supply._tcp.local.";
			String service_name = prop.getProperty("service_name"); //"supply_servce";
			int service_port = Integer.valueOf( prop.getProperty("service_port")); //#50051;
			String service_description_properties = prop.getProperty("service_description"); //"path=index.html";
			
			//Register a service
			ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port, service_description_properties);
			jmdns.registerService(serviceInfo);
			
			System.out.printf("Registering servive with type %s and name %s \n", service_type, service_name);
			
			//Wait a bit
			Thread.sleep(1000);
			
			//System.out.println("Ready to unregister services");
			
			//Unregister all services
			//jmdns.unregisterAllServices();
			//jmdns.unregisterService(serviceInfo);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void turnOnSupply(SupplyRequest request, StreamObserver<SupplyResponse> responseObserver) {
		
		System.out.println("Receiving supplying status request: " +request.getUpdateStatus());
		
		SupplyResponse response = SupplyResponse.newBuilder()
				.setSupplyStatus("The Supplying service has been turned " + request.getUpdateStatus())
				.build();
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();
		
	}

}
