package LambdaExpressions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static LambdaExpressions.Main.doStringStuff;

public class Main {
	public static void main(String[] args) {
//		lambda have 3 parts; argument list, error token and body
//		JVM knows that thread constructor accepts a runnable, runnable interface has only 1 method(which is run that doesn't take any parameters)
//		since the argument list is empty, it matches that with the runnable method run that also has no arguments, and put the body into the body of the method
//		lambda expressions can only be used in interfaces that only contain one method that has to be implemented
//		so in our example, the Runnable interface contains only the run method that must be implemented, so lambda expression can successfully match it
		new Thread(() -> {
			System.out.println("Printing from the Runnable");
			System.out.println("Line 2");
			System.out.printf("This is line %d\n", 3);
			
		}).start();
		
		Employee john = new Employee("John Doe", 30);
		Employee tim = new Employee("Tim Buchalka", 21);
		Employee jack = new Employee("Jack Hill", 40);
		Employee snow = new Employee("Snow White", 22);
		
		List<Employee> employees = new ArrayList<>();
		employees.add(john);
		employees.add(tim);
		employees.add(jack);
		employees.add(snow);

//		Collections.sort(employees, new Comparator<Employee>() {
//			@Override
//			public int compare(Employee employee1, Employee employee2) {
//				return employee1.getName().compareTo(employee2.getName());
//			}
//		});

//		Lambda replaces the entire lambda replacable functional interface, no need to instantiate, as lambda searches and matches
		Collections.sort(employees, (employee1, employee2) -> employee1.getName().compareTo(employee2.getName()));
		for (Employee employee : employees) {
			System.out.println(employee.getName());
		}
//		define the interface method with a lambda, this is basically an easier to define a method
//		you can only do this, for interfaces with 1 method
		UpperConcat uc = (s1, s2) -> {
			String result = s1.toUpperCase() + s2.toUpperCase();
			return result;
		};
//		using the lambda and then putting in the required data
		String sillyString = doStringStuff(uc, employees.get(0).getName(), employees.get(1).getName());
		System.out.println(sillyString);
		
		AnotherClass anotherClass = new AnotherClass();
		System.out.println(anotherClass.doSomething());
		
	}
	
	public final static String doStringStuff(UpperConcat uc, String s1, String s2) {
		return uc.upperAndConcat(s1, s2);
	}
}

class Employee {
	private String name;
	private int age;
	
	public Employee(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
}

interface UpperConcat {
	public String upperAndConcat(String s1, String s2);
}

class AnotherClass {
	
	public String doSomething() {
//		UpperConcat uc = (s1, s2) -> {
//			System.out.println("The Lambda's class's name is " + getClass().getSimpleName());
//			String result = s1.toUpperCase() + s2.toUpperCase();
//			return result;
//		};
		int i = 0;
		UpperConcat uc = (s1, s2) -> {
			System.out.println("The lambda expression's class is " + getClass().getSimpleName());
			System.out.println("I within lambda " + i);
			String result = s1.toUpperCase() + s2.toUpperCase();
			return result;
		};

//			UpperConcat uc = new UpperConcat() {
//				@Override
//				public String upperAndConcat(String s1, String s2) {
////					for anonymous classes, the value is copied into a variable that the anon class holds, which isn't the local variable outside
////					you cannot update it because the i that exists inside a anon class is a clone
//					System.out.println("I within anon class block " + i);
//					return s1.toUpperCase() + s2.toUpperCase();
//				}
//			};
		System.out.println("The AnotherClass class's name is " + getClass().getSimpleName());
//		i++;
//		System.out.println("i =  " + i);
		return Main.doStringStuff(uc, "String1", "String2");

//		System.out.println("The AnotherClass class's name is " + getClass().getSimpleName());
//		return Main.doStringStuff(new UpperConcat() {
//			@Override
//			public String upperAndConcat(String s1, String s2) {
//				System.out.println("The AnotherClass class's name is " + getClass().getSimpleName());
//				return s1.toUpperCase() + s1.toUpperCase();
//			}
//		}, "String1", "String2");
	}
	
}
