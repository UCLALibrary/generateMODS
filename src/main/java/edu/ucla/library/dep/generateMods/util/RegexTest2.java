package edu.ucla.library.dep.generateMods.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTest2 {

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
