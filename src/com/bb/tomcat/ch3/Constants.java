package com.bb.tomcat.ch3;

import java.io.File;

/**
 * Created by admin on 2017/7/18.
 */
public class Constants {


    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "WebRoot" ;

    public static final String CLASSESS_ROOT = System.getProperty("user.dir") + File.separator + "out\\production\\my_tomcat" ;

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN" ;

}
