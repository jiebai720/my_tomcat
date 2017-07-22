package com.bb.tomcat.ch4.startup;

import com.bb.tomcat.ch4.core.SimpleContainer;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

/**
 * Created by admin on 2017/7/17.
 */
public class Bootstrap {


    public static void main(String[] args)  {

        HttpConnector httpConnector = new HttpConnector();

        SimpleContainer container  = new SimpleContainer();
        httpConnector.setContainer( container );

        try {
            httpConnector.initialize();
            httpConnector.start();

            System.in.read();
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
