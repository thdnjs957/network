package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyboardTest {

	public static void main(String[] args) {
		
		BufferedReader br = null;
		
		try {
			//기반스트림(표준입력, 키보드, System.in)
			
			
			//보조스트림1(주스트림)
			//byte | byte | byte -> char 로 만들어줌
			InputStreamReader isr = new InputStreamReader(System.in,"utf-8");
			
			//보조스트림2 개행 전까지 저장할 수 있음 , io 줄일 때 쓰임
			//char | char | char | \n -> "char1char2char3" 
			br = new BufferedReader(isr);
					
			//read
			String line = null;
			br.readLine();
			
			while((line = br.readLine()) != null ) {
				if("exit".equals(line)) {
					break;
				}
				
				System.out.println(">>" + line);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(br != null) {
					br.close();
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
				
		}
		
	}

}
