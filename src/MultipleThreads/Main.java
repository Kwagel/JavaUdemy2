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
	 
		private int i;
	public void doCountdown() {
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
		for ( i = 10; i > 0; i--) {
			System.out.println(colour + Thread.currentThread().getName() + ": i = " + i);
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