package org.codehaus.mojo.enchanter;

import java.io.PrintWriter;

/**
 * The stream filter is the base interface for classes that want a copy of the
 * bytes read from and written to the stream.
 */
public interface StreamListener
{

    /**
     * Receives the block of data read from the back end.
     * 
     * @param b the data
     * @return the amount of bytes actually read
     */
    public void hasRead( byte b );

    /**
     * Called when data has been written to the stream from a filter
     * 
     * @param s The data
     */
    public void hasWritten( byte[] b );

    /**
     * Initializes listener
     * @param writer the writer to write to the ssh session with
     */
    public void init( PrintWriter writer );

}