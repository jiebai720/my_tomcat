package com.bb.tomcat.ch3;

/**
 * Created by admin on 2017/7/17.
 */
public class Bootstrap {


    public static void main(String[] args)  {

        HttpConnector httpConnector = new HttpConnector();
        httpConnector.start();
    }


}
