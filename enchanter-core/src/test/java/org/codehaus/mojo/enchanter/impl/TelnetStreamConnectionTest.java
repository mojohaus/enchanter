package org.codehaus.mojo.enchanter.impl;

import java.io.IOException;

import junit.framework.TestCase;

import org.codehaus.mojo.enchanter.ConnectionLibrary;
import org.codehaus.mojo.enchanter.impl.DefaultStreamConnection;
import org.codehaus.mojo.enchanter.impl.TelnetConnectionLibrary;

public class TelnetStreamConnectionTest
    extends TestCase
{

    DefaultStreamConnection stream;

    ConnectionLibrary conn;

    public TelnetStreamConnectionTest( String arg0 )
    {
        super( arg0 );
    }

    protected void setUp()
        throws Exception
    {
        super.setUp();
        stream = new DefaultStreamConnection();
        conn = new TelnetConnectionLibrary();
        stream.setConnectionLibrary( conn );
        stream.connect( "10.2.24.13" );
    }

    protected void tearDown()
        throws Exception
    {
        stream.disconnect();
    }

    public void testEmpty()
    {

    }

    public void notestSend()
        throws Exception
    {

        stream.setTimeout( 10000 );

        stream.sendLine( "" );

        /*
        if (!stream.waitFor("ogin:a")) {
        	System.out.println("timeout");
        	return;
        }
        */

        stream.sendLine( "" );
        this.emptyStream();
        stream.sendLine( "" );
        this.emptyStream();
        stream.sendLine( "" );
        this.emptyStream();
        stream.sendLine( "" );
        this.emptyStream();
        stream.sendLine( "" );
        this.emptyStream();

    }

    private void emptyStream()
        throws IOException
    {
        while ( true )
        {
            String line = stream.getLine();
            String lastLine = stream.lastLine();
            if ( line.equals( lastLine ) )
            {
                System.out.println( line );
                break;
            }
            System.out.println( line );
        }
    }
    /*
     * public void testSend() throws IOException { ssh.send("foo");
     * assertEquals("foo", conn.dumpOut());
     * 
     * ssh.connect(null, null); ssh.send("foo^C"); assertEquals("foo"+((char)3),
     * conn.dumpOut());
     * 
     * ssh.connect(null, null); ssh.send("foo^M"); assertEquals("foo\r\n",
     * conn.dumpOut()); }
     * 
     * public void testSendLine() throws IOException { conn.setInputStream(new
     * ByteArrayInputStream("foo\r\n".getBytes())); ssh.connect(null, null);
     * ssh.sendLine("foo"); assertEquals("foo\r\n", conn.dumpOut()); }
     * 
     * public void testSleep() throws InterruptedException { long start =
     * System.currentTimeMillis(); ssh.sleep(500);
     * assertTrue(System.currentTimeMillis() >= start + 500); }
     * 
     * public void testWaitForStringBoolean() throws IOException {
     * conn.setInputStream(new
     * ByteArrayInputStream("foo\r\nbar\r\njoo".getBytes())); ssh.connect(null,
     * null); assertTrue(ssh.waitFor("bar", false));
     * assertTrue(ssh.waitFor("jo", false)); assertFalse(ssh.waitFor("asdf",
     * false));
     * 
     * conn.setInputStream(new
     * ByteArrayInputStream("foo\r\nbar\r\njoo".getBytes())); ssh.connect(null,
     * null); assertTrue(ssh.waitFor("bar", true)); assertTrue(ssh.waitFor("jo",
     * true)); assertFalse(ssh.waitFor("asdf", true)); }
     * 
     * public void testWaitForWithRespond() throws IOException {
     * conn.setInputStream(new
     * ByteArrayInputStream("foo\r\nbar\r\njoo".getBytes())); ssh.connect(null,
     * null); ssh.respond("bar", "jim"); assertTrue(ssh.waitFor("oo", false));
     * assertEquals("foo", ssh.lastLine()); assertTrue(ssh.waitFor("jo",
     * false)); assertFalse(ssh.waitFor("asdf", false));
     * 
     * conn.setInputStream(new
     * ByteArrayInputStream("foo\r\nbar\r\njoo".getBytes())); ssh.connect(null,
     * null); assertTrue(ssh.waitFor("bar", true)); assertTrue(ssh.waitFor("jo",
     * true)); assertFalse(ssh.waitFor("asdf", true)); }
     * 
     * public void testWaitForMuxStringArrayBoolean() throws IOException {
     * conn.setInputStream(new
     * ByteArrayInputStream("foo\r\nbar ds\r\njoo dsf".getBytes()));
     * ssh.connect(null, null); assertEquals(1, ssh.waitForMux("bsar", "bar"));
     * assertEquals(0, ssh.waitForMux("jo", "fdo")); assertEquals(-1,
     * ssh.waitForMux("asdf"));
     * 
     * }
     * 
     * public void testLastLine() throws IOException { conn.setInputStream(new
     * ByteArrayInputStream("foo\r\nbar ds\r\njoo dsf".getBytes()));
     * ssh.connect(null, null); ssh.waitFor("bar"); assertEquals("bar",
     * ssh.lastLine());
     * 
     * conn.setInputStream(new
     * ByteArrayInputStream("foo\r\nbar ds\r\njoo dsf".getBytes()));
     * ssh.connect(null, null); ssh.waitFor("bar", true); assertEquals("bar ds",
     * ssh.lastLine()); }
     * 
     * public void testGetLine() throws IOException { conn.setInputStream(new
     * ByteArrayInputStream("foo\r\nbar ds\r\njoo dsf".getBytes()));
     * ssh.connect(null, null); assertEquals("foo", ssh.getLine()); }
     */
}
