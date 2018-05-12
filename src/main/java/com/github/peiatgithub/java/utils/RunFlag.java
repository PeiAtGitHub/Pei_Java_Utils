package com.github.peiatgithub.java.utils;

/**
 * 
 * This class helps you judge if a piece of code has been reached.
 * 
 * RunFlag.hasRun() tells you if RunFlag.run() has been invoked before. 
 * RunFlag.hasRun() is a one-time utility because it also reset the RunFlag.
 * 
 * The current implementation is very simple and there are a lot TODO to improve this utility.
 *  (Eg. multi-threading security issue, API usability issue, etc.)
 * 
 * @author pei
 * @since 1.0
 *
 */
public class RunFlag {
	
	private static int counter = 0;
	
	/**
	 * Put this method call to where you what to see if the code will be reached.
	 * @return
	 */
	public static void run() {
		incrementCounter();
	}

	/**
	 * Tells you if RunFlag.run() has been invoked AND RESET the RunFlag.
	 * @return
	 */
	public static boolean hasRun() {
		return runTimes() > 0;
	}

	/**
	 * Returns how many times RunFlag.run() has been invoked.
	 * @return
	 */
	public static int runTimes() {
		return getCounter();
	}
	
	/**
	 * Reset the flag, then hasRun() will return false
	 * @return
	 */
	public static void reset() {
		counter = 0;
	}


	/*
	 * 
	 */
	private static void incrementCounter() {
		counter++;
	}

	private static int getCounter() {
		return counter;
	}
	
}
