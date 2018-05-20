package com.github.peiatgithub.java.utils;

/**
 * <pre>
 * This class helps you judge if a piece of code has been reached.
 * 
 * Make sure to run RunFlag.reset() before use it.
 * Put RunFlag.run() where you want to see if the code can be reached.
 * Then RunFlag.hasRun() or RunFlag.runTimes() tell you if or how many times 
 * RunFlag.run() has been invoked since reset(). 
 * 
 * TODO: The current implementation is very simple and there are a lot to improve.
 *  (Eg. multi-threading issue, API usability issue, etc.)
 *  
 * </pre>
 * @author pei
 * @since 1.0
 *
 */
public class RunFlag {
	
	private static int counter = 0;
	
	/**
	 * Put this method call to where you what to see if the code will be reached.
	 */
	public static void run() {
		incrementCounter();
	}

	/**
	 * Tells you if RunFlag.run() has been invoked.
	 */
	public static boolean hasRun() {
		return runTimes() > 0;
	}

	/**
	 * Returns how many times RunFlag.run() has been invoked.
	 */
	public static int runTimes() {
		return getCounter();
	}
	
	/**
	 * Reset the flag, then hasRun() will return false.
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
