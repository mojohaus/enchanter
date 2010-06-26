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
     * Script path. 
     * 
     * @parameter expression="${enchanter.src}" 
     * @since 1.0-beta-1
     */
    protected File src;

    /**
     * List of files containing SQL statements to load.
     * @since 1.0
     * @parameter
     */
    private List<File> srcFiles = new ArrayList<File>();

    public void execute()
        throws MojoExecutionException, MojoFailureException
    {

        srcFiles.add( 0, src );
        
        if ( srcFiles.isEmpty() )
        {
            this.getLog().warn( "No script(s) to run" );
            return;
        }

        Reader reader = null;
        StreamConnection stream = null;

        try
        {
            ScriptEngine engine = this.getScriptEngine();

            stream = this.getStreamConnection();
            
            engine.put( "stream", stream );
            engine.put( "host", this.host );
            engine.put( "username", this.username );
            engine.put( "password", this.password );

            for ( File script: srcFiles )
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
        if ( ! this.srcFiles.isEmpty() )
        {
            return this.getScriptEngine( this.srcFiles.get( 0 ) );
        }

        return null;
    }

}
