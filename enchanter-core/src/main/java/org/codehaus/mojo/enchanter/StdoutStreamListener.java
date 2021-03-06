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
