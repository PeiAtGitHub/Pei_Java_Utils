package com.github.peiatgithub.java.utils;

import static com.github.peiatgithub.java.utils.Constants.*;

/**
 * 
 * @author pei
 *
 * @since 4.0
 * 
 */
public class JavaRegexBuilder {

	private StringBuilder regex = new StringBuilder(EMPTY);

	/**
	 * @return a new instance of the builder
	 */
	public static JavaRegexBuilder newBuilder() {
		return new JavaRegexBuilder();
	}

	/**
	 * <pre>
	 * Append text to the regex.
	 * NOTE: User must handle char escape on their own.
	 * </pre>
	 */
	public JavaRegexBuilder append(String text) {
		this.regex.append(text);
		return this;
	}
	/**
	 * <pre>
	 * Append literal to the regex.
	 * NOTE: User must handle char escape on their own.
	 * </pre> 
	 */
	public JavaRegexBuilder literal(String literal) {
		return append(literal);
	}

	
	public JavaRegexBuilder anyChar() {
		return append(".");
	}
	
	/**
	 * \d, a digit: [0-9]
	 */
	public JavaRegexBuilder digit() {
		return append("\\d");
	}
	/**
	 * \\D
	 */
	public JavaRegexBuilder nonDigit() {
		return append("\\D");
	}
	
	/**
	 * \s, whitespace character: [ \t\n\x0B\f\r] 
	 */
	public JavaRegexBuilder whiteSpace() {
		return append("\\s");
	}
	/**
	 * \\S 
	 */
	public JavaRegexBuilder nonWhiteSpace() {
		return append("\\S");
	}
	
	/**
	 * \w, word character: [a-zA-Z_0-9]
	 */
	public JavaRegexBuilder wordChar() {
		return append("\\w");
	}
	/**
	 * \\W
	 */
	public JavaRegexBuilder nonWordChar() {
		return append("\\W");
	}
	
	/**
	 * 26 lower case English letters 
	 */
	public JavaRegexBuilder lowerCaseLetters() {
		return append("[a-z]");
	}
	/**
	 * 26 upper case English letters 
	 */
	public JavaRegexBuilder upperCaseLetters() {
		return append("[A-Z]");
	}
	/**
	 * 26 lower case + 26 upper case English letters 
	 */
	public JavaRegexBuilder lettersIgnoreCase() {
		return append("[a-zA-Z]");
	}

	/**
	 * 26 lower case + 26 upper case English letters + 10 digits(0-9) 
	 */
	public JavaRegexBuilder lettersAndNumbers() {
		return append("[a-zA-Z0-9]");
	}
	
	/**
	 * <pre>
	 * Specify the Java Regex char classes on your own.
	 * This method is effectively equivalent to the method append(String text)
	 *  Examples:
	 * 	[abc]			a, b, or c (simple class)
	 *	[^abc]			Any character except a, b, or c (negation)
	 *	[a-zA-Z]		a through z or A through Z, inclusive (range)
	 *	[a-d[m-p]]		a through d, or m through p: [a-dm-p] (union)
	 *	[a-z&&[def]]	d, e, or f (intersection)
	 *	[a-z&&[^bc]]	a through z, except for b and c: [ad-z] (subtraction)
	 *	[a-z&&[^m-p]]	a through z, and not m through p: [a-lq-z](subtraction)
	 * </pre>
	 */
	public JavaRegexBuilder charClasses(String charClass) {
		return append(charClass);
	}
	
	/**
	 * Occurrence times 
	 */
	public JavaRegexBuilder times(int n) {
		return append(String.format("{%d}", n));
	}
	/**
	 * Occurrence times, [from, to]
	 */
	public JavaRegexBuilder times(int from, int to) {
		return append(String.format("{%d,%d}", from, to));
	}
	/**
	 * Occurrence times, at least n times
	 */
	public JavaRegexBuilder atLeast(int n) {
		return append(String.format("{%d,}", n));
	}
	/**
	 * Occurrence times, 0 or 1
	 */
	public JavaRegexBuilder zeroOrOne() {
		return append("?");
	}
	/**
	 * Occurrence times, 1 or more
	 */
	public JavaRegexBuilder atLeastOne() {
		return append("+");
	}
	/**
	 * Occurrence times, 0 or more
	 */
	public JavaRegexBuilder zeroOrMore() {
		return append("*");
	}

	public JavaRegexBuilder lineBegin() {
		return append("^");
	}
	public JavaRegexBuilder lineEnd() {
		return append("$");
	}

	
	public JavaRegexBuilder reluctant() {
		return append("?");
	}


	/**
	 * returns the build result regex String 
	 */
	public String build() {
		return this.regex.toString();
	}
	
	
}
