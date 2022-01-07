package Employees;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;


public class Main {
	public static void main(String[] args) {
		
		Employee john = new Employee("John Doe", 30);
		Employee tim = new Employee("Tim Buchalka", 21);
		Employee jack = new Employee("Jack Hill", 40);
		Employee snow = new Employee("Snow White", 22);
		Employee red = new Employee("Red Riding Hood", 35);
		Employee charming = new Employee("Prince Charming", 31);
		
		List<Employee> employees = new ArrayList<>();
		employees.add(john);
		employees.add(tim);
		employees.add(jack);
		employees.add(snow);
		employees.add(red);
		employees.add(charming);

//		by writing functions, you can do similar calculations on code, without having to use interfaces to employ methods
//		by passing functions with the same generics parameters, but different names, a method can accept
		
		Function<Employee, String> getLastName = (Employee employee) -> employee.getName().substring(employee.getName().indexOf(" ") + 1);
		
		
		String lastname = getLastName.apply(employees.get(1));
		System.out.println(lastname);
		
		Function<Employee, String> getFirstName = (Employee employee) -> employee.getName().substring(0, employee.getName().indexOf(" "));
		

		
		Random random = new Random();
		for (Employee employee : employees) {
			if (random.nextBoolean()) {
				System.out.println(getAName(getFirstName, employee));
			} else {
				System.out.println(getAName(getLastName, employee));
			}
		}
		
//		UnaryOperators take an argument and return a value of the same type as the argument
		
		IntUnaryOperator intBy5 = i -> i +5;
		System.out.println(intBy5.applyAsInt(10));
		
		Consumer<String> c1 = s -> s.toUpperCase();
		Consumer<String> c2  = s -> System.out.println(s);
//		accept runs on the method that called the andThen, not the argument inside the andThen
		c1.andThen(c2).accept("Hello, World!");
/*

		How ForEach works
		ForEach wants a consumer.
		a consumer simply takes an argument, and doesn't return anything
		forEach iterates over a list
		the lambda gives an argument to the consumer.accept() interface method. amd runs the code
*/
//		System.out.println("Employees over 30:");
//		System.out.println("=====================");
//		employees.forEach(employee -> {
//			if (employee.getAge() > 30) {
//				System.out.println(employee.getName());
//			}
//		});
//
//		System.out.println("\nEmployees under 30");
//		System.out.println("=====================");
//		employees.forEach(employee -> {
//			if (employee.getAge() <= 30) {
//				System.out.println(employee.getName());
//			}
//		});
////		employees.forEach(employee -> {
////			System.out.println(employee.getName());
////			System.out.println(employee.getAge());
////		});
////		you set the predicate condition by using a lambda, where the method will use it as a test condition on an object which you will use to do your code
//		printEmployeesByAge(employees,"Employees over 30",employee ->employee.getAge() >30 );
//		printEmployeesByAge(employees,"\nEmployees under 30",employee ->employee.getAge() <=30 );
//		printEmployeesByAge(employees, "Employees under 25", new Predicate<Employee>() {
//			@Override
//			public boolean test(Employee employee) {
//				return employee.getAge() > 30;
//			}
//		});
//		IntPredicate greaterThan15 = i -> i > 15;
//		IntPredicate lessThan100 = i -> i < 100;
////		predicates are basically boolean test conditions that you set
////		you can chain them using and
//		System.out.println(greaterThan15.test(10));
//		int a = 110;
//		System.out.println(greaterThan15.and(lessThan100).test(a));
//
//		Random random = new Random();
//		Supplier<Integer> randomSupplier = () -> random.nextInt(1000);
//		for(int i = 0; i < 10; i++){
////			System.out.println(random.nextInt(1000));
//			System.out.println(randomSupplier.get());
//		}
//
//		employees.forEach(employee -> {
//			String lastName = employee.getName().substring(employee.getName().indexOf(" ") + 1);
//			System.out.println("Last name is " + lastName);
//		});
	}
	
	private static String getAName(Function<Employee, String> getName, Employee employee) {
		return getName.apply(employee);
	}
	
	public static void printEmployeesByAge(List<Employee> employees, String ageText, Predicate<Employee> ageCondition) {
		System.out.println(ageText);
		System.out.println("=====================");
		for (Employee employee : employees) {
			if (ageCondition.test(employee)) {
				System.out.println(employee.getName());
			}
		}
	}
}

