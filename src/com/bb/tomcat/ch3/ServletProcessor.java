package com.bb.tomcat.ch3;

import com.bb.tomcat.ch3.http.HttpRequest;
import com.bb.tomcat.ch3.http.HttpResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by admin on 2017/7/14.
 */
public class ServletProcessor {

    public void process(HttpRequest request, HttpResponse response) {

        String uri = request.getRequestURI();
        String servletName = uri.substring( uri.lastIndexOf("/") + 1);

        URL[] urls = new URL[1];
        URLStreamHandler streamHandler =  null ;
        URLClassLoader loader = null ;

        File classPath = new File( Constants.CLASSESS_ROOT );
        try {
            String repository =(new URL("file" , null , classPath.getCanonicalPath()+ File.separator)).toString();
            urls[0] = new URL( null , repository , streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class myClass = null ;
        try {
            servletName = "com.bb.tomcat.ch3." + servletName ;
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Servlet servlet = null ;
        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service( request , response );
            response.finishResponse();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
