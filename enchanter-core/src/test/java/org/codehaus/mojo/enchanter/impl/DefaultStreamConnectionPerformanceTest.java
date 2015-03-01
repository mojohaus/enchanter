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
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class DefaultStreamConnectionPerformanceTest
{

    private DefaultStreamConnection ssh;

    private StubConnectionLibrary conn;

    private byte[] bibleBytes;

    @Before
    public void setUp()
        throws Exception
    {
        InputStream in = getClass().getResourceAsStream( "/kjv10.txt" );
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while ( ( len = in.read( buffer ) ) > -1 )
        {
            bout.write( buffer, 0, len );
        }
        bibleBytes = bout.toByteArray();

        ssh = new DefaultStreamConnection();
        conn = new StubConnectionLibrary();
        conn.setInputStream( new ByteArrayInputStream( bibleBytes ) );
        ssh.setConnectionLibrary( conn );
        ssh.connect( "host", "username" );
    }

    @Test
    public void testLotsOfWaitFor()
        throws Exception
    {
        long start = System.currentTimeMillis();
        int count = 0;
        while ( ssh.waitFor( "God" ) )
        {
            count++;
        }
        long now = System.currentTimeMillis();
        System.out.println( "Found " + count + " in " + ( now - start ) + " ms" );
    }

}
