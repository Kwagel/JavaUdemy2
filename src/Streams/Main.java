package Streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> someBingoNumbers = Arrays.asList(
				"N40", "N36",
				"B12","B6",
				"G53", "G49", "G60", "G50", "g64",
				"I26", "I17", "I29",
				"O71"
		);
		
		List<String> gNumbers = new ArrayList<>();
		
//		someBingoNumbers.forEach(number -> {
//			if (number.toUpperCase().startsWith("G")){
////				works as it gNumbers is effectively final as it is a reference type and within the same method
//				gNumbers.add(number);
//				System.out.println(number);
//
//			}
//		});
//		gNumbers.sort((String s1, String s2) -> s1.compareTo(s2));
//		gNumbers.forEach((String s) -> System.out.println(s));


//	 must not need other varaible states outside the chain,
//		:: is a method reference, which just calls the corresponding method from the object before it
		someBingoNumbers.stream().map(String::toUpperCase).filter(s -> s.startsWith("G")).sorted().forEach(System.out::println);
	}
}
