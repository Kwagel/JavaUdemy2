package Threads;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello from the main thread");
		Thread anotherThread = new AnotherThread();
//		invoke with start, runs the run method for a thread
//		threads can only be started once
		anotherThread.start();
//		Threads run in a random order,
		System.out.println("Hello Again from the main thread");
		
	}
}
