package com.bb.tomcat.ch2;


import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * Created by admin on 2017/7/14.
 */
public class Response implements ServletResponse {

    private static final  int BUFFER_SIZE = 1024 ;

    private OutputStream outputStream ;
    private Request request ;
    PrintWriter printWriter ;

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
            File file = new File(HttpServer1.WEB_ROOT , request.getUri() );
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

    @Override
    public String getCharacterEncoding() {
        return null;
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {

//  @TODO      对于print方法不会发送给浏览器，后续解决
        printWriter = new PrintWriter( outputStream , true );
        return printWriter ;
    }


    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
