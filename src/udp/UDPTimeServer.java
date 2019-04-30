package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPTimeServer {

	public static final int PORT = 7000; //프로토콜 다르면 포트 같아도 상관 없음
	public static final int BUFFER_SIZE = 1024;//패킷안에 내용 사이즈
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			//1. socket 생성 
			socket = new DatagramSocket(PORT);
			
			 while(true) {
				 //2. 데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
				socket.receive(receivePacket);
				
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				String message = new String(data,0,length, "UTF-8");

				System.out.println("[server] received:" + message);
				
				//3. 데이터 전송
				byte[] sendData = message.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,receivePacket.getAddress(),receivePacket.getPort());
				
				socket.send(sendPacket);
			 }
		
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(socket != null && socket.isClosed()== false) {
				socket.close();
			}
		}
		
	}
}
