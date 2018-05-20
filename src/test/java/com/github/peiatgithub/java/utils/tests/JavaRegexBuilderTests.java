package com.github.peiatgithub.java.utils.tests;

import com.github.peiatgithub.java.utils.JavaRegexBuilder;
import static org.junit.Assert.*;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * 
 * @author pei
 *
 */
public class JavaRegexBuilderTests {
	
	@Test
	public void javaRegexBuilderTest() throws Exception {
		
		assertThat(getNewJavaRegexBuilder().anyChar().zeroOrMore().build(), is(".*"));
		assertThat(getNewJavaRegexBuilder().digit().times(13).nonDigit().times(7, 8).build(), is("\\d{13}\\D{7,8}"));
		assertThat(getNewJavaRegexBuilder().whiteSpace().atLeast(1).nonWhiteSpace().zeroOrOne().build()
				, is("\\s{1,}\\S?"));
		assertThat(getNewJavaRegexBuilder().wordChar().times(8).nonWordChar().times(8).build(), is("\\w{8}\\W{8}"));
		assertThat(getNewJavaRegexBuilder().lowerCaseLetters().upperCaseLetters().lettersIgnoreCase()
				.lettersAndNumbers().build()
				, is("[a-z][A-Z][a-zA-Z][a-zA-Z0-9]"));
		assertThat(getNewJavaRegexBuilder().charClasses("[a-dm-p]").build(), is("[a-dm-p]"));
		assertThat(getNewJavaRegexBuilder().lineBegin().anyChar().atLeastOne().literal("Hello world!")
				.digit().reluctant().lineEnd().build()
				, is("^.+Hello world!\\d?$"));
		
	}

	private JavaRegexBuilder getNewJavaRegexBuilder() {
		return JavaRegexBuilder.newBuilder();
	}

}
