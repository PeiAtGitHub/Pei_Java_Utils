package com.github.peiatgithub.java.utils.tests;

import com.github.peiatgithub.java.utils.JavaRegexBuilder;
import static org.junit.Assert.*;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static com.github.peiatgithub.java.utils.Constants.*;

/**
 * 
 * @author pei
 *
 */
public class JavaRegexBuilderTests {
	
	@Test
	public void javaRegexBuilderTest() throws Exception {
		
		assertThat(getRegexBuilder().anyChar().zeroOrMore().build(), is(".*"));
		assertThat(getRegexBuilder().append(STR, 3).append(STR, 3, 6).build(), is("STR{3}STR{3,6}"));
		assertThat(getRegexBuilder().literal(STR, 3).literal(STR, 3, 6).build(), is("STR{3}STR{3,6}"));
		assertThat(getRegexBuilder().anyChar(3).anyChar(3, 6).build(), is(".{3}.{3,6}"));
		assertThat(getRegexBuilder().digit(13).nonDigit(7, 8).build(), is("\\d{13}\\D{7,8}"));
		assertThat(getRegexBuilder().digit(3, 6).build(), is("\\d{3,6}"));
		assertThat(getRegexBuilder().nonDigit(3).build(), is("\\D{3}"));
		assertThat(getRegexBuilder().whiteSpace().atLeast(1).nonWhiteSpace().zeroOrOne().build(), is("\\s{1,}\\S?"));
		assertThat(getRegexBuilder().whiteSpace(3).whiteSpace(3, 6).build(), is("\\s{3}\\s{3,6}"));
		assertThat(getRegexBuilder().nonWhiteSpace(3).nonWhiteSpace(3, 6).build(), is("\\S{3}\\S{3,6}"));
		assertThat(getRegexBuilder().wordChar(8).nonWordChar(8).build(), is("\\w{8}\\W{8}"));
		assertThat(getRegexBuilder().wordChar(3, 6).build(), is("\\w{3,6}"));
		assertThat(getRegexBuilder().nonWordChar(3, 6).build(), is("\\W{3,6}"));
		assertThat(getRegexBuilder().lowerCaseLetters().upperCaseLetters().lettersIgnoreCase().lettersAndNumbers()
				.build(), is("[a-z][A-Z][a-zA-Z][a-zA-Z0-9]"));
		assertThat(getRegexBuilder().lowerCaseLetters(3).lowerCaseLetters(3, 6).build(), is("[a-z]{3}[a-z]{3,6}"));
		assertThat(getRegexBuilder().upperCaseLetters(3).upperCaseLetters(3, 6).build(), is("[A-Z]{3}[A-Z]{3,6}"));
		assertThat(getRegexBuilder().lettersIgnoreCase(3).lettersIgnoreCase(3, 6).build()
				, is("[a-zA-Z]{3}[a-zA-Z]{3,6}"));
		assertThat(getRegexBuilder().lettersAndNumbers(3).lettersAndNumbers(3, 6).build()
				, is("[a-zA-Z0-9]{3}[a-zA-Z0-9]{3,6}"));
		assertThat(getRegexBuilder().charClasses("[a-dm-p]").build(), is("[a-dm-p]"));
		assertThat(getRegexBuilder().lineBegin().anyChar().oneOrMore().literal("Hello world!").digit().reluctant()
				.lineEnd().build(), is("^.+Hello world!\\d?$"));
		
	}

	private JavaRegexBuilder getRegexBuilder() {
		return JavaRegexBuilder.newBuilder();
	}

}
