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

import java.io.FileReader;
import java.io.Reader;

import javax.script.ScriptEngine;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Run Enchanter with a script
 * @goal enchanter
 * @requiresProject false
 */

public class EnchanterScriptMojo
    extends AbstractEnchanterMojo
{

    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        Reader reader = null; 
        StreamConnection stream = null; 
        
        try 
        {
            ScriptEngine engine =  this.getScriptEngine();
            
            stream = this.getStreamConnection();
            engine.put( "STREAM", stream );
            
            reader = new FileReader( script );
            engine.eval( reader );
        }
        catch ( Exception e )
        {
            throw new MojoExecutionException( "Error in script execution.", e );
        }
        finally
        {
            if ( stream != null )
            {
                try
                {
                    stream.disconnect();
                }
                catch ( Exception e )
                {
                    
                }
            }
        }
    }
}
