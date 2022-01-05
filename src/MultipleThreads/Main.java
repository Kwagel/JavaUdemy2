package MultipleThreads;

 class Main {
	public static void main(String[] args) {
	Countdown countdown = new Countdown();

	CountdownThread t1 = new CountdownThread(countdown);
			t1.setName("Thread 1");
	CountdownThread t2 = new CountdownThread(countdown);
			t2.setName("Thread 2");
	t1.start();
	t2.start();
	}
	
}

class Countdown {
//	 in this instance, the thread share the variable on the heap, and since running is in a random order, the results cannot be consistent
//	when threads fight for usage of the same variable, it causes race conditions
		private int i;
//		synchronized makes a whole thread run to completion before allowing other threads to access it
	public  void doCountdown() {
		String colour;
		switch (Thread.currentThread().getName()) {
			case "Thread 1":
				colour = ThreadColour.ANSI_CYAN;
				break;
			case "Thread 2":
				colour = ThreadColour.ANSI_PURPLE;
				break;
			default:
				colour = ThreadColour.ANSI_GREEN;
		}
//		variables have a lock feature, which means that threads will have to compete for it, however for local variables, they aare created separately, so they won't have functionality if used unless its on a variable the threads  actually share
//		Strings are unique and can be used to synchronise threads as JVM using string pools which are reused
		synchronized (this) {
//			you can synchronise to the method itself. which is referred to using this
			for (i = 10; i > 0; i--) {
				System.out.println(colour + Thread.currentThread().getName() + ": i = " + i);
			}
		}
	}
}

class CountdownThread extends Thread {
	private final Countdown threadCountdown;
	
	public CountdownThread(Countdown countdown) {
		threadCountdown = countdown;
	}
	
	public void run() {
		threadCountdown.doCountdown();
	}
}