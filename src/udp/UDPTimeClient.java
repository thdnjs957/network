package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPTimeClient {

	private static final String SERVER_IP = "192.168.1.28";
	private static final int SERVER_PORT = 9000;
	
	public static void main(String[] args) {
		
		// 1. 소켓 생성
		Scanner scanner = null;
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket();
			scanner = new Scanner(System.in);
			
			while (true) {
				// 3. 키보드 입력 받기
				System.out.print(">>");
				String line = scanner.nextLine();
				if ("quit".contentEquals(line)) {
					break;
				}

				//4. 데이터 보내기
				byte[] sendData = line.getBytes();
				
			    DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,new InetSocketAddress(SERVER_IP,UDPTimeServer.PORT));
				socket.send(sendPacket);
				
				// 5. 서버로 부터 받은 데이터 읽기
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPTimeServer.BUFFER_SIZE],UDPTimeServer.BUFFER_SIZE);
				socket.receive(receivePacket);
				
				String message = new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
								
				// 6. 서버에서 받은 시간 출력
				System.out.println("<<"+message);
				
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