package ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static ProducerConsumer.Main.EOF;


public class Main {
	//	having`a lot of unlock and lock could make your code error-prone and messy, and difficult to maintain
	public static final String EOF = "EOF";
	
	public static void main(String[] args) {
//		ArrayBlockingQueue make it so you don't need to use reentrant locks, but are bounded, so you must determine the size
		ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(6);
		
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		
		MyProducer producer = new MyProducer(buffer, ThreadColour.ANSI_RED);
		MyConsumer consumer1 = new MyConsumer(buffer, ThreadColour.ANSI_BLUE);
		MyConsumer consumer2 = new MyConsumer(buffer, ThreadColour.ANSI_GREEN);
//		you pass and execute classes with the Runnable interface or threads using .execute()
		executorService.execute(producer);
		executorService.execute(consumer1);
		executorService.execute(consumer2);
		
		Future<String> future = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println(ThreadColour.ANSI_PURPLE + "I'm being printed for the Callable  class");
				return "this is the callable result";
			}
		});
		try {
			System.out.println(future.get());
		} catch (ExecutionException e) {
			System.out.println("Something went wrong");
		} catch (InterruptedException e) {
			System.out.println("Thread running the task was interrupted");
		}
//		shutdown waits for any executing tasks to terminate first
//		will not accept any new tasks
		executorService.shutdown();
//		this will interrupt running tasks if possible and try to shut down asap
//		executorService.shutdownNow();
	
	}
}

class MyProducer implements Runnable {
	private ArrayBlockingQueue<String> buffer;
	private String colour;
	
	
	public MyProducer(ArrayBlockingQueue<String> buffer, String colour) {
		this.buffer = buffer;
		this.colour = colour;
	}
	
	
	@Override
	public void run() {
		Random rand = new Random();
		String[] nums = {"1", "2", "3", "4", "5"};
		for (String num : nums) {
			try {
				System.out.println(colour + "adding..." + num);
//				must lock and unlock manually
//				put automatically blocks the queue
				buffer.put(num);
				Thread.sleep(rand.nextInt(1000));
			} catch (InterruptedException e) {
				System.out.println("Producer was interrupted");
			}
		}
		System.out.println(colour + "Adding EOF and exiting...");
		
		try {
			buffer.put(EOF);
//			finally, ensures that you only unlock once
		} catch (InterruptedException e) {
			System.out.println();
		}
	}
}

class MyConsumer implements Runnable {
	private ArrayBlockingQueue<String> buffer;
	private String colour;
	
	
	public MyConsumer(ArrayBlockingQueue<String> buffer, String colour) {
		this.buffer = buffer;
		this.colour = colour;
	}
	
	@Override
	
	public void run() {
		
		
		while (true) {
			synchronized (buffer) {
				try {
					if (buffer.isEmpty()) {
						continue;
					}
					if (buffer.peek().equals(EOF)) {
//				exiting without removing EOF so other runs don't infinite loops
						System.out.println(colour + "exiting");
						break;
					} else {
						System.out.println(colour + "Removed " + buffer.take());
					}
				} catch (InterruptedException e) {
					System.out.println();
				}
			}
		}
	}
}
