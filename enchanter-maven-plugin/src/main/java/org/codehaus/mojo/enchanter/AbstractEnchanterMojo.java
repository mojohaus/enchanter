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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

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
     * Shell type. Acceptable values are telnet,ssh
     * 
     * @parameter expression="${shellType}" default-value="telnet"
     * @since 1.0-beta-1
     */
    protected String shellType;
    
    /**
     * Script type. Acceptable values are ruby, python, and beanshell
     * 
     * @parameter expression="${scriptType}" default-value="ruby"
     * @since 1.0-beta-1
     */
    protected String scriptType;
    
    /**
     * Script type. Acceptable values are ruby, python, and beanshell
     * 
     * @parameter expression="${script}" default-value="ruby"
     * @since 1.0-beta-1
     * @required
     */
    protected File script;
    
}
