package com.benasmussen.maven.plugin.i18n.domain;

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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Ben Asmusssen
 *
 */
public class ResourceEntry
{

    public static final String DEFAULT_LOCALE = "DEFAULT";

    String name;

    LinkedList<KeyEntry> entries = new LinkedList<KeyEntry>();

    private List<String> locales = new LinkedList<String>();

    public ResourceEntry(String name)
    {
        this.name = name;
    }

    public void add(KeyEntry keyEntry)
    {
        entries.add(keyEntry);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LinkedList<KeyEntry> getEntries()
    {
        return entries;
    }

    public List<String> getLocales()
    {
        return locales;
    }

    public void setLocales(Collection<String> locales)
    {
        this.locales = new LinkedList<String>(locales);
    }

}
