package thread;

public class ThreadEx03 {

	public static void main(String[] args) {
		Thread thread1 = new DigitThread();
		Thread thread2 = new AlphabetThread();
		Thread thread3 = new Thread(new UppercaseAlphabetRunnableImpl());//runnable impl을 구현한 객체를 넣어줌
		
		
		thread1.start();
		thread2.start();
		thread3.start();
		
	
	}

}
