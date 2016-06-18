package org.codehaus.mojo.enchanter.impl;

import org.codehaus.plexus.util.cli.Commandline;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SshExecStreamConnectionTest
{
    private DefaultStreamConnection conn;

    @Before
    public void setUp()
        throws Exception
    {
        Commandline cl = new Commandline();
        cl.setExecutable( "ssh" );
        cl.createArg().setLine( "-o StrictHostKeyChecking=no -l root 10.13.204.27" );
        conn = new DefaultStreamConnection( new ExecConnectionLibrary( cl ) );
        conn.setDebug( true );
        conn.connect( "" );

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
        Thread.sleep( 4000 );
        Assert.assertTrue( "Password prompt not found", conn.waitFor( "Password" ) );
        conn.sendLine( "changeme" );

        Thread.sleep( 5000 );//see the simulated script
        Assert.assertTrue( "command promt not found", conn.waitFor( ":~ #" ) );
    }


}
