package Employees;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
		
/*
		How ForEach works
		ForEach wants a consumer.
		a consumer simply takes an argument, and doesn't return anything
		forEach iterates over a list
		the lambda gives an argument to the consumer.accept() interface method. amd runs the code
*/
		System.out.println("Employees over 30:");
		System.out.println("=====================");
		employees.forEach(employee -> {
			if (employee.getAge() > 30) {
				System.out.println(employee.getName());
			}
		});
		
		System.out.println("\nEmployees under 30");
		System.out.println("=====================");
		employees.forEach(employee -> {
			if (employee.getAge() <= 30) {
				System.out.println(employee.getName());
			}
		});
//		employees.forEach(employee -> {
//			System.out.println(employee.getName());
//			System.out.println(employee.getAge());
//		});
//		you set the predicate condition by using a lambda, where the method will use it as a test condition on an object which you will use to do your code
		printEmployeesByAge(employees,"Employees over 30",employee ->employee.getAge() >30 );
		printEmployeesByAge(employees,"\nEmployees under 30",employee ->employee.getAge() <=30 );
	}
	
	public static void printEmployeesByAge(List<Employee> employees, String ageText, Predicate<Employee> ageCondition) {
		System.out.println("Employees over 30:");
		System.out.println("=====================");
		for (Employee employee : employees) {
			if (ageCondition.test(employee)) {
				System.out.println(employee.getName());
			}
		}
	}
}

