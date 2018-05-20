package com.github.peiatgithub.java.utils;

import java.text.DecimalFormat;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import com.github.peiatgithub.java.utils.function.NonArgFunction;

import static com.github.peiatgithub.java.utils.Constants.*;

/**
 * Misc utility methods.
 * @author pei
 * @since 1.0
 */
public class Utils {
        

    /**
     *  println the message with the thread name prefixed.
     */
    public static void printlnWithThreadName(String message) {
        System.out.format("%s: %s%n", Thread.currentThread().getName(), message);
    }
    
    
    /**
     * Simplify the codes to catch an unchecked exception, usually the RuntimeException.
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
     */
    public static void repeatRun(int times, NonArgFunction function) {
    	IntStream.range(0, times).forEach(i->function.doSth());
    }
    
    /**
     * <pre>
     * Simplify the code to repeatedly run a piece of code for N times.
     * Takes the iteration index as arg of the to-run code.
     * </pre>
     */
    public static void repeatRun(int times, Consumer<Integer> c) {
    	IntStream.range(0, times).forEach(i->c.accept(i));
    }
    
    /**
     * <pre>
     * Display the number of bytes in a human readable way.
     * Apache Commons FileUtils.byteCountToDisplaySize() rounds down to integer number.
     * This method keeps 1 digit after decimal point.
     * </pre>
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
     */
    public static String numberToReadable(long num) {
        String result = EMPTY;
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
     * <pre>
     * Make the codes for Thread.sleep(millis) in one line by 
     * swallowing the InterruptedException 
     * (just call e.printStackTrace())
     * </pre>
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
     * <pre>
     * Make the codes for thread.join() in one line by 
     * swallowing the InterruptedException 
     * (just call e.printStackTrace())
     * </pre>
     */
    public static void threadJoin(Thread thr) {
    	try {
			thr.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    
    
    /**
     * <pre>
     * Get the sub String between the n1th and the n2th delimiter. 
     * Index begins from 0
     * E.g. 
     * getSubString("111,aaa,bbb,,ccc", ",", 1, 2) returns "bbb"
     * getSubString("111,aaa,bbb,,ccc", ",", -1, 1) returns "111,aaa"
     * getSubString("111,aaa,bbb,,ccc", ",", 2, 3) returns ""
     * getSubString("111,aaa,bbb,,ccc", ",", x, x) returns ""
     * getSubString("111,aaa,bbb,,ccc", ",", x, [a value bigger than the max occurrence number of delimiter]) returns ""
     * </pre>
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
     * <pre>
     * If obj is not null, run fnc.
     * 
     * Simplify the following code to one line:
     * 
     * if (sth != null){
     *     run some code;
     * }
     * </pre>
     */
    public static void ifNotNull(Object obj, NonArgFunction fnc) {
    	ifThen((obj != null), fnc);
    }

    /**
     * <pre>
     * If flag is true, run function.
     * Simplify the following code to 1 line: 
     * if(flag){
     *     fnc.doSth();
     * }
     * This is especially helpful when fnc is just a simple 1 line code.
     * </pre>
     */
    public static void ifThen(boolean flag, NonArgFunction fnc) {
    	if(flag) {
    		fnc.doSth();	
    	}
    }

    /**
     * <pre>
     * If flag is true, run function1, else, run function2
     * Simplify the following code to 1 line:
       if(flag) {
           fnc1.doSth();
       }else {
           fnc2.doSth();
       }
       This is especially helpful when fnc1 and fnc2 are just simple 1 line codes.
     * </pre>
     */
    public static void ifElse(boolean flag, NonArgFunction fnc1, NonArgFunction fnc2) {
    	if(flag) {
    		fnc1.doSth();	
    	}else {
    		fnc2.doSth();
    	}
    }

    /**
     * get the sum value of an array of int numbers.
     */
    public static int intsSum(int... numbers) {
        int sum = 0;
        for (int num : numbers) {
            sum = sum + num;
        }
        return sum;
    }
    
    /**
     * <pre>
     * The java.lang.StringBuilder has no method to reset/clear its content simply and explicitly.
     * This method do so.
     * </pre>
     */
    public static void clearStringBuilder(StringBuilder sb) {
		sb.setLength(0);
	}

    
}
