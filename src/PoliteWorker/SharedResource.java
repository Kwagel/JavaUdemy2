package PoliteWorker;


public class SharedResource {
	private Worker owner;
	
	public SharedResource(Worker owner) {
		this.owner = owner;
	}
	
	public Worker getOwner() {
		return owner;
	}
//	when changing data, you don't want any thread interference
	public synchronized void setOwner(Worker owner) {
		this.owner = owner;
	}
}
