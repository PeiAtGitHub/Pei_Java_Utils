package com.github.peiatgithub.java.utils.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.peiatgithub.java.utils.Quote;

/**
 * 
 * @author pei
 *
 */
public class QuoteEnumTests {
    
    @Test
    public void confirmQuoteEnum() throws Exception {

        assertThat(Quote.SINGLE.begin(), is("'"));
        assertThat(Quote.SINGLE.end(), is("'"));

        assertThat(Quote.DOUBLE.begin(), is("\""));
        assertThat(Quote.DOUBLE.end(), is("\""));
        
        assertThat(Quote.PARENTHESES.begin(), is("("));
        assertThat(Quote.PARENTHESES.end(), is(")"));

        assertThat(Quote.BRACES.begin(), is("{"));
        assertThat(Quote.BRACES.end(), is("}"));
        
        assertThat(Quote.BRACKETS.begin(), is("["));
        assertThat(Quote.BRACKETS.end(), is("]"));
        
        assertThat(Quote.GUILLEMET.begin(), is("<<"));
        assertThat(Quote.GUILLEMET.end(), is(">>"));
                
        assertThat(Quote.SINGLE_GUILLEMET.begin(), is("<"));
        assertThat(Quote.SINGLE_GUILLEMET.end(), is(">"));
        
        assertThat(Quote.EMPTY.begin(), is(""));
        assertThat(Quote.EMPTY.end(), is(""));

    }

}
