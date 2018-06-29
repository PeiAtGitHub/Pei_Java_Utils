package com.github.peiatgithub.java.utils.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.peiatgithub.java.utils.Encloser;

/**
 * 
 * @author pei
 *
 */
public class EncloserTests {
    
    @Test
    public void confirmEncloserEnum() throws Exception {

        assertThat(Encloser.SINGLE.begin(), is("'"));
        assertThat(Encloser.SINGLE.end(), is("'"));

        assertThat(Encloser.DOUBLE.begin(), is("\""));
        assertThat(Encloser.DOUBLE.end(), is("\""));
        
        assertThat(Encloser.PARENTHESES.begin(), is("("));
        assertThat(Encloser.PARENTHESES.end(), is(")"));

        assertThat(Encloser.BRACES.begin(), is("{"));
        assertThat(Encloser.BRACES.end(), is("}"));
        
        assertThat(Encloser.BRACKETS.begin(), is("["));
        assertThat(Encloser.BRACKETS.end(), is("]"));
        
        assertThat(Encloser.GUILLEMET.begin(), is("<<"));
        assertThat(Encloser.GUILLEMET.end(), is(">>"));
                
        assertThat(Encloser.SINGLE_GUILLEMET.begin(), is("<"));
        assertThat(Encloser.SINGLE_GUILLEMET.end(), is(">"));
        
        assertThat(Encloser.EMPTY.begin(), is(""));
        assertThat(Encloser.EMPTY.end(), is(""));

    }

}
