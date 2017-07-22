package com.bb.tomcat.ch3;

import com.bb.tomcat.ch3.http.HttpRequest;
import com.bb.tomcat.ch3.http.HttpResponse;

import java.io.IOException;

/**
 * Created by admin on 2017/7/14.
 */
public class StaticServletProcessor1 {

    public void process(HttpRequest request , HttpResponse response){

        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
