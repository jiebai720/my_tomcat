package com.bb.tomcat.ch3;

import com.sun.net.httpserver.spi.HttpServerProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by admin on 2017/7/17.
 */
public class HttpConnector implements Runnable {

    boolean stopped = false ;
    private String scheme = "http";

    public String getScheme(){
        return scheme ;
    }


    @Override
    public void run() {
        ServerSocket serverSocket = null ;
        int port = 8080;
        try{
            serverSocket = new ServerSocket(port , 1 ,  InetAddress.getByName("127.0.0.1") );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while( !stopped ){
            Socket socket = null ;
            try{
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            HttpProcessor processor = new HttpProcessor(this);
            processor.process(socket);
        }

    }


    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }

}
