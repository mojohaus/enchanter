package org.codehaus.mojo.enchanter;

import org.codehaus.mojo.enchanter.ScriptRecorder;

import junit.framework.TestCase;

public class ScriptRecorderTest
    extends TestCase
{

    ScriptRecorder rec = null;

    public ScriptRecorderTest( String arg0 )
    {
        super( arg0 );
    }

    protected void setUp()
        throws Exception
    {
        super.setUp();
        rec = new ScriptRecorder();
    }

    protected void tearDown()
        throws Exception
    {
        super.tearDown();
    }

    public void testHasRead()
        throws Exception
    {
        for ( int x = 0; x < 10; x++ )
        {
            rec.hasRead( String.valueOf( x ).getBytes()[0] );
        }
        assertEquals( "0123456789", new String( rec.lastChars ) );
    }

    public void testHasRead_under()
        throws Exception
    {
        for ( int x = 0; x < 5; x++ )
        {
            rec.hasRead( String.valueOf( x ).getBytes()[0] );
        }
        assertEquals( "01234", rec.getLastChars() );
    }

    public void testHasRead_overflow()
        throws Exception
    {
        for ( int x = 0; x < 10; x++ )
        {
            rec.hasRead( String.valueOf( x ).getBytes()[0] );
        }
        rec.hasRead( "3".getBytes()[0] );
        assertEquals( "3123456789", new String( rec.lastChars ) );
    }

    public void testGetLastChars_start()
    {
        rec.lastChars = "0123456789".toCharArray();
        rec.lastCharsPos = 0;
        assertEquals( "0123456789", rec.getLastChars() );
    }

    public void testGetLastChars_midpoint()
    {
        rec.lastChars = "0123456789".toCharArray();
        rec.lastCharsPos = 2;
        assertEquals( "2345678901", rec.getLastChars() );
    }

    public void testGetLastChars_end()
    {
        rec.lastChars = "0123456789".toCharArray();
        rec.lastCharsPos = 9;
        assertEquals( "9012345678", rec.getLastChars() );
    }

    public void testGetLastChars_newlineMid()
    {
        rec.lastChars = "012\r\n56789".toCharArray();
        rec.lastCharsPos = 0;
        assertEquals( "56789", rec.getLastChars() );
    }

    public void testGetLastChars_newlineEnd()
    {
        rec.lastChars = "012345678\n".toCharArray();
        rec.lastCharsPos = 0;
        assertEquals( "", rec.getLastChars() );
    }

}
