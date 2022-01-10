package RegEx;

public class Main {
	public static void main(String[] args) {
		String string = "I am a string. Yes, I am.";
		System.out.println(string);
		String yourString = string.replaceAll("I", "You");
		System.out.println(yourString);
		
		String alphaNumeric = "abcDeeeF12Ghiiiijkl99z";
		String alphaNumeric2 = "abcDeeeF12Ghiiiijkl99zabcDeee";
//		. matches any character.
		System.out.println(alphaNumeric.replaceAll(".","Y"));
//		^ denotes must exactly match at the start of the string. called a boundary matcher
		System.out.println(alphaNumeric2.replaceAll("^abcDeee","YYY"));
//		for matches, the entire string must match
		System.out.println(alphaNumeric.matches("^abcDeee"));
		System.out.println(alphaNumeric.matches("^abcDeeeF12Ghiiiijkl99z"));
		
//		$ denotes must exactly match at the end of the string, another boundary matcher.
		System.out.println(alphaNumeric.replaceAll("ijkl99z$", "THE END" ));
//		any individual character inside will be replaced inside []
		System.out.println(alphaNumeric.replaceAll("[aei]", "X"));
		System.out.println(alphaNumeric.replaceAll("[aei]", "I replaced a letter here"));
//		if using multiple, it works as anything inside the first[] followed by anything inside the second [] matches
//		so aF | eF | iF | aj | ej | ij matches
		System.out.println(alphaNumeric.replaceAll("[aei][Fj]", "X"));
		
		
	}
	
}
