package com.bb.tomcat.ch5;

import com.bb.tomcat.ch4.core.SimpleContainer;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

/**
 * Created by admin on 2017/7/17.
 */
public class Bootstrap1 {


    public static void main(String[] args)  {

        HttpConnector httpConnector = new HttpConnector();

        Wrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("com.bb.tomcat.ch5.PrimitiveServlet");

        Loader loader = new SimpleLoader();
        Valve valve1 = new HeaderLoggerValve() ;
        Valve valve2 = new ClientIPLoggerValve() ;

        wrapper.setLoader( loader );
        ((Pipeline)wrapper ).addValve( valve1 );
        ((Pipeline)wrapper ).addValve( valve2 );

        httpConnector.setContainer( wrapper );

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
