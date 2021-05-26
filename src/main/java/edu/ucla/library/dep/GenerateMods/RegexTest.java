package edu.ucla.library.dep.GenerateMods;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTest {

	public static void main(String[] args) {
		String text    =
	            "Title | arm";

	       // String patternString = ".*http://.*";
		String patternString="Title.*";

	        Pattern pattern = Pattern.compile(patternString);

	        Matcher matcher = pattern.matcher(text);
	        System.out.println(matcher.matches());

	}

}
