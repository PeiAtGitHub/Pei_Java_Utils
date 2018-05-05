package com.github.peiatgithub.java.utils;

import java.text.DecimalFormat;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import com.github.peiatgithub.java.utils.function.NonArgFunction;

import static com.github.peiatgithub.java.utils.Constants.*;

/**
 * 
 * @author pei
 * @since 1.0
 */
public class Utils {
        

    /**
     *  println the message with the thread name prefixed.
     */
    public static void printWithThreadName(String message) {
        System.out.format("%s: %s%n", Thread.currentThread().getName(), message);
    }
    
    
    /**
     * Simplify the codes to catch an unchecked exception, usually the RuntimeException.
     * @param function
     * @return The Throwable thrown by the function. null if no throwable is thrown.
     */
    public static Throwable catchThrowable(NonArgFunction function) {
        try {
            function.doSth();
            return null;
        } catch (Throwable t) {
            return t;
        }
    }
    
    /**
     * Simplifies the code to repeatedly run a piece of code for N times.
     * @param times
     * @param function
     */
    public static void repeatRun(int times, NonArgFunction function) {
    	IntStream.range(0, times).forEach(i->function.doSth());
    }
    
    /**
     * Simplify the code to repeatedly run a piece of code for N times.
     * Takes the iteration index as arg of the to-run code.
     * @param times
     * @param function
     */
    public static void repeatRun(int times, Consumer<Integer> c) {
    	IntStream.range(0, times).forEach(i->c.accept(i));
    }
    
    /**
     * Display the number of bytes in a human readable way.
     * 
     * Apache Commons FileUtils.byteCountToDisplaySize() rounds down to integer numbers.
     * 
     * This method keeps 1 digit after decimal point.
     * 
     * @param numOfBytes
     * @return the readable String
     */
    public static String bytesToReadable(double numOfBytes) {
        
        if (numOfBytes < 0) {
            throw new IllegalArgumentException("Number of bytes must bigger than or equal to 0.");
        }
        
        DecimalFormat df = new DecimalFormat("#.#");

        if (numOfBytes >= GB) {
            return df.format(numOfBytes / GB) + " GB";
        } else if (numOfBytes >= MB) {
            return df.format(numOfBytes / MB) + " MB";
        } else if (numOfBytes >= KB) {
            return df.format(numOfBytes / KB) + " KB";
        } else {
            return df.format(numOfBytes) + " Bytes";
        }
    }
    
    /**
     * returns the human readable String of a number with proper units
     * @param num
     * @return
     */
    public static String numberToReadableString(long num) {
        String result = "";
        double numAbs = Math.abs(num);

        DecimalFormat df = new DecimalFormat("#.#");

        if (numAbs >= BN) {
            result = df.format(numAbs / BN).toString() + " Bn";
        } else if (numAbs >= MN) {
        	String r = df.format(numAbs / MN).toString();
        	result = r.equals("1000") ? "1 Bn" : (r + " Mn");
        } else if (numAbs >= K) {
        	String r = df.format(numAbs / K).toString();
            result = r.equals("1000") ? "1 Mn" : (r + " K");
        } else {
            result = String.valueOf(numAbs);
        }
        if (num < 0) {
            result = DASH + result;
        }
        return result;
    }


    /**
     * Make the codes for Thread.sleep(millis) in one line by 
     * swallowing the InterruptedException (just call e.printStackTrace())
     * 
     * @param millis
     */
    public static void threadSleep(long millis) {
    	try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Simplify this common practice to one line.
     * 
     * @param r the Runnable to run in the newly created thread
     * @return the newly created thread
     */
    public static Thread createAndStartThread(Runnable r) {
    	Thread t = new Thread(r);
    	t.start();
    	return t;
    }
    

    /**
     * Make the codes for thread.join() in one line by 
     * swallowing the InterruptedException (just call e.printStackTrace())
     * 
     * @param the thread to join
     */
    public static void threadJoin(Thread thr) {
    	try {
			thr.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    
    
    /**
     * Get the sub String between the n1th and the n2th delimiter 
     * index begins from 0
     * E.g. 
     * getSubString("111,aaa,bbb,,ccc", ",", 1, 2) returns "bbb"
     * getSubString("111,aaa,bbb,,ccc", ",", -1, 1) returns "111,aaa"
     * getSubString("111,aaa,bbb,,ccc", ",", 2, 3) returns ""
     * getSubString("111,aaa,bbb,,ccc", ",", x, x) returns ""
     * getSubString("111,aaa,bbb,,ccc", ",", x, [a value bigger than the max occurrence number of delimiter]) returns ""
     * 
     */
    public static String getSubString(String str, String delimiter, int n1, int n2) {
        if (n2 <= n1) {
            return EMPTY;
        }
        
        try {
            int resultBeginIdx = 0;
            int delimiterIdx = -1;
            for (int i = 0; i <= n2; i++) {
                delimiterIdx = str.indexOf(delimiter, delimiterIdx + 1);
                if(delimiterIdx == -1) {
                	return EMPTY;
                }
                if (i == n1) {// this happens at most once
                    resultBeginIdx = delimiterIdx + 1;
                }
            }
            
            return str.substring(resultBeginIdx, delimiterIdx);
            
        } catch (IndexOutOfBoundsException e) {
            return EMPTY;
        }
    }
    
    /**
     * If obj is not null, run fnc.
     * 
     * Simplify the following code to one line:
     * 
     * if (sth != null){
     *     run some code;
     * }
     * 
     * @param obj
     * @param fnc
     */
    public static void ifNotNull(Object obj, NonArgFunction fnc) {
    	if(obj != null) {
    		fnc.doSth();	
    	}
    }

    /**
     * get the sum value of an array of int numbers.
     * @param numbers
     * @return
     */
    public static int intsSum(int... numbers) {
        int sum = 0;
        for (int num : numbers) {
            sum = sum + num;
        }
        return sum;
    }
    
}
