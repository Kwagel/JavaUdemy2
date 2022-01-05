package Messages;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
//		when one methodd is waiting for access of another variable, this is known as a deadlock
//		atomic operations that cannot be stopped by a thread
//		reading or writing reference variables, reading or writing primitive variables except double or long(which can be split between two runs), and volatile variables
		Message message = new Message();
//		some collections aren't thread safe, not natively synchronised, so you must synchronise it when using it
		new Thread(new Writer(message)).start();
		new Thread(new Reader(message)).start();
	}
}

class Message {
	private String message;
	private Boolean empty = true;
	
	public synchronized String read() {
		while (empty) {
			try {
//				when not using the message variable, it waits and releases the lock to allow other threads to use it
				wait();
			} catch (InterruptedException e) {
				System.out.println();
			}
		}
		empty = true;
		notifyAll();
		return message;
	}
	
	public synchronized void write(String message) {
//		the wait() is in a while loop testing for the condition that the method is waiting for, threads can be woken up without fulfilling conditions, so it must be constantly tested for while the thread is awake
		while (!empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println();
			}
		}
		empty = false;
//		you cannot notify a specific thread( notify doesn't accept any parameters
		notifyAll();
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
			try {
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {
				System.out.println();
			}
		}
		message.write("Finished");
	}
}

class Reader implements Runnable {
	private Message message;
	
	public Reader(Message message) {
		this.message = message;
	}
	
	public void run() {
		Random random = new Random();
		for (String latestMessage = message.read(); !latestMessage.equals("Finished"); latestMessage = message.read()) {
			System.out.println(latestMessage);
			try {
				Thread.sleep(random.nextInt(2000));
				
			} catch (InterruptedException e) {
				System.out.println();
			}
		}
	}
}