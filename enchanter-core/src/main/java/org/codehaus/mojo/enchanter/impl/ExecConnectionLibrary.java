package org.codehaus.mojo.enchanter.impl;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.naming.OperationNotSupportedException;

import org.codehaus.mojo.enchanter.ConnectionLibrary;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

/**
 * Connection Library implementation via invocation of a external executable
 */
public class ExecConnectionLibrary
    implements ConnectionLibrary
{
    private Commandline cl;

    private String passwordPrompt;

    private String password;

    private Process p;

    public ExecConnectionLibrary( Commandline cl )
    {
        this.cl = cl;
    }

    public ExecConnectionLibrary( Commandline cl, String passwordPrompt, String password )
    {
        this.cl = cl;
        this.passwordPrompt = passwordPrompt;
        this.password = password;
    }

    @Override
    public void connect( String host )
        throws IOException, OperationNotSupportedException
    {
        connect();
    }

    @Override
    public void connect( String host, int port )
        throws IOException, OperationNotSupportedException
    {
        connect();
    }

    @Override
    public void connect( String host, String username )
        throws IOException, OperationNotSupportedException
    {
        connect();
    }

    @Override
    public void connect( String host, int port, String username, String password )
        throws IOException, OperationNotSupportedException
    {
        connect();
    }

    @Override
    public void connect( String host, int port, String username, String password, String privateKeyPath )
        throws IOException, OperationNotSupportedException
    {
        connect();
    }

    public void connect()
        throws IOException
    {
        try
        {
            p = cl.execute();
        }
        catch ( CommandLineException e )
        {
            throw new IOException( "Unable to execute: " + cl, e );
        }
    }

    @Override
    public void disconnect()
        throws IOException
    {
        p.destroy();
    }

    @Override
    public InputStream getInputStream()
    {
        return p.getInputStream();
    }

    @Override
    public OutputStream getOutputStream()
    {
        return p.getOutputStream();
    }

    @Override
    public void setReadTimeout( int msec )
        throws IOException, OperationNotSupportedException
    {

    }

}
