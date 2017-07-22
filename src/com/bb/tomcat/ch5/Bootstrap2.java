package com.bb.tomcat.ch5;

import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

/**
 * Created by admin on 2017/7/17.
 */
public class Bootstrap2 {



    public static void main(String[] args)  {

        HttpConnector httpConnector = new HttpConnector();


        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("com.bb.tomcat.ch5.PrimitiveServlet");

        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("com.bb.tomcat.ch5.ModernServlet");

        Context context = new SimpleContext();
        context.addChild( wrapper1 );
        context.addChild( wrapper2 );

        Valve valve1 = new HeaderLoggerValve() ;
        Valve valve2 = new ClientIPLoggerValve() ;

        ((Pipeline) context).addValve( valve1 );
        ((Pipeline) context).addValve( valve2 );

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");

        context.addMapper( mapper );

        Loader loader = new SimpleLoader();
        context.setLoader( loader );
        context.addServletMapping("/p" , "Primitive");
        context.addServletMapping("/m" , "Modern");

        httpConnector.setContainer( context );

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
