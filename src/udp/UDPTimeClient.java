package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPTimeClient {

	private static final String SERVER_IP = "192.168.1.28";
	private static final int SERVER_PORT = 7004;
	
	public static void main(String[] args) {
		
		// 1. 소켓 생성
		Scanner scanner = null;
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket();
			scanner = new Scanner(System.in);
			
			while (true) {
				
				String line = scanner.nextLine();
				if ("quit".contentEquals(line)) {
					break;
				}

				//4. 데이터 보내기
				byte[] sendData = line.getBytes();
				
			    DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,new InetSocketAddress(SERVER_IP,UDPEchoServer.PORT));
				socket.send(sendPacket);
				
				// 5. 서버로 부터 받은 데이터 읽기
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE],UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket);
				
				String time = new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
								
				// 6. 서버에서 받은 시간 출력
				System.out.println("[server]"+time);
				
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (scanner != null) {
					scanner.close();
				}
				if (socket != null && socket.isClosed() == false)
					socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void log(String log) {
		System.out.println("[server] " + log);
	}

}
