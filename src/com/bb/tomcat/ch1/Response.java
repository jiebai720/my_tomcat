package com.bb.tomcat.ch1;


import java.io.*;

/**
 * Created by admin on 2017/7/14.
 */
public class Response {

    private static final  int BUFFER_SIZE = 1024 ;

    private OutputStream outputStream ;
    private Request request ;

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response( OutputStream outputStream  ){
        this.outputStream = outputStream ;
    }

    public void sendStaticResource() throws IOException {

        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null ;
        try {
            File file = new File(HttpServer.WEB_ROOT , request.getUri() );
            if( file.exists() ){

                fis = new FileInputStream( file );
                int ch = fis.read(bytes , 0 , BUFFER_SIZE );
                while( ch !=  -1 ){
                    outputStream.write(bytes , 0 , ch);
                    ch = fis.read(bytes , 0 , BUFFER_SIZE );
                }
            }else{
                String errorMessage = "HTTP/1.1 404 aa\r\n " +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                         "\r\n" +
                        "<h1>File not Found</h1>" ;

                outputStream.write( errorMessage.getBytes() );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null)
                fis.close();
        }
    }

}
