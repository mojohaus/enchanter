package org.codehaus.mojo.enchanter;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.codehaus.mojo.enchanter.impl.DefaultStreamConnection;

/**
 * Stream reader that helps create a script.  Meant to be subclassed by scripting
 * engine-dependent modules
 */
public class ScriptRecorder
    implements StreamListener
{

    char[] lastChars = new char[10];

    int lastCharsPos = 0;

    PrintWriter out;

    public void setPromptSize( int size )
    {
        lastChars = new char[size];
    }

    protected void printUsage()
    {
        System.err.println( "Usage: java -jar enchanter.jar [{-l,--learn} [{-h,--host}]\n"
            + "                                             [{-p,--port}] [{-u,--username}]\n"
            + "                                             [{-P,--password}]{-p,--port}]\n"
            + "                                             [--prompt-size]\n"
            + "                               ] SCRIPT_PATH" );
    }

    /**
     * Processes command line for learning mode args
     * @param args
     * @return
     * @throws IOException
     */
    public String[] processForLearningMode( String[] args )
        throws IOException
    {
        CmdLineParser parser = new CmdLineParser();
        Option optLearn = parser.addBooleanOption( 'l', "learn" );
        Option optHost = parser.addStringOption( 'h', "host" );
        Option optPort = parser.addIntegerOption( 'p', "port" );
        Option optUsername = parser.addStringOption( 'u', "username" );
        Option optPassword = parser.addStringOption( 'P', "password" );
        Option optPromptSize = parser.addIntegerOption( "prompt-size" );

        try
        {
            parser.parse( args );
        }
        catch ( CmdLineParser.OptionException e )
        {
            System.err.println( e.getMessage() );
            printUsage();
            System.exit( 2 );
        }

        if ( parser.getRemainingArgs().length != 1 )
        {
            printUsage();
            System.exit( 2 );
        }

        if ( ( (Boolean) parser.getOptionValue( optLearn, false ) ).booleanValue() )
        {
            String host = (String) parser.getOptionValue( optHost, "localhost" );
            int port = ( (Integer) parser.getOptionValue( optPort, Integer.valueOf( 22 ) ) ).intValue();
            String username = (String) parser.getOptionValue( optUsername );
            String password = (String) parser.getOptionValue( optPassword, "" );
            setPromptSize( ( (Integer) parser.getOptionValue( optPromptSize, Integer.valueOf( 10 ) ) ).intValue() );

            StreamConnection streamConnection = new DefaultStreamConnection();
            streamConnection.addStreamListener( this );
            streamConnection.connect( host, port, username, password );
            startRecording( parser.getRemainingArgs()[0], host, port, username, password );
            ConsoleReader reader = new ConsoleReader();
            Thread t = new Thread( reader );
            t.start();
            streamConnection.waitFor( "asfdasfasfdSomeStringThatDoesntExistasdfasdf" );
            endRecording();
            System.exit( 0 );
        }

        return parser.getRemainingArgs();
    }

    protected void endRecording()
    {
        System.out.println( "End recording" );
    }

    protected void startRecording( String string, String host, int port, String username, String password )
    {
        System.out.println( "Starting recording..." );
    }

    // Primarily used for testing
    public static final void main( String[] args )
        throws IOException
    {
        ScriptRecorder rec = new ScriptRecorder();

        rec.processForLearningMode( args );
    }

    public synchronized void hasRead( byte b )
    {
        lastChars[lastCharsPos++] = (char) b;
        if ( lastCharsPos == lastChars.length )
        {
            lastCharsPos = 0;
        }
        System.out.print( (char) b );
    }

    /**
     * Subclasses should override to write language-specific code
     * @param prompt
     * @param response
     */
    protected void writePrompt( String prompt, String response )
    {
        System.out.println( "Prompt: " + prompt + " Response: " + response );
    }

    public void hasWritten( byte[] b )
    {
    }

    String getLastChars()
    {
        char[] chars = new char[lastChars.length];
        // test for partial
        if ( lastCharsPos < lastChars.length - 1 && lastChars[lastCharsPos + 1] == 0 )
        {
            System.arraycopy( lastChars, 0, chars, 0, lastCharsPos + 1 );
            return new String( chars, 0, lastCharsPos );
        }
        else
        {
            System.arraycopy( lastChars, lastCharsPos, chars, 0, lastChars.length - lastCharsPos );

            if ( lastCharsPos > 0 )
            {
                System.arraycopy( lastChars, 0, chars, lastChars.length - lastCharsPos, lastCharsPos );
            }
            for ( int start = chars.length - 1; start > 0; start-- )
            {
                if ( chars[start] == '\n' )
                {
                    if ( start == chars.length - 1 )
                    {
                        return "";
                    }
                    else
                    {
                        return new String( chars, start + 1, chars.length - start - 1 );
                    }
                }
            }
            return new String( chars );
        }
    }

    class ConsoleReader
        implements Runnable
    {

        BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );

        public void run()
        {
            try
            {
                while ( true )
                {
                    String line = reader.readLine();
                    synchronized ( this )
                    {
                        if ( line != null && line.length() > 0 )
                        {
                            out.println( line );
                            out.flush();
                            writePrompt( getLastChars(), line );
                        }
                    }
                }
            }
            catch ( IOException ex )
            {
                ex.printStackTrace();
            }

        }

    }

    public void init( PrintWriter writer )
    {
        this.out = writer;
    }

}
