package org.codehaus.mojo.enchanter.impl;

import org.codehaus.plexus.util.Os;
import org.codehaus.plexus.util.cli.Commandline;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExecStreamConnectionTest
{
    private String cmd = "src/test/exec/testecho";

    private DefaultStreamConnection conn;

    @Before
    public void setUp()
        throws Exception
    {
        Commandline cl = this.createCommandLineSubProcess();
        conn = new DefaultStreamConnection( createConnectionLibrary( cl ) );

        conn.setDebug( true );
        conn.connect( "" );

        // conn.setTimeout( 2000000 );
    }

    @After
    public void tearDown()
        throws Exception
    {
        conn.disconnect();
    }

    @Test
    public void testNormalCommand()
        throws Exception
    {
        Thread.sleep( 2000 );
        Assert.assertTrue( "before-pause not found", conn.waitFor( "before-pause" ) );
        Assert.assertTrue( "Press any key to continue . . . not found",
                           conn.waitFor( "Press any key to continue . . ." ) );
        Assert.assertFalse( "Found unexpected after-pause", conn.waitFor( "after-pause" ) );
        conn.sendLine( "" );

        Thread.sleep( 2000 );//see the simulated script
        Assert.assertTrue( "after-pause not found", conn.waitFor( "after-pause" ) );
    }

    private Commandline createCommandLineSubProcess()
    {
        if ( Os.isFamily( "windows" ) )
        {
            cmd += ".bat";
            cmd = cmd.replace( '/', '\\' );
        }

        Commandline cl = new Commandline( cmd );
        cl = new Commandline( "sh" );
        cl.createArg().setLine( "src/test/exec/testecho" );

        return cl;
    }

    private ExecConnectionLibrary createConnectionLibrary( Commandline cl )
    {
        ExecConnectionLibrary lib = new ExecConnectionLibrary( cl );
        if ( cl.getExecutable().equals( "sh" ) )
        {
            lib = new ExecConnectionLibrary( cl, "password:", 1000, "password" );
        }
        return lib;
    }

}
