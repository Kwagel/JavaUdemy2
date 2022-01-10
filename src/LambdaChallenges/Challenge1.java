package LambdaChallenges;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Challenge1 {
	Runnable runnable = () -> {
		String myString = "Let's split this up into an array";
		String[] parts = myString.split(" ");
		for (String part : parts) {
			System.out.println(part);
		}
	};
	
	
	public static void main(String[] args) {
		Supplier<String> supplier = () -> "I love Java!";
		String iLoveJava = supplier.get();
		System.out.println(iLoveJava);
		Function<String, String> lambdaFunction = (String s) -> {
			StringBuilder returnVal = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				if (i % 2 == 1) {
					returnVal.append(s.charAt(i));
				}
			}
			return returnVal.toString();
		};


//		BiFunction<Function<String,String>, String, String> everySecondCharacter = (Function<String,String> function , String input) -> {
//			 return function.apply(input);
//
//		};
		String result = lambdaFunction.apply("1234567890");
		System.out.println(result);
		
		System.out.println(everySecondCharacter(lambdaFunction, "1234567890"));
		
		List<String> topNames2015 = Arrays.asList("Amelia", "Olivia", "emily", "Isla", "Ava", "oliver", "Jack", "Charlie", "harry", "Jacob");
		Function<List<String>, List<String>> parseList = (list) -> list.stream()
																   .sorted()
																   .collect(Collectors.toList());
		Function<List<String>, List<String>> firstLetterCapital = (s) -> {
			List<String> upperCaseList = new ArrayList<>();
			s.forEach(name -> upperCaseList.add(name.substring(0, 1)
													.toUpperCase(Locale.ROOT)
													.concat(name.substring(1))));
			
			return parseList.apply(upperCaseList);
		};
		
		System.out.println(firstLetterCapital.apply(topNames2015));
		List<String> upperCaseList = new ArrayList<>();
		topNames2015.forEach(name -> upperCaseList.add(name.substring(0, 1).toUpperCase() + name.substring(1)));
		upperCaseList.sort(String::compareTo);
		upperCaseList.forEach(System.out::println);
		
		long beingsWithA = topNames2015.stream()
									   .map(name -> name.substring(0, 1).toUpperCase() + name.substring(1))
									   .peek(System.out::println)
									   .sorted(String::compareTo)
									   .count();
		System.out.println(beingsWithA);
	}
	
	public static String everySecondCharacter(Function<String, String> function, String input) {
		return function.apply(input);
	}
	
	
}
