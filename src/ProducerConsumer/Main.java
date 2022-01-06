package ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static ProducerConsumer.Main.EOF;


public class Main {
	//	having`a lot of unlock and lock could make your code error-prone and messy, and difficult to maintain
	public static final String EOF = "EOF";
	
	public static void main(String[] args) {
		List<String> buffer = new ArrayList<>();
		ReentrantLock bufferLock = new ReentrantLock();
		MyProducer producer = new MyProducer(buffer, ThreadColour.ANSI_RED, bufferLock);
		MyConsumer consumer1 = new MyConsumer(buffer, ThreadColour.ANSI_BLUE, bufferLock);
		MyConsumer consumer2 = new MyConsumer(buffer, ThreadColour.ANSI_GREEN, bufferLock);
		
		new Thread(producer).start();
		new Thread(consumer1).start();
		new Thread(consumer2).start();
	}
}

class MyProducer implements Runnable {
	private List<String> buffer;
	private String colour;
	private ReentrantLock bufferLock;
	
	public MyProducer(List<String> buffer, String colour, ReentrantLock bufferLock) {
		this.buffer = buffer;
		this.colour = colour;
		this.bufferLock = bufferLock;
	}
	
	@Override
	public void run() {
		Random rand = new Random();
		String[] nums = {"1", "2", "3", "4", "5"};
		for (String num : nums) {
			try {
				System.out.println(colour + "adding..." + num);
//				must lock and unlock manually
				bufferLock.lock();
				try {
					buffer.add(num);
				} finally {
					bufferLock.unlock();
				}
				Thread.sleep(rand.nextInt(1000));
			} catch (InterruptedException e) {
				System.out.println("Producer was interrupted");
			}
		}
		System.out.println(colour + "Adding EOF and exiting...");
		bufferLock.lock();
		try {
			buffer.add(EOF);
//			finally, ensures that you only unlock once
		} finally {
			bufferLock.unlock();
		}
	}
}

class MyConsumer implements Runnable {
	private List<String> buffer;
	private String colour;
	private ReentrantLock bufferLock;
	
	public MyConsumer(List<String> buffer, String colour, ReentrantLock bufferLock) {
		this.buffer = buffer;
		this.colour = colour;
		this.bufferLock = bufferLock;
	}
	
	@Override
	
	public void run() {
		int counter = 0;
	
		while (true) {
			if (bufferLock.tryLock()) {
				try {
					if (buffer.isEmpty()) {
						continue;
					}
					System.out.println(colour + "The counter  = " + counter);
					if (buffer.get(0).equals(EOF)) {
//				exiting without removing EOF so other runs don't infinite loops
						System.out.println(colour + "exiting");
						break;
					} else {
						System.out.println(colour + "Removed " + buffer.remove(0));
					}
				} finally {
					bufferLock.unlock();
				}
			} else {
				counter++;
			}
		}
	}
}
