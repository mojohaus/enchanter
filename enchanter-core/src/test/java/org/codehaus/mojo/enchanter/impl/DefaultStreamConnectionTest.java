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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultStreamConnectionTest
{

    private DefaultStreamConnection conn;

    private StubConnectionLibrary lib;

    @Before
    public void setUp()
        throws Exception
    {
        conn = new DefaultStreamConnection();
        lib = new StubConnectionLibrary();
        conn.setConnectionLibrary( lib );
        conn.connect( "host", "username" );
    }

    @After
    public void tearDown()
        throws Exception
    {
    }

    @Test
    public void testSend()
        throws IOException
    {
        conn.send( "foo" );
        Assert.assertEquals( "foo", lib.dumpOut() );

        conn.connect( null, null );
        conn.send( "foo^C" );
        Assert.assertEquals( "foo" + ( (char) 3 ), lib.dumpOut() );

        conn.connect( null, null );
        conn.send( "foo^M" );
        Assert.assertEquals( "foo\r\n", lib.dumpOut() );
    }

    @Test
    public void testSendLine()
        throws IOException
    {
        lib.setInputStream( new ByteArrayInputStream( "foo\r\n".getBytes() ) );
        conn.connect( null, null );
        conn.sendLine( "foo" );
        Assert.assertEquals( "foo\r\n", lib.dumpOut() );
    }

    @Test
    public void testSleep()
        throws InterruptedException
    {
        long start = System.currentTimeMillis();
        int sleepTime = 100;
        conn.sleep( sleepTime );
        long now = System.currentTimeMillis() + 1; // not very precise sleep
        long diff = now - ( start + sleepTime );
        Assert.assertTrue( "" + now + " not >= " + start + sleepTime + " diff is: " + diff, diff >= 0 );
    }

    @Test
    public void testWaitForStringBoolean()
        throws IOException
    {
        lib.setInputStream( new ByteArrayInputStream( "foo\r\nbar\r\njoo".getBytes() ) );
        conn.connect( null, null );
        Assert.assertTrue( conn.waitFor( "bar", false ) );
        Assert.assertTrue( conn.waitFor( "jo", false ) );
        Assert.assertFalse( conn.waitFor( "asdf", false ) );

        lib.setInputStream( new ByteArrayInputStream( "foo\r\nbar\r\njoo".getBytes() ) );
        conn.connect( null, null );
        Assert.assertTrue( conn.waitFor( "bar", true ) );
        Assert.assertTrue( conn.waitFor( "jo", true ) );
        Assert.assertFalse( conn.waitFor( "asdf", true ) );
    }

    @Test
    public void testWaitForWithRespond()
        throws IOException
    {
        lib.setInputStream( new ByteArrayInputStream( "foo\r\nbar\r\njoo".getBytes() ) );
        conn.connect( null, null );
        conn.respond( "bar", "jim" );
        Assert.assertTrue( conn.waitFor( "oo", false ) );
        Assert.assertEquals( "foo", conn.lastLine() );
        Assert.assertTrue( conn.waitFor( "jo", false ) );
        Assert.assertFalse( conn.waitFor( "asdf", false ) );

        lib.setInputStream( new ByteArrayInputStream( "foo\r\nbar\r\njoo".getBytes() ) );
        conn.connect( null, null );
        Assert.assertTrue( conn.waitFor( "bar", true ) );
        Assert.assertTrue( conn.waitFor( "jo", true ) );
        Assert.assertFalse( conn.waitFor( "asdf", true ) );
    }

    @Test
    public void testWaitForMuxStringArrayBoolean()
        throws IOException
    {
        lib.setInputStream( new ByteArrayInputStream( "foo\r\nbar ds\r\njoo dsf".getBytes() ) );
        conn.connect( null, null );
        Assert.assertEquals( 1, conn.waitForMux( "bsar", "bar" ) );
        Assert.assertEquals( 0, conn.waitForMux( "jo", "fdo" ) );
        Assert.assertEquals( -1, conn.waitForMux( "asdf" ) );

    }

    @Test
    public void testLastLine()
        throws IOException
    {
        lib.setInputStream( new ByteArrayInputStream( "foo\r\nbar ds\r\njoo dsf".getBytes() ) );
        conn.connect( null, null );
        conn.waitFor( "bar" );
        Assert.assertEquals( "bar", conn.lastLine() );

        lib.setInputStream( new ByteArrayInputStream( "foo\r\nbar ds\r\njoo dsf".getBytes() ) );
        conn.connect( null, null );
        conn.waitFor( "bar", true );
        Assert.assertEquals( "bar ds", conn.lastLine() );
    }

    @Test
    public void testGetLine()
        throws IOException
    {
        lib.setInputStream( new ByteArrayInputStream( "foo\r\nbar ds\r\njoo dsf".getBytes() ) );
        conn.connect( null, null );
        Assert.assertEquals( "foo", conn.getLine() );
    }

}
