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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.mojo.enchanter.impl.DefaultStreamConnection;
import org.codehaus.mojo.enchanter.impl.GanymedSSHLibrary;
import org.codehaus.mojo.enchanter.impl.TelnetConnectionLibrary;
import org.codehaus.plexus.util.FileUtils;

/**
 * Run Enchanter script using any any dynamic supported language ( ie ruby, javascript, python, groovy, beanshel, etc )
 * @goal run
 * @requiresProject true
 */

@SuppressWarnings("restriction")
public class EnchanterScriptingMojo
    extends AbstractEnchanterMojo
{

    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        DefaultStreamConnection stream = new DefaultStreamConnection();

        ConnectionLibrary conn = this.createConnection( this.connectionType );
        stream.setConnectionLibrary( conn );
        
        ScriptEngine scriptEngine = this.createScriptingEngine();

        try
        {
            scriptEngine.put( "STREAM", stream );
            scriptEngine.eval( script.getAbsolutePath() );

        }
        catch ( ScriptException e )
        {
            throw new MojoExecutionException( "Error detected: ", e );
        }
        finally
        {
            if ( conn != null )
            {
                try
                {
                    conn.disconnect();
                }
                catch ( Exception e )
                {
                }
            }
        }        
    }
    
    private ScriptEngine createScriptingEngine()
    {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        return scriptEngineManager.getEngineByExtension( FileUtils.extension( this.script.getName() ) );
    }
    
    private ConnectionLibrary createConnection( String connectionType )
        throws MojoExecutionException, MojoFailureException
    {
        if ( "telnet".equals( connectionType ) )
        {
            return new TelnetConnectionLibrary();
        }
        else if ( "ssh".equals( connectionType ) )
        {
            return new GanymedSSHLibrary();
        }
        
        throw new MojoFailureException( "Unknown connection type: " + this.connectionType );
    }

}
