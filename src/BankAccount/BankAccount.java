package BankAccount;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
	private String accountNumber;
	private double balance;
	private Lock lock;
	
	public BankAccount(String accountNumber, double balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		lock = new ReentrantLock();
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
		try {
			if (lock.tryLock(1000, TimeUnit.MILLISECONDS)){
				try {
					
					balance += amount;
				} finally {
					
					lock.unlock();
				}
			}else{
				System.out.println("Could not get the lock");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void withdraw(double amount) {
		try {
			if (lock.tryLock(1000,TimeUnit.MILLISECONDS)){
			try {
				balance -= amount;
			} finally {
				lock.unlock();
			}
			}else{
				System.out.println("Could not get the lock");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void printAccountNumber() {
		System.out.println("Account number = " + accountNumber);
		
	}
	
	public double getBalance() {
		return balance;
	}
}
