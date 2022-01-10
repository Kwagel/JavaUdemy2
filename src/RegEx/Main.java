package RegEx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		String string = "I am a string. Yes, I am.";
		System.out.println(string);
		String yourString = string.replaceAll("I", "You");
		System.out.println(yourString);
		
		String alphaNumeric = "abcDeeeF12Ghiiiijkl99z";
		String alphaNumeric2 = "abcDeeeF12Ghiiiijkl99zabcDeee";
//		. matches any character.
		System.out.println(alphaNumeric.replaceAll(".", "Y"));
//		^ denotes must exactly match at the start of the string. called a boundary matcher
		System.out.println(alphaNumeric2.replaceAll("^abcDeee", "YYY"));
//		for matches, the entire string must match
		System.out.println(alphaNumeric.matches("^abcDeee"));
		System.out.println(alphaNumeric.matches("^abcDeeeF12Ghiiiijkl99z"));

//		$ denotes must exactly match at the end of the string, another boundary matcher.
		System.out.println(alphaNumeric.replaceAll("ijkl99z$", "THE END"));
//		any individual character inside will be replaced inside []
		System.out.println(alphaNumeric.replaceAll("[aei]", "X"));
		System.out.println(alphaNumeric.replaceAll("[aei]", "I replaced a letter here"));
//		if using multiple, it works as anything inside the first[] followed by anything inside the second [] matches
//		so aF | eF | iF | aj | ej | ij matches
		System.out.println(alphaNumeric.replaceAll("[aei][Fj]", "X"));
//		you can use [] to match individual capital letters pairs easily
		System.out.println("harry".replaceAll("[Hh]arry", "Harry"));
//		if you want to replace anything but certain characters, inside [], if ^ is put first, it is a character class, not a boundary matcher
//		it negates the pattern that follows it, so ^ef means any character(anything) except e or j match
		System.out.println(alphaNumeric.replaceAll("[^ej]", "X"));
//		regEx are case sensitive (D and F weren't replaced)
		System.out.println(alphaNumeric2.replaceAll("[abcdef345678]", "X"));
//		this is a shorter way to write the line about using - between a range of characters so [a-e] means a | b | c | d | e
//		only cares about 1 character on each side
		System.out.println(alphaNumeric2.replaceAll("[a-f3-8]", "X"));
//		to include capital letters, you need to add another range just for capital letters so [a-eA-E] is a | A |  b | B | c | C | d | D | e| E
		System.out.println(alphaNumeric2.replaceAll("[a-fA-F3-8]", "X"));
//		You can use the special construct (?i) to ignore case sensitivity so (?i)[a-e] is the same as [a-eA-e]
//		(?iu) is ignore case sensitivity for unicode characters
		System.out.println(alphaNumeric2.replaceAll("(?i)[a-f3-8]", "X"));
//		replacing all numbers is [0-9]
		System.out.println(alphaNumeric2.replaceAll("[0-9]", "X"));
//		or using \\d the first \ escapes the \ so it can be read
		System.out.println(alphaNumeric2.replaceAll("\\d", "X"));
//		for all non digits the shortcut is similar with \\D
		System.out.println(alphaNumeric2.replaceAll("\\D", "X"));
		
		String hasWhitespace = "I have blanks and\ta tab, and also a newline\n";
		System.out.println(hasWhitespace);
//		\\s removes all white space, including new lines and tabs
		System.out.println(hasWhitespace.replaceAll("\\s", ""));
//		it follows convention for tab escapes and new line escapes so \\t
		System.out.println(hasWhitespace.replaceAll("\\t", "X"));
//		and \\n
		System.out.println(hasWhitespace.replaceAll("\\n", "X"));
//		as with the number convention \\d and \\D is opposite, ans \\S removes everything but black space
		System.out.println(hasWhitespace.replaceAll("\\S", ""));
//		\\w matches a-z, A-z, 0-9 and _
		System.out.println(alphaNumeric2.replaceAll("\\w", "X"));
//		doesn't replace white space
		System.out.println(hasWhitespace.replaceAll("\\w", "X"));
