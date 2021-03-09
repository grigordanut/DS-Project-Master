package jmDNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class Registration {

	public static void main(String[] args) {
		
		try {
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", "supplying", 50051, "path=index.html");
			
			jmdns.registerService(serviceInfo);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}

}
