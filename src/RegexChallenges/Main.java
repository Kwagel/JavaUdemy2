package RegexChallenges;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		String challenge1 = "I want a bike.";
		String challenge2 = "I want a ball.";
		
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
		
		
		String challenge8 = "abc.123uvqz.7tzik.999";
//		group the bit you want to output, and select that group, if you don't use boundary markers, it will keep searching the entire string
		String takeNumRegex = "[a-zA-Z]+.(\\d+)";
		Pattern takeNumPattern = Pattern.compile(takeNumRegex);
		Matcher takeNumMatcher = takeNumPattern.matcher(challenge8);
		
		while (takeNumMatcher.find()) {
			System.out.println(takeNumMatcher.group(1));
		}
		
		String challenge9 = "abcd.123\tuvqz.7\ttzik.999\n";
		Pattern chal9 = Pattern.compile("[a-zA-Z]+.(\\d+)\\s");
		Matcher chal9Matcher = chal9.matcher(challenge9);
		while (chal9Matcher.find()) {
			System.out.println(chal9Matcher.group(1));
		}
		
		String challenge10 = "abcd.135\tuvqz.7\ttzik.999\n";
		Pattern chal10 = Pattern.compile("[a-zA-Z]+.(\\d+)\\s");
		Matcher chal10Matcher = chal10.matcher(challenge10);
		while (chal10Matcher.find()) {
			System.out.println("Occurrences " + chal10Matcher.start(1) + " : " + (chal10Matcher.end(1) - 1));
		}
		
		String challenge11 = "{0, 2}, {0, 5}, {1, 3}, {2, 4}";
//		"\\{(\\d, \\d)}"
//		. means anything + means atleast 1, ? makes it lazy to return the shortest match
		Pattern c11Pattern = Pattern.compile("\\{(.+?)}");
		Matcher c11Matcher = c11Pattern.matcher(challenge11);
		while (c11Matcher.find()){
			System.out.println(c11Matcher.group(1));
		}
		
	}
}