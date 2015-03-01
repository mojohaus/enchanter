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

    private ExecConnectionLibrary lib;

    @Before
    public void setUp()
        throws Exception
    {
        if ( Os.isFamily( "windows" ) )
        {
            cmd += ".bat";
            cmd = cmd.replace( '/', '\\' );
        }

        Commandline cl = new Commandline( cmd );
        conn = new DefaultStreamConnection();
        lib = new ExecConnectionLibrary( cl );
        conn.setConnectionLibrary( lib );
        conn.connect( "" );
        // conn.setTimeout( 100 );
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
        Assert.assertTrue( "before-pause not found", conn.waitFor( "before-pause" ) );
        Assert.assertTrue( "any key to continue . . . not found", conn.waitFor( "any key to continue . . ." ) );
        Assert.assertFalse( "Found unexpected after-pause", conn.waitFor( "after-pause" ) );
        conn.sendLine( "" );
        Assert.assertTrue( "after-pause not found", conn.waitFor( "after-pause" ) );
    }

}
