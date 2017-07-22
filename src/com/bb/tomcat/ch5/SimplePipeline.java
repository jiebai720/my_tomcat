package com.bb.tomcat.ch5;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by admin on 2017/7/20.
 */
public class SimplePipeline implements Pipeline {

    protected Valve basic = null;
    protected Container container = null;
    protected Valve valves[] = new Valve[0];


    public SimplePipeline(Container container) {
        setContainer(container);
    }

    public void setContainer(Container container) {
        this.container = container;
    }


    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {

        this.basic = valve ;
        ((Contained) valve).setContainer(container);
    }

    @Override
    public void addValve(Valve valve) {
        if (valve instanceof Contained)
            ((Contained) valve).setContainer(this.container);

        synchronized (valves){
            Valve result[] = new Valve[valves.length+1];
            System.arraycopy( valves , 0 , result , 0 , valves.length );
            result[valves.length] = valve ;
            valves = result ;
        }
    }

    @Override
    public Valve[] getValves() {
        return valves ;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

        (new SimplePipelineValveContext()).invokeNext(request, response);
    }

    @Override
    public void removeValve(Valve valve) {

    }

    // this class is copied from org.apache.catalina.core.StandardPipeline class's
    // StandardPipelineValveContext inner class.
    protected class SimplePipelineValveContext implements ValveContext {

        protected int stage = 0;

        public String getInfo() {
            return null;
        }

        public void invokeNext(Request request, Response response)
                throws IOException, ServletException {
            int subscript = stage;
            stage = stage + 1;
            // Invoke the requested Valve for the current request thread
            if (subscript < valves.length) {
                valves[subscript].invoke(request, response, this);
            }
            else if ((subscript == valves.length) && (basic != null)) {
                basic.invoke(request, response, this);
            }
            else {
                throw new ServletException("No valve");
            }
        }
    } // end of inner class


}
