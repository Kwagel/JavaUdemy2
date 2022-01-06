package ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static ProducerConsumer.Main.EOF;


public class Main {
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
				buffer.add(num);
				bufferLock.unlock();
				Thread.sleep(rand.nextInt(1000));
			} catch (InterruptedException e) {
				System.out.println("Producer was interrupted");
			}
		}
		System.out.println(colour + " Adding EOF and exiting...");
		bufferLock.lock();
		buffer.add("EOF");
		bufferLock.unlock();
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
		while (true) {
			bufferLock.lock();
			if (buffer.isEmpty()) {
				bufferLock.unlock();
				continue;
			}
			if (buffer.get(0).equals(EOF)) {
//				exiting without removing EOf so other runs don't infinite loops
				System.out.println(colour + "exiting");
				bufferLock.unlock();
				break;
			} else {
				System.out.println(colour + "Removed " + buffer.remove(0));
				
			}
			bufferLock.unlock();
		}
	}
}
