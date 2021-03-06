package org.codehaus.mojo.enchanter;

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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ScriptRecorderTest
{

    ScriptRecorder rec = null;

    @Before
    public void setUp()
        throws Exception
    {
        rec = new ScriptRecorder();
    }


    @Test
    public void testHasRead()
        throws Exception
    {
        for ( int x = 0; x < 10; x++ )
        {
            rec.hasRead( String.valueOf( x ).getBytes()[0] );
        }

        Assert.assertEquals( "0123456789", new String( rec.lastChars ) );
    }

    @Test
    public void testHasRead_under()
        throws Exception
    {
        for ( int x = 0; x < 5; x++ )
        {
            rec.hasRead( String.valueOf( x ).getBytes()[0] );
        }
        Assert.assertEquals( "01234", rec.getLastChars() );
    }

    @Test
    public void testHasRead_overflow()
        throws Exception
    {
        for ( int x = 0; x < 10; x++ )
        {
            rec.hasRead( String.valueOf( x ).getBytes()[0] );
        }
        rec.hasRead( "3".getBytes()[0] );
        Assert.assertEquals( "3123456789", new String( rec.lastChars ) );
    }

    @Test
    public void testGetLastChars_start()
    {
        rec.lastChars = "0123456789".toCharArray();
        rec.lastCharsPos = 0;
        Assert.assertEquals( "0123456789", rec.getLastChars() );
    }

    @Test
    public void testGetLastChars_midpoint()
    {
        rec.lastChars = "0123456789".toCharArray();
        rec.lastCharsPos = 2;
        Assert.assertEquals( "2345678901", rec.getLastChars() );
    }

    public void testGetLastChars_end()
    {
        rec.lastChars = "0123456789".toCharArray();
        rec.lastCharsPos = 9;
        Assert.assertEquals( "9012345678", rec.getLastChars() );
    }

    @Test
    public void testGetLastChars_newlineMid()
    {
        rec.lastChars = "012\r\n56789".toCharArray();
        rec.lastCharsPos = 0;
        Assert.assertEquals( "56789", rec.getLastChars() );
    }

    @Test
    public void testGetLastChars_newlineEnd()
    {
        rec.lastChars = "012345678\n".toCharArray();
        rec.lastCharsPos = 0;
        Assert.assertEquals( "", rec.getLastChars() );
    }

}
