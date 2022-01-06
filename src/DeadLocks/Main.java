package DeadLocks;



public class Main {
	public static Object lock1 = new Object();
	public static Object lock2 = new Object();
	
	public static void main(String[] args) {
//		To prevent deadlocks from occurring, make the threads obtain the locks in the same order

	new Thread1().start();
	new Thread2().start();
	}
	
	
	private static class Thread1 extends Thread {
		public void run() {
			synchronized (lock1) {
				System.out.println("Thread 1: Has lock1");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println();
				}
				System.out.println("Thread 1: Waiting for lock2");
				synchronized (lock2) {
					System.out.println("Thread 1: Has lock1 and lock2");
				}
				System.out.println("Thread 1: released lock2");
			}
			System.out.println("Thread 1 : Released lock1, Exiting");
		}
		
		
	}
	private static class Thread2 extends Thread{
		public void run(){
			synchronized (lock1) {
				System.out.println("Thread 2: Has lock1");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println();
				}
				System.out.println("Thread 2: Waiting for lock2");
				synchronized (lock2) {
					System.out.println("Thread 2: Has lock1 and lock2");
				}
				System.out.println("Thread 2: released lock2");
			}
			System.out.println("Thread 2 : Released lock1, Exiting");
		}
		
	}
}