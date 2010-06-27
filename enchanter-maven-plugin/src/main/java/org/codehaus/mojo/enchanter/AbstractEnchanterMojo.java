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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.mojo.enchanter.impl.DefaultStreamConnection;
import org.codehaus.mojo.enchanter.impl.GanymedSSHLibrary;
import org.codehaus.mojo.enchanter.impl.TelnetConnectionLibrary;
import org.codehaus.plexus.util.FileUtils;

public abstract class AbstractEnchanterMojo
    extends AbstractMojo
{
    /**
     * Internal
     * 
     * @parameter expression="${project}"
     * @readonly
     * @since 1.0-beta-1
     */
    protected MavenProject project;

    /**
     * Shell type. Acceptable values are telnet and ssh
     * 
     * @parameter expression="${enchanter.type}" default-value="telnet"
     * @required
     * @since 1.0-beta-1
     */
    protected String connectionType;

    /**
     * Connection user name to login to remote system
     * 
     * @parameter expression="${enchanter.host}" default-value="localhost"
     * @required
     * @since 1.0-beta-1
     */
    protected String host;

    /**
     * Connection user name to login to remote system
     * 
     * @parameter expression="${enchanter.username}" default-value="${os.username}"
     * @required
     * @since 1.0-beta-1
     */
    protected String username;

    /**
     * Connection password to login to remote system
     * 
     * @parameter expression="${enchanter.password}" default-value=""
     * @required
     * @since 1.0-beta-1
     */
    protected String password;

    
    protected ScriptEngine getScriptEngine( File script )
        throws MojoExecutionException
    {
        String fileExt = FileUtils.getExtension( script.getName() );
        
        ScriptEngine engine = new ScriptEngineManager().getEngineByExtension( fileExt );
        
        if ( engine == null )
        {
            throw new MojoExecutionException( "Scripting engine not found for: " + script );
        }
        
        return engine;
    }

    protected StreamConnection getStreamConnection()
        throws MojoExecutionException
    {
        if ( "ssh".equals( this.connectionType ) )
        {
            return createSshStreamConnection();
        }
        else if ( "telnet".equals( this.connectionType ) )
        {
            return createTelnetStreamConnection();
        }
        else
        {
            throw new MojoExecutionException( "Invalid connection type: " + connectionType );
        }

    }

    private StreamConnection createTelnetStreamConnection( )
    {
        DefaultStreamConnection streamConnection = new DefaultStreamConnection();
        TelnetConnectionLibrary connLib = new TelnetConnectionLibrary();
        streamConnection.setConnectionLibrary( connLib );
        return streamConnection;
    }

    private StreamConnection createSshStreamConnection( )
    {
        DefaultStreamConnection streamConnection = new DefaultStreamConnection();
        GanymedSSHLibrary connLib = new GanymedSSHLibrary();
        streamConnection.setConnectionLibrary( connLib );
        return streamConnection;
    }

}
