package com.bb.tomcat.ch5;

import org.apache.catalina.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2017/7/22.
 */
public class SimpleContextMapper implements Mapper {

    private SimpleContext context = null ;
    private String protocol = null ;

    @Override
    public Container getContainer() {
        return context ;
    }

    @Override
    public void setContainer(Container container) {

        if( !(container instanceof  SimpleContext) )
            throw  new IllegalArgumentException("Illegal type of container");

        context = (SimpleContext) container ;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public void setProtocol(String s) {
        this.protocol = s ;
    }

    @Override
    public Container map(Request request, boolean b) {

        String contextPath = ( (HttpServletRequest) request.getRequest() ) .getContextPath();
        String requestURI = ( (HttpRequest) request).getDecodedRequestURI();
        String relativeURI = requestURI.substring( contextPath.length() );

        Wrapper wrapper = null ;
        String servletPath = relativeURI ;
        String pathInfo = null ;
        String name = context.findServletMapping( relativeURI ) ;
        if( name != null ){
            wrapper = (Wrapper) context.findChild(name);
        }

        return wrapper ;
    }
}
