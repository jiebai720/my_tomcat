package com.bb.tomcat.ch2;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by admin on 2017/7/14.
 */
public class PrimitiveServlet implements Servlet {


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("= init =");
    }


    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        System.out.println( " PrimitiveServlet service " );
        PrintWriter printWriter =  servletResponse.getWriter();
        printWriter.println("Hello world ");
        printWriter.print("bnnnnn");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }


    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

}
