package Streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
	
	public static void main(String[] args) {
		List<String> someBingoNumbers = Arrays.asList(
				"N40", "N36",
				"B12", "B6",
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


//	 must not need other variable states outside the chain,
//		:: is a method reference, which just calls the corresponding method from the object before it,
//		you can only put method references in places where they expect a function
//		sorted() uses natural order normally, which is what you expect.
//		all these methods are part of the stream class, and are not the same as the default filters and for each method
//		Class::Method
//		instead of (String s ) -> System.out.println(s)
//		they are equivalent, but 38 explains the lambda mapping, while class::method is a shortcut
		someBingoNumbers.stream().map(String::toUpperCase).filter(s -> s.startsWith("G")).sorted().forEach(System.out::println);

//		The forEach() doesn't return a value, so it must be a terminal operation, and no further chaining can occur
		Stream<String> ioNumberStream = Stream.of("I26", "I17", "I29", "O71");
		Stream<String> inNumberStream = Stream.of("N40", "N36", "I26", "I29", "O71");
		Stream<String> concatStream = Stream.concat(ioNumberStream, inNumberStream);
		System.out.println("--------------------");
//		peek is a intermediate method, so can be placed before a terminal output and still let the chain continue, as it takes and returns the same integer, but you can use methods references on it beforehand , or a lambda method
		System.out.println(concatStream.distinct().peek(System.out::println).count());
	}
}
