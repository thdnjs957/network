package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			//1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 바인딩(binding) : Socket에 SocketAddress(IPAddress + Port)를 바인딩 한다.
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			//String localhost = inetAddress.getHostAddress();
			
			//serverSocket.bind(new InetSocketAddress(localhost,5000));//원래 이렇게 가능
			//serverSocket.bind(new InetSocketAddress(inetAddress,5000));//이렇게도 가능
			
			serverSocket.bind(new InetSocketAddress("0.0.0.0",4545)); //여러개가 있는 경우도 있으니깐 0.0.0.0으로 한다
											//client 에서 연결이 되면 그때 이 위에 클라이언트가 찌른 ip로 바뀜
			
			//xshell 127.0.0.1 4545 로 
			
			//3. accept client의 연결 요청을 기다린다.
			Socket socket = serverSocket.accept();
			
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			
			//결국 요청한 반대편 inetSocketAddress나옴
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			
			System.out.println(socket.getRemoteSocketAddress());
			
			System.out.println("[server] connected by client ["+remoteHostAddress + ":" + remotePort + "]");

			//요부분은 thread처리 해서 따로 빼야함 accept와 따로 돌게 하기 위해서
			
			try {
				//4. IOStream 받아오기 
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while(true) {
					//5. 데이터 읽기
					byte[] buffer = new byte[256];//데이터 통신은 byte
					int readByteCount = is.read(buffer); // blocking 
					
					if(readByteCount == -1) {
						//클라이언트가 -1 return 하면 정상종료 한 경우임
						//close() 메소드 호출
						System.out.println("[server] closed by client");
						break;
					}
					
					String data = new String(buffer,0,readByteCount, "utf-8");
					System.out.println("[server recevied: "+data+"]");
					
					
					//6. 데이터 쓰기
					os.write(data.getBytes("utf-8"));
				
				}
				
				
			}
			catch(SocketException e) {//IOException이 이미 이 기능이 있어서 socketException은 더 위에 작성 
				System.out.println("[server] sudden close by client!");
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if(socket != null && socket.isClosed()==false) socket.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(serverSocket != null && serverSocket.isClosed()==false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}