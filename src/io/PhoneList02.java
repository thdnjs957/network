package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PhoneList02 {
	
	public static void main(String[] args) {
		Scanner scan = null;
		try {
			 scan = new Scanner(new File("phone.txt"));
			 while(scan.hasNextLine()) {
				 String name = scan.next();
				 String phone01 = scan.next();
				 String phone02 = scan.next();
				 String phone03 = scan.next();
				 
				 System.out.println(name + ":" + phone01 + "-" + phone02 + "-" + phone03);
				 
				 
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(scan != null) {
				scan.close();
			}
		}
		
	}

}
