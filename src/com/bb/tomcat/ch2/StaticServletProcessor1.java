package com.bb.tomcat.ch2;

import java.io.IOException;

/**
 * Created by admin on 2017/7/14.
 */
public class StaticServletProcessor1 {

    public void process( Request request , Response response){

        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
