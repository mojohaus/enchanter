package org.codehaus.mojo.enchanter;

import java.io.PrintWriter;

/**
 * Use this to dump data in the communication stream to console for debugging purpose
 */

public class StdoutStreamListener
    implements StreamListener
{

    @Override
    public void hasRead( byte b )
    {
        System.out.write( b );
    }

    @Override
    public void hasWritten( byte[] b )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void init( PrintWriter writer )
    {
        // TODO Auto-generated method stub

    }

}
