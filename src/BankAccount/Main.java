package BankAccount;

public class Main {
	
	
	public static void main(String[] args) {
		 BankAccount account = new BankAccount("1234-678", 1000.00);
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
			
					
					account.deposit(300.00);
					account.withdraw(50.00);
					System.out.println( account.getBalance());
				
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				
					
					account.deposit(203.75);
					account.withdraw(100.00);
					System.out.println( account.getBalance());
				
			}
		});
		
		t1.start();
		t2.start();
		
	}
	
}
