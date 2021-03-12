package jmDNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class RegistrationService {
	
	public static JmDNS jmdns;
	public static ServiceInfo serviceSupply;

	public static void main(String[] args) throws UnknownHostException, IOException {
		
	}
		
	public void jmDNSRegister(int supplyingPort) throws InterruptedException{
		
		try {
			//create a JmDNS instance
			jmdns = JmDNS.create(InetAddress.getLocalHost());
				
			/*
			* Setting service information - prepare parameters for creating the ServiceInfo
			*/
			//Assume that I am registering an http server			
			String service_type = "_http._tcp.local.";  //service - type.domain
			String service_name = "supplying";
			int service_port = 50051;
			String service_path = "path=index.html";
				
			//register a service - with ServiceInfo
			serviceSupply = ServiceInfo.create(service_type, service_name, service_port, service_path);
				
			//Register the service			
			jmdns.registerService(serviceSupply);			
			System.out.printf("registering service with type %s and name %s on port %d \n", service_type, service_name, service_port);	
				
			//usually we want to wait - this is 10 seconds
			Thread.sleep(25000);			
			
				
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
	}	
	
	public void unRegister() {
		//unregister the services
		jmdns.unregisterService(serviceSupply);
		System.out.println("ready to unregister services ");	
	}

}
