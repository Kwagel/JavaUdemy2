package Messages;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
	
	}
}

class Message {
	private String message;
	private Boolean empty = true;
	
	public synchronized String read() {
		while (empty) {
		
		}
		empty = true;
		return message;
	}
	
	public synchronized void write(String message) {
		while (!empty) {
		
		}
		empty = false;
		this.message = message;
	}
}

class Writer implements Runnable {
	private Message message;
	
	public Writer(Message message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		String[] messages = {
				"Humpty Dumpty sat on a wall",
				"Humpty Dumpty had a great fall",
				"All the king's horses and all the king's men",
				"Couldn't put Humpty together again",
		};
		Random random = new Random();// allows us to generate random delays
		
		for (int i = 0; i < messages.length; i++) {
			message.write(messages[i]);
			try{
				Thread.sleep(random.nextInt(2000));
			}catch (InterruptedException e){
				System.out.println();
			}
		}
		message.write("Finished");
	}
}