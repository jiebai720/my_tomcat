package com.bb.tomcat.ch3;

import com.bb.tomcat.ch3.http.HttpHeader;
import com.bb.tomcat.ch3.http.HttpRequest;
import com.bb.tomcat.ch3.http.HttpResponse;
import com.bb.tomcat.ch3.http.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by admin on 2017/7/17.
 */
public class HttpProcessor {


    private HttpConnector connector = null;
    private HttpRequest request;
    private HttpRequestLine requestLine = new HttpRequestLine();
    private HttpResponse response;

    protected String method = null;
    protected String queryString = null;

//    protected String

    public HttpProcessor(HttpConnector httpConnector) {
        this.connector = httpConnector ;
    }

    public void process(Socket socket)  {

        SocketInputStream inputStream = null ;
        OutputStream outputStream = null;
        try {
            inputStream = new SocketInputStream( socket.getInputStream() , 2048 ) ;
            outputStream = socket.getOutputStream();

            // create HttpRequest object and parse
            request = new HttpRequest(inputStream) ;

            // create HttpResponse object
            response = new HttpResponse(outputStream) ;
            response.setRequest(request);

            response.setHeader("Server" , "bb server");

            parseRequest( inputStream , outputStream );
            parseHeaders( inputStream );

            if(request.getRequestURI().startsWith("/servlet/")){

                ServletProcessor processor1  = new ServletProcessor();
                processor1.process( request , response );
            }else{

                StaticServletProcessor1 processor2 = new StaticServletProcessor1();
                processor2.process( request , response );
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }


    private void parseHeaders(SocketInputStream inputStream) throws ServletException, IOException {

        while(true) {

            HttpHeader httpHeader = new HttpHeader();
            inputStream.readHeader(httpHeader);
            if (httpHeader.nameEnd == 0) {
                if (httpHeader.valueEnd == 0) {
                    return;
                } else {
                    throw new ServletException("httpProcessor.parseHeaders.colon");
                }
            }
            String name = new String(httpHeader.name, 0, httpHeader.nameEnd);
            String value = new String(httpHeader.value, 0, httpHeader.valueEnd);
            request.addHeader(name, value);

            if (name.equals("cookie")) {
                Cookie cookies[] = RequestUtil.parseCookieHeader(value);
                for (int i = 0; i < cookies.length; i++) {
                    if ( cookies[i].getName().equals("jsessionid") ){
                        if (!request.isRequestedSessionIdFromCookie()){
                            request.setRequestSessionId( cookies[i].getValue() );
                            request.setRequestedSessionCookie(true);
                            request.setRequestedSessionURL(false);
                        }
                    }
                    request.addCookie( cookies[i] );
                }

            }else if (name.equals("content-length")) {

                int n = -1;
                try {
                    n = Integer.parseInt(value);
                }
                catch (Exception e) {
//                    TODO
                    e.printStackTrace();
//                    throw new ServletException(sm.getString("httpProcessor.parseHeaders.contentLength"));
                }
                request.setContentLength( n );
            }else if (name.equals("content-type")) {
                request.setContentType( value );
            }

        }
    }


    private void parseRequest(SocketInputStream inputStream, OutputStream outputStream) throws ServletException, IOException {

        inputStream.readRequestLine( requestLine ) ;
        String method = new String( requestLine.method, 0 , requestLine.methodEnd );
        String uri  = null;
        String protocol = new String( requestLine.protocol, 0 , requestLine.protocolEnd );

        if( method.length() < 1 ){
            throw new ServletException("missing HTTP request method");
        } else if(  requestLine.uriEnd < 1 ){
            throw new ServletException("missing HTTP request uri");
        }

        int question = requestLine.indexOf("?");
        if( question >=0 ){
            request.setQueryString( new String(requestLine.uri , question +1 ,
                    requestLine.uriEnd - question - 1 ) );
            uri = new String( requestLine.uri , 0 , question );
        } else {
            request.setQueryString(null);
            uri = new String( requestLine.uri , 0 , requestLine.uriEnd );
        }

        if( !uri.startsWith("/")){
            int pos = uri.indexOf("://");
            if( pos !=-1){
                pos = uri.indexOf("/" , pos + 3);
                if (pos == -1) {
                    uri = "";
                }
            }else{
                uri = uri.substring(pos);
            }
        }

        String match = ";jsessionid=" ;
        int semicolon = uri.indexOf(match);
        if( semicolon >= 0){
            String rest = uri.substring( semicolon + match.length());
            int semicolon2 = rest.indexOf(";");
            if( semicolon2 >= 0){
                request.setRequestSessionId( rest.substring(0 , semicolon2)) ;
                rest = rest.substring(semicolon2);
            }else{
                request.setRequestSessionId( rest ) ;
                rest = "" ;
            }
            request.setRequestSessionURL( true ) ;
            uri = uri.substring( 0, semicolon ) + rest ;
        }else{
            request.setRequestSessionId( null ) ;
            request.setRequestSessionURL( false ) ;
        }

        String normalizedUri = normalize(uri);
        HttpRequest httpRequest = (HttpRequest) request ;
        httpRequest.setMethod(method);
        request.setProtocol(protocol);

        if( normalizedUri != null ){
            httpRequest.setRequestURI(normalizedUri);
        }else{
            httpRequest.setRequestURI(uri);
        }

        if( normalizedUri == null ){
            throw  new ServletException("Invalid URI:" + uri + "'");
        }
    }




    private String normalize(String path) {

        if (path == null)
            return null;

        String normalized = path;

        // Normalize "/%7E" and "/%7e" at the beginning to "/~"
        if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
            normalized = "/~" + normalized.substring(4);



        return  normalized ;
    }

}
