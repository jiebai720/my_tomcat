package com.bb.tomcat.ch1;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

/**
 * Created by admin on 2017/7/14.
 */
public class HTTPTest {



    public static void main(String[] args) throws IOException, InterruptedException {

        String ip = "218.205.231.140" ;
        int port = 80 ;
        String url = "GET /service/index.html HTTP/1.1" ;

        ip = "127.0.0.1" ;
        port = 8080 ;
        url = "GET /index.html HTTP/1.1" ;

        test( ip , port , url );
    }


    private static void test( String ip , int port , String url ) throws IOException, InterruptedException {

        Socket socket = new Socket(  ip , port ) ;
        OutputStream outputStream = socket.getOutputStream() ;

        boolean autoFresh = true;
        PrintWriter printWriter = new PrintWriter( outputStream ,   autoFresh );
        BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) ) ;

        printWriter.println( url );
        printWriter.println( "Host: localhost:8080" );
        printWriter.println( "Connection: Close" );
        printWriter.println();

        boolean loop = true ;
        StringBuilder stringBuilder  = new StringBuilder(8096);
        while(loop){
            if( in.ready() ){
                int i=0;
                while( i !=-1){
                    i= in.read();
                    stringBuilder.append( (char) i);
                }
                loop = false;
            }
            Thread.currentThread().sleep(50);
        }

        System.out.println( stringBuilder.toString() );
        socket.close();;
    }



}
