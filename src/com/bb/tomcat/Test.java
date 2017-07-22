package com.bb.tomcat;

import com.bb.tomcat.ch3.HttpConnector;

import java.util.Random;

/**
 * Created by admin on 2017/7/19.
 */
public class Test {

    private Random random = null;
    private String shutdown = "SHUTDOWN";

    public static void main(String[] args)  {

        Test test = new Test();
        test.test();
    }

    public void test(){

        // Read a set of characters from the socket
        StringBuffer command = new StringBuffer();
        int expected = 1024; // Cut off to avoid DoS attack
        while (expected < shutdown.length()) {
            System.out.println( "before ===" +  expected );
            if (random == null){
                random = new Random(System.currentTimeMillis());
            }
            System.out.println( "before ===" +  expected );
            expected += (random.nextInt() % 1024);
            System.out.println( "after ===" + expected );

        }
    }

}
