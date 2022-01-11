package RegexChallenges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		String challenge1 = "I want a bike.";
		String challenge2 = "I want a ball.";
		String stringRegex = "(^[\\D]{0,100}$)";
		System.out.println(challenge1.matches("^I want a b[\\w]{3}\\.$"));
		System.out.println(challenge2.matches("^I want a b[\\w]{3}.$"));
		
		
		String iWantRegex = "I want a \\w+.";
		Pattern iWantPattern = Pattern.compile(iWantRegex);
		Matcher iWantMatcher = iWantPattern.matcher(challenge1);
		System.out.println(iWantMatcher.matches());
		iWantMatcher = iWantPattern.matcher(challenge2);
		System.out.println(iWantMatcher.matches());
		
		String challenge4 = "replace all banks with underscores.";
		System.out.println(challenge4.replaceAll(" ", "_"));
		System.out.println(challenge4.replaceAll("\\s", "_"));
		String challenge5 = "aaabccccccccdddefffg";
		System.out.println(challenge5.matches("a+b+c+d+e+f+g+"));
		System.out.println(challenge5.matches("[abcdefg]+"));
		System.out.println(challenge5.matches("[a-g]+"));
		System.out.println(challenge5.matches("^a{3}bc{8}d{3}ef{3}g$"));
		
		String challenge7 = "abcd.135";
		System.out.println(challenge7.matches("^[a-zA-Z]+.\\d+$"));
		
	}
}