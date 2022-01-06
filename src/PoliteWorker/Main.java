package PoliteWorker;

public class Main {
	public static void main(String[] args) {
//		slipped condition means that a method is acting on old information due to suspending and the information getting update between this time.
//		fix using synchronize blocks or locks to synchronize changing information
//		if multiple locks are used, the order in which they are acquired can also cause slipped condition of interference
		
		final Worker worker1 = new Worker("Worker 1", true);
		final Worker worker2 = new Worker("Worker 2", true);
		
		final SharedResource sharedResource = new SharedResource(worker1);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				worker1.work(sharedResource, worker2);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				worker2.work(sharedResource, worker1);
			}
		}).start();
		
	}
}
