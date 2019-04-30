package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import echo.EchoServer;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;

	public ChatServerThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		EchoServer.log("connected by client[" + remoteHostAddress + ":" + remotePort + "]" );
		
		
		try {
			//4. IOStream 생성(받아오기)
			BufferedReader br = new BufferedReader( 
					new InputStreamReader( socket.getInputStream(),
										   "utf-8") ); 				
			PrintWriter pr = new PrintWriter( 
					new OutputStreamWriter( socket.getOutputStream(), "utf-8"), true );
			
			while(true) {
				//5. 데이터 읽기
				String data = br.readLine();
				if(data == null) {
					System.out.println("클라이언트로 부터 연결 끊김"); //여기서 방 퇴장??
					break;
				}
				
				EchoServer.log("received:" + data);
				
				//6. 데이터 쓰기
				pr.println(data);
			}
		}catch(SocketException e) {
			System.out.println("[server] sudden closed by client");
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(socket != null && socket.isClosed() == false ) {
					socket.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	
	}

}
