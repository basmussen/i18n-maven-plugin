package com.benasmussen.maven.plugin.i18n.io;

/*
 * #%L
 * Maven Plugin i18n
 * %%
 * Copyright (C) 2014 Ben Asmussen <opensource@ben-asmussen.com>
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.benasmussen.maven.plugin.i18n.domain.KeyEntry;
import com.benasmussen.maven.plugin.i18n.domain.ResourceEntry;

public abstract class AbstractResourceWriter<T> implements ResourceWriter
{

    File outputFolder;

    Map<String, T> output;

    String outputEnconding = "UTF-8";

    List<ResourceEntry> resourceEntries;

    Escaping escaping;

    protected void before(ResourceEntry resourceEntry)
    {
        output = new HashMap<String, T>();

        // template
    }

    public void write()
    {
        for (ResourceEntry resourceEntry : resourceEntries)
        {
            before(resourceEntry);

            doWrite(resourceEntry);

            after(resourceEntry);
        }
    }

    protected void after(ResourceEntry resourceEntry)
    {
        // template
    }

    protected void doWrite(ResourceEntry resourceEntry)
    {
        LinkedList<KeyEntry> entries = resourceEntry.getEntries();
        for (KeyEntry keyEntry : entries)
        {
            writeEntry(keyEntry);
        }
    }

    protected abstract void writeEntry(KeyEntry keyEntry);

    protected abstract String getFilenameSuffix();

    public String getFilename(ResourceEntry resourceEntry, String locale)
    {
        StringBuilder sb = new StringBuilder(resourceEntry.getName());

        if (StringUtils.trimToNull(locale) != null && !ResourceEntry.DEFAULT_LOCALE.equalsIgnoreCase(locale))
        {
            sb.append("_");
            sb.append(locale);
        }
        sb.append(getFilenameSuffix());

        return sb.toString();
    }

    public List<ResourceEntry> getResourceEntries()
    {
        return resourceEntries;
    }

    public void setResourceEntries(List<ResourceEntry> resourceEntries)
    {
        this.resourceEntries = resourceEntries;
    }

    public File getOutputFolder()
    {
        return outputFolder;
    }

    public void setOutputFolder(File outputFolder)
    {
        this.outputFolder = outputFolder;
    }

    public String getOutputEnconding()
    {
        return outputEnconding;
    }

    public void setOutputEnconding(String outputEnconding)
    {
        this.outputEnconding = outputEnconding;
    }

    public void setEscaping(Escaping escaping)
    {
        this.escaping = escaping;
    }

}
