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
        //conn.setTimeout( 100 );
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
        Assert.assertTrue( "Not able to find 123456789 from sub process output stream", conn.waitFor( "123456789" ) );
    }

    @Test
    public void testNormalCommand2()
        throws Exception
    {
        Assert.assertTrue( "Not able to find 12345678 from sub process output stream", conn.waitFor( "12345678" ) );
    }

    @Test
    public void testNoFalsePositive()
        throws Exception
    {
        Assert.assertFalse( conn.waitFor( "1234567890" ) );
    }
}
