package ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ProducerConsumer.Main.EOF;


public class Main {
	public static final String EOF = "EOF";
	
	public static void main(String[] args) {
		List<String> buffer = new ArrayList<>();
		MyProducer producer = new MyProducer(buffer,ThreadColour.ANSI_RED);
		MyConsumer consumer1 = new MyConsumer(buffer,ThreadColour.ANSI_BLUE);
		MyConsumer consumer2 = new MyConsumer(buffer,ThreadColour.ANSI_GREEN);
		
		new Thread(producer).start();
		new Thread(consumer1).start();
		new Thread(consumer2).start();
	}
}

class MyProducer implements Runnable {
	private List<String> buffer;
	private String colour;
	
	public MyProducer(List<String> buffer, String colour) {
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
				buffer.add(num);
				
				Thread.sleep(rand.nextInt(1000));
			} catch (InterruptedException e) {
				System.out.println("Producer was interrupted");
			}
		}
		System.out.println(colour + " Adding EOF and exiting...");
		buffer.add("EOF");
	}
}

class MyConsumer implements Runnable {
	private List<String> buffer;
	private String colour;
	
	public MyConsumer(List<String> buffer, String colour) {
		this.buffer = buffer;
		this.colour = colour;
	}
	
	@Override
	
	public void run() {
		while (true) {
			if (buffer.isEmpty()) {
				continue;
			}
			if (buffer.get(0).equals(EOF)) {
//				exiting without removing EOf so other runs don't infinite loops
				System.out.println(colour + "exiting");
				break;
			} else {
				System.out.println(colour + "Removed " + buffer.remove(0));
			}
		}
	}
}