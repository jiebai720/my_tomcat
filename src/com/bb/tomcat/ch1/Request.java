package com.bb.tomcat.ch1;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2017/7/14.
 */
public class Request {


    private InputStream inputStream ;
    private String uri;

    public  Request( InputStream inputStream  ){
        this.inputStream = inputStream ;
    }

    public void parse(){
        StringBuilder request = new StringBuilder(2048);
        int i = 0 ;
        byte[] buffer = new byte[2048];
        try{
            i  = inputStream.read(buffer) ;
        } catch (IOException e) {
            e.printStackTrace();
            i=-1 ;
        }
        for (int j = 0; j < i; j++) {
            request.append( (char) buffer[j] );
        }
        System.out.println( request.toString() );
        uri = parseUri( request.toString() );
    }

    private String parseUri(String requestString){
        int index1 , index2 ;
        index1 = requestString.indexOf(" ");
        if( index1 != -1 ){
            index2 = requestString.indexOf(" " , index1 + 1);
            if( index2 >  index1 ){
                return requestString.substring( index1+1 , index2 );
            }
        }
        return null;
    }

    public String getUri(){
        return this.uri ;
    }


}
