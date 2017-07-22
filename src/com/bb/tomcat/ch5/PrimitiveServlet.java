package com.bb.tomcat.ch5;

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

    public void hello() throws ServletException {
        System.out.println("= hello =");
    }

    @Override
    public void service( ServletRequest servletRequest , ServletResponse servletResponse) throws ServletException, IOException {

        System.out.println( " 5555555555555 PrimitiveServlet service " );

        String name = servletRequest.getParameter("name");
        System.out.println( "name===" + name );
        PrintWriter printWriter =  servletResponse.getWriter();
//        printWriter.println(" ");

        printWriter.println("  ch3");
        printWriter.println(" Hello world ");
        printWriter.print(" aaa ");
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
