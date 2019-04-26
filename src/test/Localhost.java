package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

//localhost 주소 알아내기
public class Localhost {
	public static void main(String[] args) {
		
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			String hostName = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			
			byte[] adresses = inetAddress.getAddress();//String 말고 byte열로 나옴 
			for( byte b_addres : adresses) {
				System.out.print((int)b_addres & 0x000000ff);
				System.out.print(".");
			}
			/*
			 * InetAddress[] inetAddresses = inetAddress.getAllByName(hostName);
			 * 
			 * for(InetAddress addr : inetAddresses) { System.out.println(hostName + ": " +
			 * addr.getHostAddress()); }
			 */
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

}
