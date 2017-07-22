package com.bb.tomcat.ch5;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by admin on 2017/7/20.
 */
public class HeaderLoggerValve implements Valve , Contained {


    protected Container container ;

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container ;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {

        System.out.println( "Header IP Logger Valve" );

        valveContext.invokeNext( request , response );
        HttpServletRequest request1 = (HttpServletRequest)request;

        Enumeration headerNames = request1.getHeaderNames();
        while ( headerNames.hasMoreElements() ){
            String headerName = headerNames.nextElement().toString();
            String headerValue = request1.getHeader( headerName );
            System.out.println( headerName + ":" + headerValue);
        }
        System.out.println( "=----------------------");
    }

}
