package com.bb.tomcat.ch2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by admin on 2017/7/14.
 */
public class HttpServer1 {


    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "WebRoot" ;

    public static final String CLASSESS_ROOT = System.getProperty("user.dir") + File.separator + "out\\production\\my_tomcat" ;

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN" ;

    private boolean shutDown = false ;


    public static void main(String[] args)  {

        System.out.println( WEB_ROOT );
        HttpServer1 httpServer = new HttpServer1();
        httpServer.await();
    }


    public void await(){

        ServerSocket serverSocket = null ;
        int port = 8080;
        try{
            serverSocket = new ServerSocket(port , 1 ,  InetAddress.getByName("127.0.0.1") );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while( !shutDown ){
            Socket socket = null ;
            InputStream inputStream = null ;
            OutputStream outputStream = null ;
            try{
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                Request request = new Request(inputStream) ;
                request.parse();

                Response response = new Response(outputStream) ;
                response.setRequest(request);

                if(request.getUri().startsWith("/servlet/")){
                    ServletProcessor1 processor1  = new ServletProcessor1();
                    processor1.process( request , response );
                }else{

                    StaticServletProcessor1 processor2 = new StaticServletProcessor1();
                    processor2.process( request , response );
                }

                socket.close();
                shutDown = request.getUri().equals(SHUTDOWN_COMMAND) ;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

    }


}
