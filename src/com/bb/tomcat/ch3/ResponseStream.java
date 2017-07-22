package com.bb.tomcat.ch3;

import com.bb.tomcat.ch3.http.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by admin on 2017/7/19.
 */
public class ResponseStream  extends OutputStream {

    HttpResponse httpResponse ;


    public ResponseStream(HttpResponse httpResponse) {
        this.httpResponse = httpResponse ;
    }

    @Override
    public void write(int b) throws IOException {

    }

}
