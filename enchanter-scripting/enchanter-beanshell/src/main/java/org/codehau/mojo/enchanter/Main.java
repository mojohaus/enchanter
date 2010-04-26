package org.codehau.mojo.enchanter;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.codehaus.mojo.enchanter.ScriptRecorder;
import org.codehaus.mojo.enchanter.impl.DefaultStreamConnection;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Executes the passed script using Beanshell
 */
public class Main
{

    /**
     * @param args
     * @throws EvalError 
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws BSFException
     */
    public static void main( String[] args )
        throws EvalError, FileNotFoundException, IOException
    {
        ScriptRecorder rec = new BeanShellScriptRecorder();

        args = rec.processForLearningMode( args );

        String filePath = args[0];

        DefaultStreamConnection streamConnection = new DefaultStreamConnection();

        Interpreter i = new Interpreter();

        // deprecated
        i.set( "ssh", streamConnection );

        i.set( "conn", streamConnection );
        i.set( "args", args );
        i.source( filePath );
    }

}
