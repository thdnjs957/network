package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class nsLookup {
		
	public static void main(String[] args) {
		
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		InetAddress[] inetAddresses = null;
		
		while(true) {
			
			String hostname = null;
			try {
				hostname = in.readLine();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			if(hostname.equals("exit")) {
				break;
			}
			
			try {
				inetAddresses = InetAddress.getAllByName(hostname);
				for(InetAddress addr : inetAddresses) {
					System.out.println(addr.getHostAddress());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
				
			
		}
		System.out.println("종료되었습니다.");
		
	}

}
