package the.bytecode.club.bytecodeviewer.resources;

import java.io.File;
import java.util.LinkedHashMap;

import org.objectweb.asm.tree.ClassNode;
import the.bytecode.club.bytecodeviewer.api.ASMUtil;
import the.bytecode.club.bytecodeviewer.util.LazyNameUtil;

/***************************************************************************
 * Bytecode Viewer (BCV) - Java & Android Reverse Engineering Suite        *
 * Copyright (C) 2014 Kalen 'Konloch' Kinloch - http://bytecodeviewer.com  *
 *                                                                         *
 * This program is free software: you can redistribute it and/or modify    *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation, either version 3 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. *
 ***************************************************************************/

/**
 * Represents a loaded file in the form of a resource container
 * with all of the contents inside of it.
 *
 * @author Konloch
 */

public class ResourceContainer
{
    public File file;
    public String name;
    public File APKToolContents = null;
    
    public LinkedHashMap<String, byte[]> resourceFiles = new LinkedHashMap<>();
    public LinkedHashMap<String, byte[]> resourceClassBytes = new LinkedHashMap<>();
    public LinkedHashMap<String, ClassNode> resourceClasses = new LinkedHashMap<>();
    
    public ResourceContainer(File f)
    {
        this(f, f.getName());
    }

    public ResourceContainer(File f, String name)
    {
        this.file = f;
        this.name = LazyNameUtil.applyNameChanges(name);
    }
    
    /**
     * Returns the ClassNode resource for the specified resource key (full name path)
     */
    public ClassNode getClassNode(String resourceName)
    {
        return resourceClasses.get(resourceName);
    }
    
    /**
     * Returns the unique 'working' name for container + resource look up.
     * This is used to look up a specific resource inside of this specific
     * container when you need to iterate through all opened containers
     */
    public String getWorkingName(String resourceName)
    {
        return file.getAbsolutePath() + ">" + resourceName;
    }
    
    /**
     * Returns the resource bytes for the specified resource key (full name path)
     */
    public byte[] getBytes(String resourceName)
    {
        if(resourceClassBytes.containsKey(resourceName))
            return resourceClassBytes.get(resourceName);
        else
            return resourceFiles.get(resourceName);
    }
    
    /**
     * Updates the ClassNode reference on the resourceClass list and resourceClassBytes list
     */
    public ResourceContainer updateNode(ClassNode oldNode, ClassNode newNode)
    {
        //update all classnode references for ASM
        if (resourceClasses.containsKey(oldNode.name))
        {
            resourceClasses.remove(oldNode.name);
            resourceClasses.put(newNode.name, newNode);
        }
        
        //update the resource bytes
        String oldResourceKey = oldNode.name + ".class";
        String newResourceKey = newNode.name + ".class";
        if(resourceClassBytes.containsKey(oldResourceKey))
        {
            resourceClassBytes.remove(oldResourceKey);
            resourceClassBytes.put(newResourceKey, ASMUtil.nodeToBytes(newNode));
        }
        return this;
    }
    
    /**
     * Clear this container's resources
     */
    public ResourceContainer clear()
    {
        resourceFiles.clear();
        resourceClassBytes.clear();
        resourceClasses.clear();
        return this;
    }
    
    /**
     * Copy a resource container's resources into this container
     */
    public ResourceContainer copy(ResourceContainer copyFrom)
    {
        resourceFiles.putAll(copyFrom.resourceFiles);
        resourceClassBytes.putAll(copyFrom.resourceClassBytes);
        resourceClasses.putAll(copyFrom.resourceClasses);
        return this;
    }
}
