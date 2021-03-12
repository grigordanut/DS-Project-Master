package jmDNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

public class DiscoveryService {
	
	private static class MyServiceListener implements ServiceListener{

		@Override
		public void serviceAdded(ServiceEvent event) {
			System.out.println("Service added: "+event.getInfo());
			
		}

		@Override
		public void serviceRemoved(ServiceEvent event) {
			System.out.println("Service removed: "+event.getInfo());
			
		}

		@Override
		public void serviceResolved(ServiceEvent event) {
			System.out.println("Service resolved: "+event.getInfo());
			
			ServiceInfo info = event.getInfo();
			int port = info.getPort();
			String path = info.getNiceTextString().split("=")[1];
			
			String url = "http://localhost:"+port+"/"+path;
			System.out.println(" --- sending request to "+url);
			
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		
		try {						
			//get an instance from JmDNS
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			//i will discover service based on service_type
			String service_type = "_http._tcp.local.";
			
			//Add service listener
			jmdns.addServiceListener(service_type, new MyServiceListener());
			System.out.println("Listening: ");
			
			//Wait a bit
			Thread.sleep(15000);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
