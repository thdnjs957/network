package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ChatServer {

	private static final int SERVER_PORT = 7000;
	//public static final String SERVER_IP = "192.168.1.2";

	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		
		try {
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			List<PrintWriter> pw_list = new ArrayList<PrintWriter>();
			
			
			//2. 바인딩(binding)
			serverSocket.bind(new InetSocketAddress("0.0.0.0", SERVER_PORT));
			log("server starts....[port :"+ SERVER_PORT +"]");

			while(true) {//다시 돌아가야함 thread에게 socket 전달하고  
				//3. accept
				Socket socket = serverSocket.accept();
	
				Thread thread = new ChatServerThread(socket,pw_list);
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if( serverSocket != null && serverSocket.isClosed() == false ) {
					serverSocket.close();	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void log(String log) {
		System.out.println("[server#"+ Thread.currentThread().getId() + "] " + log);
	}
	
}
