package org.codehau.mojo.enchanter;

import java.io.IOException;

import org.codehaus.mojo.enchanter.ScriptRecorder;
import org.codehaus.mojo.enchanter.StreamConnection;
import org.codehaus.mojo.enchanter.impl.DefaultStreamConnection;
import org.python.util.PythonInterpreter;

/**
 * Executes the passed script using Python
 */
public class Main
{

    public static void main( String[] args )
        throws IOException
    {
        ScriptRecorder rec = new PythonScriptRecorder();

        args = rec.processForLearningMode( args );

        String filePath = args[0];

        PythonInterpreter interp = new PythonInterpreter();
        StreamConnection conn = new DefaultStreamConnection();

        // deprecated
        interp.set( "ssh", conn );
        interp.set( "conn", conn );
        interp.set( "args", args );
        interp.execfile( filePath );

    }

}
