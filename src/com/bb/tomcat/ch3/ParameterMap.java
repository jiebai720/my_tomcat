package com.bb.tomcat.ch3;

import org.apache.catalina.util.StringManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/7/19.
 */
public class ParameterMap extends HashMap {


    public ParameterMap(){
        super();
    }

    public ParameterMap( int initialCapacity ){
        super(initialCapacity);
    }

    public ParameterMap( int initialCapacity ,int loadFactor ){
        super(initialCapacity , loadFactor );
    }

    public ParameterMap( Map map ){
        super(map);
    }

    private boolean locked = false;

    public boolean isLocked(){
        return (this.locked);
    }

    public void setLocked( boolean locked ){
         this.locked = locked ;
    }


//    private static final StringManager sm = StringManager.getManager("LocalStrings");

    public void clear(){
        if(locked)
            throw new IllegalStateException( "locked" ) ;
        super.clear();
    }

    public Object put( Object key , Object value){
        if(locked)
            throw new IllegalStateException( "locked" ) ;

        return super.put(key ,value);
    }

    public void putAll( Map map){
        if(locked)
            throw new IllegalStateException( "locked" ) ;

        super.putAll(map);
    }


    public boolean remove( Object key , Object value){
        if(locked)
            throw new IllegalStateException( "locked" ) ;

        return super.remove(key ,value);
    }




}
