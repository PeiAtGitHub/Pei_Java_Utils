package com.github.peiatgithub.java.utils;

import static com.github.peiatgithub.java.utils.Constants.*;
import static com.github.peiatgithub.java.utils.Utils.*;

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
	 * Simpler form of calling append(text).times(n) 
	 */
	public JavaRegexBuilder append(String text, int n) {
		return append(text).times(n);
	}
	/**
	 * Simpler form of calling append(text).times(from, to) 
	 */
	public JavaRegexBuilder append(String text, int from, int to) {
		return append(text).times(from, to);
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
	/**
	 * Simpler form of calling literal(literal).times(n) 
	 */
	public JavaRegexBuilder literal(String literal, int n) {
		return literal(literal).times(n);
	}
	/**
	 * Simpler form of calling literal(literal).times(from, to) 
	 */
	public JavaRegexBuilder literal(String literal, int from, int to) {
		return literal(literal).times(from, to);
	}

	/**
	 * . 
	 */
	public JavaRegexBuilder anyChar() {
		return append(".");
	}
	/**
	 * Simpler form of calling anyChar().times(n) 
	 */
	public JavaRegexBuilder anyChar(int n) {
		return anyChar().times(n);
	}
	/**
	 * Simpler form of calling anyChar().times(from, to) 
	 */
	public JavaRegexBuilder anyChar(int from, int to) {
		return anyChar().times(from, to);
	}
	
	/**
	 * \d, a digit: [0-9]
	 */
	public JavaRegexBuilder digit() {
		return append("\\d");
	}
	/**
	 * Simpler form of calling digit().times(n) 
	 */
	public JavaRegexBuilder digit(int n) {
		return digit().times(n);
	}
	/**
	 * Simpler form of calling digit().times(from, to) 
	 */
	public JavaRegexBuilder digit(int from, int to) {
		return digit().times(from, to);
	}
	/**
	 * \\D
	 */
	public JavaRegexBuilder nonDigit() {
		return append("\\D");
	}
	/**
	 * Simpler form of calling nonDigit().times(n) 
	 */
	public JavaRegexBuilder nonDigit(int n) {
		return nonDigit().times(n);
	}
	/**
	 * Simpler form of calling nonDigit().times(from, to) 
	 */
	public JavaRegexBuilder nonDigit(int from, int to) {
		return nonDigit().times(from, to);
	}
	
	/**
	 * \s, whitespace character: [ \t\n\x0B\f\r] 
	 */
	public JavaRegexBuilder whiteSpace() {
		return append("\\s");
	}
	/**
	 * Simpler form of calling whiteSpace().times(n) 
	 */
	public JavaRegexBuilder whiteSpace(int n) {
		return whiteSpace().times(n);
	}
	/**
	 * Simpler form of calling whiteSpace().times(from, to) 
	 */
	public JavaRegexBuilder whiteSpace(int from, int to) {
		return whiteSpace().times(from, to);
	}
	/**
	 * \\S 
	 */
	public JavaRegexBuilder nonWhiteSpace() {
		return append("\\S");
	}
	/**
	 * Simpler form of calling nonWhiteSpace().times(n) 
	 */
	public JavaRegexBuilder nonWhiteSpace(int n) {
		return nonWhiteSpace().times(n);
	}
	/**
	 * Simpler form of calling nonWhiteSpace().times(from, to) 
	 */
	public JavaRegexBuilder nonWhiteSpace(int from, int to) {
		return nonWhiteSpace().times(from, to);
	}
	
	/**
	 * \w, word character: [a-zA-Z_0-9]
	 */
	public JavaRegexBuilder wordChar() {
		return append("\\w");
	}
	/**
	 * Simpler form of calling wordChar().times(n) 
	 */
	public JavaRegexBuilder wordChar(int n) {
		return wordChar().times(n);
	}
	/**
	 * Simpler form of calling wordChar().times(from, to) 
	 */
	public JavaRegexBuilder wordChar(int from, int to) {
		return wordChar().times(from, to);
	}
	/**
	 * \\W
	 */
	public JavaRegexBuilder nonWordChar() {
		return append("\\W");
	}
	/**
	 * Simpler form of calling nonWordChar().times(n) 
	 */
	public JavaRegexBuilder nonWordChar(int n) {
		return nonWordChar().times(n);
	}
	/**
	 * Simpler form of calling nonWordChar().times(from, to) 
	 */
	public JavaRegexBuilder nonWordChar(int from, int to) {
		return nonWordChar().times(from, to);
	}
	
	/**
	 * 26 lower case English letters 
	 */
	public JavaRegexBuilder lowerCaseLetters() {
		return append("[a-z]");
	}
	/**
	 * Simpler form of calling lowerCaseLetters().times(n) 
	 */
	public JavaRegexBuilder lowerCaseLetters(int n) {
		return lowerCaseLetters().times(n);
	}
	/**
	 * Simpler form of calling lowerCaseLetters().times(from, to) 
	 */
	public JavaRegexBuilder lowerCaseLetters(int from, int to) {
		return lowerCaseLetters().times(from, to);
	}
	/**
	 * 26 upper case English letters 
	 */
	public JavaRegexBuilder upperCaseLetters() {
		return append("[A-Z]");
	}
	/**
	 * Simpler form of calling upperCaseLetters().times(n) 
	 */
	public JavaRegexBuilder upperCaseLetters(int n) {
		return upperCaseLetters().times(n);
	}
	/**
	 * Simpler form of calling upperCaseLetters().times(from, to) 
	 */
	public JavaRegexBuilder upperCaseLetters(int from, int to) {
		return upperCaseLetters().times(from, to);
	}
	/**
	 * 26 lower case + 26 upper case English letters 
	 */
	public JavaRegexBuilder lettersIgnoreCase() {
		return append("[a-zA-Z]");
	}
	/**
	 * Simpler form of calling lettersIgnoreCase().times(n) 
	 */
	public JavaRegexBuilder lettersIgnoreCase(int n) {
		return lettersIgnoreCase().times(n);
	}
	/**
	 * Simpler form of calling lettersIgnoreCase().times(from, to) 
	 */
	public JavaRegexBuilder lettersIgnoreCase(int from, int to) {
		return lettersIgnoreCase().times(from, to);
	}

	/**
	 * 26 lower case + 26 upper case English letters + 10 digits(0-9) 
	 */
	public JavaRegexBuilder lettersAndNumbers() {
		return append("[a-zA-Z0-9]");
	}
	/**
	 * Simpler form of calling lettersAndNumbers().times(n) 
	 */
	public JavaRegexBuilder lettersAndNumbers(int n) {
		return lettersAndNumbers().times(n);
	}
	/**
	 * Simpler form of calling lettersAndNumbers().times(from, to) 
	 */
	public JavaRegexBuilder lettersAndNumbers(int from, int to) {
		return lettersAndNumbers().times(from, to);
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
		return append(str("{{}}", n));
	}
	/**
	 * Occurrence times, [from, to]
	 */
	public JavaRegexBuilder times(int from, int to) {
		return append(str("{{},{}}", from, to));
	}
	/**
	 * Occurrence times, at least n times
	 */
	public JavaRegexBuilder atLeast(int n) {
		return append(str("{{},}", n));
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
	public JavaRegexBuilder oneOrMore() {
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
