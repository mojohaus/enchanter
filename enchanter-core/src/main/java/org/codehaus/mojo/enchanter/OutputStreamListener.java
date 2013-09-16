package org.codehaus.mojo.enchanter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Use this to dump data in the communication stream to external ouput stream for debugging purpose
 * Works best with FileOutputStream
 **/
public class OutputStreamListener
    implements StreamListener
{
    private OutputStream os;

    public OutputStreamListener( OutputStream os )
    {
        this.os = os;
    }

    @Override
    public void hasRead( byte b )
    {
        try
        {
            os.write( b );
        }
        catch ( IOException e )
        {
        }
    }

    @Override
    public void hasWritten( byte[] b )
    {
    }

    @Override
    public void init( PrintWriter writer )
    {
    }

}
