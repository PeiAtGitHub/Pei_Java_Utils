package com.github.peiatgithub.java.utils;

/**
 * The char pair for enclosing a String.
 * 
 * @author pei
 * @since 5.0
 */
public enum Encloser {

    /** Single quote '' */
    SINGLE("'", "'"),
    
    /** Double quote "" */
    DOUBLE("\"", "\""),

    /** Round brackets or parentheses: () */
    PARENTHESES("(", ")"),

    /** Curly brackets or braces: {} */
    BRACES("{", "}"),

    /** Square brackets or simply brackets (US): [] */
    BRACKETS("[", "]"),

    /** Guillemet, or angle quotes: <<>> */
    GUILLEMET("<<", ">>"),

    /** Single Guillemet: <> */
    SINGLE_GUILLEMET("<", ">"),

    /** No quote */
    EMPTY("", "");

    private final String begin;
    private final String end;

    Encloser(String begin, String end) {
        this.begin = begin;
        this.end = end;
    }

    public String begin() {
        return begin;
    }

    public String end() {
        return end;
    }
}
