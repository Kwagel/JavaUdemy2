package Starvation;

import java.io.Reader;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	//	setting the first parameter as true enable a fair reentrant lock which is fcf but not duration of tasks.
//	fair locks use more computation, so it might run faster to just allow starvation and use synchronized blocks
	private static  ReentrantLock lock = new ReentrantLock(true);
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Worker(ThreadColour.ANSI_RED), "Priority 10");
		Thread t2 = new Thread(new Worker(ThreadColour.ANSI_BLUE), "Priority 8");
		Thread t3 = new Thread(new Worker(ThreadColour.ANSI_GREEN), "Priority 6");
		Thread t4 = new Thread(new Worker(ThreadColour.ANSI_CYAN), "Priority 4");
		Thread t5 = new Thread(new Worker(ThreadColour.ANSI_PURPLE), "Priority 2");
//	priority gives preference for certain thread to get the lock first, but it isn't certain, lower priority threads may be starved of locks
		t1.setPriority(10);
		t2.setPriority(8);
		t3.setPriority(6);
		t4.setPriority(4);
		t5.setPriority(2);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
	
	//	worker is static so there is only one instance of worker
//	each thread has to compete for the worker object
//	constantly
	private static class Worker implements Runnable {
		private int runCount = 1;
		private String threadColour;
		
		public Worker(String threadColour) {
			this.threadColour = threadColour;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
//			%s for strings, %d for integers
				lock.lock();
				try {
					System.out.format(threadColour + "%s: runCount = %d\n", Thread.currentThread().getName(), runCount++);
//			Execute critical section of code
				}finally {
					lock.unlock();
				}
			}
		}
	}
}
