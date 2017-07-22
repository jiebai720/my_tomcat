package com.bb.tomcat.ch5;

import com.bb.tomcat.ch3.http.*;
import org.apache.catalina.*;
import org.apache.catalina.HttpResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 2017/7/20.
 */
public class SimpleWrapperValve implements Valve , Contained {

    protected Container container;

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
    public void invoke( Request request, Response response, ValveContext valveContext) throws IOException, ServletException {

        SimpleWrapper wrapper = (SimpleWrapper) getContainer() ;
        ServletRequest servletRequest = request.getRequest() ;
        ServletResponse servletResponse = response.getResponse() ;

        Servlet servlet = null ;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest ;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse ;

//      獲取servlet實例
        servlet = wrapper.allocate();
        if( httpServletResponse != null ){
            servlet.service( httpServletRequest , httpServletResponse  );
        }else{
            servlet.service( servletRequest , servletResponse  );

        }

    }


}
