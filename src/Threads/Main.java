package Threads;

import static Threads.ThreadColour.*;

public class Main {
	public static void main(String[] args) {
		System.out.println(ANSI_PURPLE +"Hello from the main thread");
		Thread anotherThread = new AnotherThread();
		anotherThread.setName("== Another Thread ==");
		anotherThread.start();
//		invoke with start, runs the run method for a thread
//		threads can only be started once
//		you have to use start as if you use run(), you will actually run the code on the same thread
//		anotherThread.run();
		
		new Thread(){
			public void run(){
				System.out.println(ANSI_GREEN + "Hello from the anonymous class thread");
			}
		}.start();
//		Can create runnable classes my implementing runnable
		Thread myRunnableThread = new Thread(new MyRunnable(){
			@Override
			public void run() {
				System.out.println(ANSI_RED + "Hello from the anonymous clas's implementation of run()");
				try{
//					you can add a millisecond value to time out the join command after a certain amount of time has passed.
					anotherThread.join();
					System.out.println(ANSI_RED + "Another Thread terminated or timed out, so I'm running again");
				}catch (InterruptedException e ){
					System.out.println(ANSI_RED + " I couldn't wait after all. I was interrupted");
				}
			}
		});
		myRunnableThread.start();
//		anotherThread.interrupt();
		
//		Threads run in a random order, working with threads is tricky
		System.out.println(ANSI_PURPLE +"Hello again from the main thread");
		
		
	}
}
