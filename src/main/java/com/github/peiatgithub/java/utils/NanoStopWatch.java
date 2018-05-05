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
    
    private NanoStopWatch() {
    	throw new RuntimeException();
    }
    
    /**
     * start running the stop watch
     */
    public static void begin() {
        begin = System.nanoTime();
        running = true;
    }
     
    /**
     * Stop the watch and get the elapsed time since start in milli seconds.
     * If the watch was not started, return 0;
     * @return
     */
    public static long stopAndGetMillis() {
    	
    	return stopAndGetElapsed(TimeUnit.MILLISECONDS);
    	
    }
    
    /**
     * Stop the watch and get the elapsed time since start in nano seconds.
     * If the watch was not started, return 0;
     * @return
     */
    public static long stopAndGetNanos() {
    	
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
