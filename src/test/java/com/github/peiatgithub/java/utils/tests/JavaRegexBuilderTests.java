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

        assertThat(new JavaRegexBuilder().anyChar().zeroOrMore().build(), is(".*"));
        assertThat(new JavaRegexBuilder().append(STR, 3).append(STR, 3, 6).build(), is("STR{3}STR{3,6}"));
        assertThat(new JavaRegexBuilder().literal(STR, 3).literal(STR, 3, 6).build(), is("STR{3}STR{3,6}"));
        assertThat(new JavaRegexBuilder().anyChar(3).anyChar(3, 6).build(), is(".{3}.{3,6}"));
        assertThat(new JavaRegexBuilder().digit(13).nonDigit(7, 8).build(), is("\\d{13}\\D{7,8}"));
        assertThat(new JavaRegexBuilder().digit(3, 6).build(), is("\\d{3,6}"));
        assertThat(new JavaRegexBuilder().nonDigit(3).build(), is("\\D{3}"));
        assertThat(new JavaRegexBuilder().whiteSpace().atLeast(1).nonWhiteSpace().zeroOrOne().build(),
                is("\\s{1,}\\S?"));
        assertThat(new JavaRegexBuilder().whiteSpace(3).whiteSpace(3, 6).build(), is("\\s{3}\\s{3,6}"));
        assertThat(new JavaRegexBuilder().nonWhiteSpace(3).nonWhiteSpace(3, 6).build(), is("\\S{3}\\S{3,6}"));
        assertThat(new JavaRegexBuilder().wordChar(8).nonWordChar(8).build(), is("\\w{8}\\W{8}"));
        assertThat(new JavaRegexBuilder().wordChar(3, 6).build(), is("\\w{3,6}"));
        assertThat(new JavaRegexBuilder().nonWordChar(3, 6).build(), is("\\W{3,6}"));
        assertThat(new JavaRegexBuilder().lowerCaseLetters().upperCaseLetters().lettersIgnoreCase().lettersAndNumbers()
                .build(), is("[a-z][A-Z][a-zA-Z][a-zA-Z0-9]"));
        assertThat(new JavaRegexBuilder().lowerCaseLetters(3).lowerCaseLetters(3, 6).build(), is("[a-z]{3}[a-z]{3,6}"));
        assertThat(new JavaRegexBuilder().upperCaseLetters(3).upperCaseLetters(3, 6).build(), is("[A-Z]{3}[A-Z]{3,6}"));
        assertThat(new JavaRegexBuilder().lettersIgnoreCase(3).lettersIgnoreCase(3, 6).build(),
                is("[a-zA-Z]{3}[a-zA-Z]{3,6}"));
        assertThat(new JavaRegexBuilder().lettersAndNumbers(3).lettersAndNumbers(3, 6).build(),
                is("[a-zA-Z0-9]{3}[a-zA-Z0-9]{3,6}"));
        assertThat(new JavaRegexBuilder().charClasses("[a-dm-p]").build(), is("[a-dm-p]"));
        assertThat(new JavaRegexBuilder().lineBegin().anyChar().oneOrMore().literal("Hello world!").digit().reluctant()
                .lineEnd().build(), is("^.+Hello world!\\d?$"));

    }

}
