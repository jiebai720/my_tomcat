package com.bb.tomcat.ch3.http;

import com.bb.tomcat.ch3.ParameterMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by admin on 2017/7/14.
 */
public class HttpRequest implements ServletRequest {

    private InputStream inputStream ;

    private  String requestSessionId ;
    private  boolean requestSessionURL ;
    private  String queryString ;
    private  String method ;
    protected ArrayList cookies = new ArrayList();


    public HttpRequest(InputStream inputStream  ){
        this.inputStream = inputStream ;
    }


    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String name) {

        parseParameters();
        String values[] = (String[]) parameters.get(name);
        if (values != null)
            return (values[0]);
        else
            return (null);

    }


    protected boolean parsed = false;
    protected ParameterMap parameters = null;

    protected void parseParameters() {

        if(parsed)
            return ;

        ParameterMap results = parameters ;
        if( results == null)
            results = new ParameterMap();

        results.setLocked(false);
        String encoding = getCharacterEncoding() ;

        if( encoding == null)
                encoding = "ISO-8859-1" ;

        String queryString = getQueryString() ;
        try {
            RequestUtil.parseParameters( results , queryString , encoding );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String contentType = getContentType() ;
        if(contentType == null )
            contentType = "" ;

        int semicolon = contentType.indexOf(";");
        if( semicolon >= 0 ){
            contentType = contentType.substring( 0 ,semicolon).trim();
        }else{
            contentType = contentType.trim();
        }

        if( "POST".equals(getMethod()) && (getContentLength() >0)
                && "application/x-www-form-urlencoded".equals(contentType) ) {

            try {
                int max = getContentLength() ;
                int len = 0 ;
                byte buf[] =  new byte[getContentLength()];
                ServletInputStream is = getInputStream();
                while (len < max){
                    int next = is.read(buf, len , max -len );
                    if( next < 0){
                        break ;
                    }
                    len += next;
                }
                is.close();

                if( len < max ){
                    throw  new RuntimeException("content length mismatch");
                }

                RequestUtil.parseParameters( results , buf , encoding );

                results.setLocked(true);
                parsed = true ;
                parameters = results ;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public Enumeration getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }


    public void setRequestSessionId(String requestSessionId) {
        this.requestSessionId = requestSessionId;
    }


    public void setRequestSessionURL(boolean requestSessionURL) {
        this.requestSessionURL = requestSessionURL;
    }


    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private String protocol;
    private String requestURI;
    private int contentLength;
    private String contentType;


    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }


    protected HashMap headers = new HashMap();

//   @TODO
    public void addHeader(String name, String value) {
        name = name.toLowerCase();
        synchronized ( headers ){
            ArrayList values = (ArrayList) headers.get(name);
            if( values == null ){
                values = new ArrayList();
                headers.put( name ,values);
            }
            values.add(value);
        }
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    private boolean requestedSessionCookie ;
    private boolean requestedSessionURL;

    public void setRequestedSessionCookie(boolean requestedSessionCookie) {
        this.requestedSessionCookie = requestedSessionCookie;
    }


    public void setRequestedSessionURL(boolean requestedSessionURL) {
        this.requestedSessionURL = requestedSessionURL;
    }

    public void addCookie(Cookie cookie) {
        synchronized (cookies) {
            cookies.add(cookie);
        }
    }

    public String getRequestSessionId() {
        return requestSessionId;
    }

    public boolean isRequestSessionURL() {
        return requestSessionURL;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getMethod() {
        return method;
    }

    public ArrayList getCookies() {
        return cookies;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public HashMap getHeaders() {
        return headers;
    }

    public boolean isRequestedSessionCookie() {
        return requestedSessionCookie;
    }

    public boolean isRequestedSessionURL() {
        return requestedSessionURL;
    }
}
