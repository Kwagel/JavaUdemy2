package BankAccount;

public class BankAccount {
	private String accountNumber;
	private double balance;
	
	public BankAccount(String accountNumber, double balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	
	//	public synchronized void deposit(double amount){
//
//		balance += amount;
//	}
//
//	public synchronized void withdraw(double amount){
//		balance -= amount;
//	}
	public void deposit(double amount) {
//		this method is better when the code is less simple and only critical code need to be synchronised instead of the entre method
//		this is to ensure best performance
		synchronized (this) {
			
			balance += amount;
		}
	}
	
	public void withdraw(double amount) {
		synchronized (this) {
			balance -= amount;
			
		}
	}
	
	public double getBalance() {
		return balance;
	}
}
