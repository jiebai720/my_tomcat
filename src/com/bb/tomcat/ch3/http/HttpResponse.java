package com.bb.tomcat.ch3.http;



import com.bb.tomcat.ch3.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * Created by admin on 2017/7/14.
 */
public class HttpResponse implements ServletResponse {

    // the default buffer size
    private static final int BUFFER_SIZE = 1024;
    OutputStream output;
    PrintWriter writer;
    protected byte[] buffer = new byte[BUFFER_SIZE];
    protected int bufferCount = 0;
    /**
     * Has this response been committed yet?
     */
    protected boolean committed = false;
    /**
     * The actual number of bytes written to this Response.
     */
    protected int contentCount = 0;

    protected String encoding = null;


    private OutputStream outputStream ;
    private HttpRequest request ;
    PrintWriter printWriter ;

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public HttpResponse(OutputStream outputStream  ){
        this.outputStream = outputStream ;
    }

    public void sendStaticResource() throws IOException {

        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null ;
        try {
            File file = new File( Constants.WEB_ROOT , request.getRequestURI() );
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
        if (encoding == null)
            return ("ISO-8859-1");
        else
            return (encoding);
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {

//  @TODO      对于print方法不会发送给浏览器，后续解决
//        printWriter = new PrintWriter( outputStream , true );
//        return printWriter ;

        ResponseStream newStream = new ResponseStream(this);
        newStream.setCommit(false);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter( newStream , getCharacterEncoding() );
        printWriter = new ResponseWriter( outputStreamWriter);
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

    public void setHeader(String server, String s) {
    }

    public void finishResponse() {

        if( printWriter != null){
            printWriter.flush();
            printWriter.close();
        }

    }

    public void write(int b) throws IOException {
        if (bufferCount >= buffer.length)
            flushBuffer();
        buffer[bufferCount++] = (byte) b;
        contentCount++;
    }


    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException {
        // If the whole thing fits in the buffer, just put it there
        if (len == 0)
            return;
        if (len <= (buffer.length - bufferCount)) {
            System.arraycopy(b, off, buffer, bufferCount, len);
            bufferCount += len;
            contentCount += len;
            return;
        }

        // Flush the buffer and start writing full-buffer-size chunks
        flushBuffer();
        int iterations = len / buffer.length;
        int leftoverStart = iterations * buffer.length;
        int leftoverLen = len - leftoverStart;
        for (int i = 0; i < iterations; i++)
            write(b, off + (i * buffer.length), buffer.length);

        // Write the remainder (guaranteed to fit in the buffer)
        if (leftoverLen > 0)
            write(b, off + leftoverStart, leftoverLen);
    }



}
