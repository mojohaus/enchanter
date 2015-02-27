package org.codehaus.mojo.enchanter.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.naming.OperationNotSupportedException;

import org.codehaus.mojo.enchanter.ConnectionLibrary;

public class ConsoleConnectionLibrary
    implements ConnectionLibrary
{

    public void connect( String host )
        throws IOException, OperationNotSupportedException
    {
    }

    public void connect( String host, int port )
        throws IOException, OperationNotSupportedException
    {
    }

    public void connect( String host, String username )
        throws IOException, OperationNotSupportedException
    {
    }

    public void connect( String host, int port, String username, String password )
        throws IOException, OperationNotSupportedException
    {
    }

    public void connect( String host, int port, String username, String password, String privateKeyPath )
        throws IOException, OperationNotSupportedException
    {
    }

    public void disconnect()
        throws IOException
    {
    }

    public InputStream getInputStream()
    {
        return System.in;
    }

    public OutputStream getOutputStream()
    {
        return System.out;
    }

    public void setReadTimeout( int msec )
        throws IOException, OperationNotSupportedException
    {
        //see http://www.javaspecialists.eu/archive/Issue153.html
    }

}
