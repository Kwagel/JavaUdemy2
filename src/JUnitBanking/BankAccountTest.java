package JUnitBanking;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
	private BankAccount account;
	private static int count;
	
	@org.junit.jupiter.api.BeforeAll
	public static void beforeClass() {
		System.out.println("This executes before any test cases, Count = " + count++);
	}
	
	
	@org.junit.jupiter.api.BeforeEach
	public void setup() {
		account = new BankAccount("Tim", "Buchalka", 1000.00, BankAccount.CHECKING);
		System.out.println("Running a test...");
	}
	
	
	@org.junit.jupiter.api.Test
	void deposit() {
		double balance = account.deposit(200.00, true);
		assertEquals(1200.00, balance, 0);
	}
	
	@org.junit.jupiter.api.Test
	void withdraw_branch() {
		double balance = account.withdraw(600.00, true);
		assertEquals(400.00, balance, 0);
	}
	
	@org.junit.jupiter.api.Test
	public void withdraw_not_branch() {
		assertThrows(IllegalArgumentException.class, () -> {
			account.withdraw(600.00, false);
		}, "Allowed withdraw of 500 without branch permission, should throw IllegalArgumentException");
		
	}
	
	@org.junit.jupiter.api.Test
	void getBalance_deposit() {
		account.deposit(200.00, true);
		assertEquals(1200.00, account.getBalance(), 0);
	}
	
	@org.junit.jupiter.api.Test
	void getBalance_withdraw() {
		account.withdraw(200.00, true);
		assertEquals(800.00, account.getBalance(), 0);
	}
	
	@org.junit.jupiter.api.Test
	void isChecking_true() {
		assertTrue(account.isChecking(), "The account is NOT a checking account");
	}
	
	@org.junit.jupiter.api.AfterAll
	public static void afterClass() {
		System.out.println("This executes after any test cases, Count = " + count++);
	}
	
	@org.junit.jupiter.api.AfterEach
	public void teardown() {
		System.out.println("Count = " + count++);
	}
	
	
	@org.junit.jupiter.params.ParameterizedTest
	@org.junit.jupiter.params.provider.MethodSource("testConditions")
	void deposit(double amount, boolean branch, double expected) {
		account.deposit(amount, branch);
		assertEquals(expected, account.getBalance(), 0.1);
	}
	
	public static Stream<Arguments> testConditions() {
		return Stream.of(
				Arguments.of(100.00, true, 1100.00),
				Arguments.of(200.00, true, 1200.00),
				Arguments.of(325.14, true, 1325.14),
				Arguments.of(489.33, true, 1489.33),
				Arguments.of(1000.00, true, 2000.00)
		);
	}
	
}