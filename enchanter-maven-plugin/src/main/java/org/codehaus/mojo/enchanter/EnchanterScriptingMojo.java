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

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.IOUtil;

/**
 * Run Enchanter script using any any dynamic supported language ( ie ruby, javascript, python, groovy, beanshel, etc )
 * @goal run
 * @requiresProject false
 */

public class EnchanterScriptingMojo
    extends AbstractEnchanterMojo
{

    /**
     * List of scripts files run. The first file in the list determines scripting engine type ( ie .rb is ruby )
     * Mixing scripting types are not allowed
     * @since 1.0-beta-1
     * @parameter
     */
    private File [] scripts = new File[0];

    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        
        loadUserInfoFromSettings();

        if ( scripts.length == 0 )
        {
            this.getLog().warn( "No script(s) to run" );
            return;
        }

        Reader reader = null;
        StreamConnection stream = null;

        try
        {

            stream = this.getStreamConnection();
            stream.setDebug( true );

            ScriptEngine engine = this.getScriptEngine();
            engine.put( "conn", stream );
            engine.put( "host", this.host );
            engine.put( "username", this.username );
            engine.put( "password", this.password );

            for ( File script : scripts )
            {
                reader = new FileReader( script );
                engine.eval( reader );
                IOUtil.close( reader );
                reader = null;
            }
        }
        catch ( Exception e )
        {
            throw new MojoExecutionException( "Error in script execution.", e );
        }
        finally
        {
            IOUtil.close( reader );

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

    protected ScriptEngine getScriptEngine()
        throws MojoExecutionException
    {
        if ( this.scripts.length != 0  )
        {
            return this.getScriptEngine( this.scripts[0] );
        }

        return null;
    }

}
