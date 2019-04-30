package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	
	private static final String DOCUMENT_ROOT = "./webapp";
	private Socket socket;
	
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {//이미지랑 뭐 다른거  그냥 byte로 함

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog( "connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );
					
			
			// get IOStream 
			OutputStream os = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));

			
			String request = null;//GET/ HTTP/1.1 이 한줄을 request에 담자
			
			//브라우저로 부터 오는 요청 다 읽기 
			while(true) {
				String line = br.readLine();
				//브라우저가 연결을 끊으면
				if(line == null) {
					break;
				}

				//요청에 대한 header만 읽음
				if("".equals(line)) {
					break;
				}
				
				//Header의 첫번째 라인만 request에 담자(처리)
				if(request == null) {
					request = line;//다음 라인이 되면 request 는 null이 아니니깐
				}
				
				
			}
			
			//line이 잘 온거임
			consoleLog("received : "+request);
			
			//request 구문분석(파싱)
			String[] tokens = request.split(" ");
			if("GET".contentEquals(tokens[0])) {
				consoleLog("request:" + tokens[1]);
				responseStaticResource(os, tokens[1],tokens[2]);//정적인 응답
				
			}else { //POST, PUT, DELETE, HEAD , CONNECT 와 같은  method는 무시 
				/*
				 * HTTP/1.1 400 Bad Request\r\n
				 * Content-Type:text/html; charset=utf-8\r\n
				 * \r\n
				 * 
				 * body부분은 error/400.html(HTML 에러 문서) 
				 */
				response400Error(os,tokens[2]);
			}
			
					
		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
				
			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}

	public void responseStaticResource(OutputStream os, String url,String protocol) throws IOException{//protocol = 버전
		if("/".equals(url)) {//url을 명시하지 않으면
			url = "/index.html";
			
		}
		
		File file = new File(DOCUMENT_ROOT + url);//file 객체는 파일에 대한 정보를 다룰수 있음
		
		if(file.exists() == false) {
			//응답 예시
			/*
			 * HTTP/1.1 404 File Not Found\r\n
			 * Content-Type:text/html; charset=utf-8\r\n
			 * \r\n
			 * 
			 * body부분은 error/404.html(HTML 에러 문서) 
			 */
			
			response404Error(os,protocol);
			
			return;
		}
		
		//nio
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath()); //css 읽을수있게
		
		//응답
		os.write( (protocol+" 200 OK\r\n").getBytes( "UTF-8" ) );
		os.write( ("Content-Type: "+ contentType +"; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		os.write( "\r\n".getBytes() ); //header 와 body 부분 나눠지는 부분
		os.write( body );
		
		
	}
	
	public void response404Error(OutputStream os,String protocol) throws IOException {

		File error_file = new File(DOCUMENT_ROOT + "/error/404.html");
		String contentType = Files.probeContentType(error_file.toPath());
		
		byte[] body = Files.readAllBytes(error_file.toPath());
		os.write( (protocol+"404 File Not Found\\r\\n").getBytes( "UTF-8" ) );
		os.write( ("Content-Type: "+ contentType +"; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		os.write( "\r\n".getBytes() ); 
		os.write( body );
		
	}
	
	public void response400Error(OutputStream os,String protocol) throws IOException {

		File error_file = new File(DOCUMENT_ROOT + "/error/400.html");
		String contentType = Files.probeContentType(error_file.toPath());
		
		byte[] body = Files.readAllBytes(error_file.toPath());
		os.write( (protocol+"400 Bad Request\\r\\n").getBytes( "UTF-8" ) );
		os.write( ("Content-Type: "+ contentType +"; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		os.write( "\r\n".getBytes() ); 
		os.write( body );
		
	}
	

	
	
	public void consoleLog( String message ) {
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}
}
