package org.codehau.mojo.enchanter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.codehaus.mojo.enchanter.ScriptRecorder;
import org.codehaus.mojo.enchanter.impl.DefaultStreamConnection;
import org.jruby.IRuby;
import org.jruby.RubyException;
import org.jruby.RubyFixnum;
import org.jruby.RubyNumeric;
import org.jruby.exceptions.JumpException;
import org.jruby.exceptions.MainExitException;
import org.jruby.exceptions.RaiseException;
import org.jruby.javasupport.JavaEmbedUtils;
import org.jruby.runtime.GlobalVariable;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * Executes the passed script using Ruby
 */
public class Main
{

    public static void main( String[] args )
        throws IOException
    {
        ScriptRecorder rec = new RubyScriptRecorder();

        args = rec.processForLearningMode( args );

        String filePath = args[0];

        IRuby runtime = JavaEmbedUtils.initialize( Collections.singletonList( "." ) );
        IRubyObject conn = JavaEmbedUtils.javaToRuby( runtime, new DefaultStreamConnection() );

        try
        {
            // deprecated
            runtime.getGlobalVariables().set( GlobalVariable.variableName( "ssh" ), conn );

            runtime.getGlobalVariables().set( GlobalVariable.variableName( "conn" ), conn );
            runtime.getGlobalVariables().set( GlobalVariable.variableName( "args" ),
                                              JavaEmbedUtils.javaToRuby( runtime, args ) );
            runtime.loadFile( new File( filePath ), false );
        }
        catch ( JumpException je )
        {
            if ( je.getJumpType() == JumpException.JumpType.RaiseJump )
            {
                RubyException raisedException = ( (RaiseException) je ).getException();

                if ( raisedException.isKindOf( runtime.getClass( "SystemExit" ) ) )
                {
                    RubyFixnum status = (RubyFixnum) raisedException.getInstanceVariable( "status" );

                    if ( status != null )
                    {
                        System.exit( RubyNumeric.fix2int( status ) );
                    }
                }
                else
                {
                    runtime.printError( raisedException );
                    System.exit( 1 );
                }
            }
            else if ( je.getJumpType() == JumpException.JumpType.ThrowJump )
            {
                runtime.printError( (RubyException) je.getTertiaryData() );
                System.exit( 1 );
            }
            else
            {
                //throw je;
            }
        }
        catch ( MainExitException e )
        {
            if ( e.isAborted() )
            {
                System.exit( e.getStatus() );
            }
            else
            {
                throw e;
            }
        }

    }

}
