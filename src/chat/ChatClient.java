package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	
	private static final String SERVER_IP = "192.168.1.28";
	private static final int SERVER_PORT = 7000;
	
	
	public static void main(String[] args) {

		// 1. Scanner 생성
		Scanner scanner = null;
		// 2. 소켓 생성
		Socket socket = null;
		
		try {
			scanner = new Scanner(System.in);
			socket = new Socket();
			
			// 3. 서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("connected");

			// 4. IOStream 받아오기

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			PrintWriter pr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			while (true) {
				System.out.print("닉네임을 정하세요");
				String nickname = scanner.nextLine();
				
				if(nickname.isEmpty() == false) {
					break;
				}
				
				// 6. 데이터 쓰기
				pr.println(nickname);// ln 으로 개행을 붙여야 서버에서 인식함 print로 보내면 안됨

				// 7. 데이터 읽기
				String data = br.readLine();
				if (data == null) {
					log("closed by server");
					break;
				}

				// 8. console 출력
				System.out.println("<<" + data);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (scanner != null) {
					scanner.close();
				}
				if (socket != null && socket.isClosed() == false)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void log(String log) {
		System.out.println("[server] " + log);
	}
	
	
}
