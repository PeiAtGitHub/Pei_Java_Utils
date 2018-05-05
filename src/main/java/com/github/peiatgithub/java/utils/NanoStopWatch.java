package com.github.peiatgithub.java.utils;

import java.util.concurrent.TimeUnit;


/**
 * 
 * A stop watch to measure the elapsed time from previous call of begin()
 * to the call of a stopXXX method.
 * 
 * @author pei
 *
 */
public class NanoStopWatch {
    
    private static boolean running = false;
    
    private static long begin;
    
    /**
     * start running the stop watch
     */
    public static void begin() {
        begin = System.nanoTime();
        running = true;
    }
     
    /**
     * Stop running the stop watch and get the elapsed time since start running in milli secondes.
     * If the watch was not started, return 0;
     * @return
     */
    public static long stopAndGetElapsedMillis() {
    	
    	return stopAndGetElapsed(TimeUnit.MILLISECONDS);
    	
    }
    
    /**
     * Stop running the stop watch and get the elapsed time since start running in nano secondes.
     * If the watch was not started, return 0;
     * @return
     */
    public static long stopAndGetElapsedNanos() {
    	
    	return stopAndGetElapsed(TimeUnit.NANOSECONDS);
    	
    }
    
    
    
    private static long stopAndGetElapsed(TimeUnit tu) {
        
        long elapsed = System.nanoTime() - begin;
        
        if (running) {
            running = false;
            switch (tu) {
			case MILLISECONDS:
				return TimeUnit.NANOSECONDS.toMillis(elapsed);
			case NANOSECONDS:
				return elapsed;
			default:
				throw new RuntimeException("Unsupported TimeUnit: " + tu.name());
			}
        }else{ // the stop watch was not running
            return 0;
        }
    
    }
    
}
