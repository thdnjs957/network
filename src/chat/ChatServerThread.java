package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import echo.EchoServer;

public class ChatServerThread extends Thread {
	
	private String nickname;
	private Socket socket;
	private List<PrintWriter> pw_list = null;
	
	public ChatServerThread(Socket socket , List<PrintWriter> pw_list) {
		this.socket = socket;
		this.pw_list = pw_list;
	}
	
	@Override
	public void run() {
		
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		ChatServer.log("connected by client[" + remoteHostAddress + ":" + remotePort + "]" );
		
		try {
			//스트림 얻기
			BufferedReader br = new BufferedReader( 
					new InputStreamReader( socket.getInputStream(),
										   "utf-8") );
			
			PrintWriter pw = new PrintWriter( 
					new OutputStreamWriter( socket.getOutputStream(), "utf-8"), true );
			
			while(true) {
				
				//3. 데이터 읽기 요청 처리
				String data = br.readLine();
				if(data == null) {
					System.out.println("클라이언트로 부터 연결 끊김"); //여기서 방 퇴장??
					break;
				}
				
				//4. 프로토콜 분석
				String[] tokens = data.split( ":" );
				
				if( "join".equals( tokens[0] ) ) {

				  // doJoin( tokens[1], pw );

				} else if( "message".equals( tokens[0] ) ) {
				   
				   //doMessage( tokens[1] );

				} else if( "quit".equals( tokens[0] ) ) {
				   
				   //doQuit();

				} else {

				   ChatServer.log( "에러:알수 없는 요청(" + tokens[0] + ")" );
				}

//				//6. 데이터 쓰기
//				pw.println(data);
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