//		same with \\W as above inverted
		System.out.println(hasWhitespace.replaceAll("\\W", "X"));
//		\\b matches word boundaries denoted by whitespace, so around every word
		System.out.println(hasWhitespace.replaceAll("\\b", "X"));

//		QUANTIFIERS
		String thirdAlphanumericString = "abcDeeeF12Ghiiiijkl99z";
//		{n} denotes the amount of times proceeding character must occur to match
//		"eee" == "e{3}"
		System.out.println(thirdAlphanumericString.replaceAll("^abcDe{3}", "YYY"));
//		+ means any amount of the preceding character will be a match- one or more
//		"e" == "ee" == "eee" == "eeee" etc
		System.out.println(thirdAlphanumericString.replaceAll("^abcDe+", "YYY"));
//		* denotes 0 or more of the preceding character makes it match- n>= 0
//		basically, it doesn't care if it does or doesn't have the preceding character , its optional, but will still match if included
		System.out.println(thirdAlphanumericString.replaceAll("^abcDe*", "YYY"));
//		you can also define a range for {n} as {n,m} for how many chapters it's between to match
//		e{2,5} == "ee" == "eee" == "eeee" == "eeeee"
		System.out.println(thirdAlphanumericString.replaceAll("^abcDe{2,5}", "YYY"));
//		so this means atleast 1 h, any amount of i's followed by a j
		System.out.println(thirdAlphanumericString.replaceAll("h+i*j", "Y"));
		
		StringBuilder htmlTest = new StringBuilder("<h1>My Heading </h>");
		htmlTest.append("<h2>Sub-heading</h2>");
		htmlTest.append("<p>This is a paragraph about something.</p>");
		htmlTest.append("<p>This is another paragraph about something else.</p>");
		htmlTest.append("<h2>Summary</h2>");
		htmlTest.append("<p>Here is the summary</p>");

//		using .* then what you are looking for surrounded by another .* means you will match any occurrence no matter where it is
//		parentheses defines a group which allows for further functionality
		String h2Pattern = "(<h2>)";
		Pattern pattern = Pattern.compile(h2Pattern);
		Matcher matcher = pattern.matcher(htmlTest);
		System.out.println(matcher.matches());
//		matchers have an internal state that shows that it has been used, which has to be reset after each use
		int count = 0;
		matcher.reset();
		while (matcher.find()) {
			count++;
			System.out.println("Occurrences : " + count + ": " + matcher.start() + ": " + matcher.end());
		}
//		* is a greedy quantifier, meaning it keep looking for possible cases that still match it
//		e.g. abcab (a.*b) == abcab or (a.*?b) == ab, ab
//		adding ? after the * makes it a lazy quantifier, so it grabs the minimum required to match the regEx
		String h2GroupPattern = "(<h2>.*?</h2>)";
		Pattern groupPattern = Pattern.compile(h2GroupPattern);
		Matcher groupMatcher = groupPattern.matcher(htmlTest);
		System.out.println(groupMatcher.matches());
		groupMatcher.reset();
		while (groupMatcher.find()) {
//			to use the group, each group is indexed from 1, and you can have multiple groups in one regEx
			System.out.println("Occurrence: " + groupMatcher.group(1));
		}
		
//		String string2 = "abcab";
//		String newPattern = "(a.*?b)";
//		Pattern usePattern = Pattern.compile(newPattern);
//		Matcher useMatcher = usePattern.matcher(string2);
//
//		while (useMatcher.find()){
//			System.out.println(useMatcher.group(1));
//		}
		
		String h2TextGroups = "(<h2>)(.+?)(</h2>)";
		Pattern h2TextPattern = Pattern.compile(h2TextGroups);
		Matcher h2TextMatcher = h2TextPattern.matcher(htmlTest);

		while (h2TextMatcher.find()) {
//			to use the group, each group is indexed from 1, and you can have multiple groups in one regEx
			System.out.println("Occurrence: " + h2TextMatcher.group(2));
		}
	}
	
}
