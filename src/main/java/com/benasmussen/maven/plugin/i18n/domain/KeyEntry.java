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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


/**
 * <pre>
 * KEY
 * 
 * VALUES
 *   de_DE = Welt
 *   en_US = World
 * </pre>
 * 
 * @author basmussen
 *
 */
public class KeyEntry
{

    private String key;

    private Map<String, String> localeValues = new HashMap<String, String>();

    private List<String> locales = new LinkedList<String>();

    public KeyEntry(String key)
    {
        super();
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
    
    public void setKey(String key)
    {
        this.key = key;
    }

    public void addValue(String locale, String value)
    {
        if (StringUtils.trimToNull(value) != null)
        {
            localeValues.put(locale, value);
            locales.add(locale);
        }
    }
    
    public List<String> getLocales()
    {
        return locales;
    }
    
    public Map<String, String> getLocaleValues()
    {
        return localeValues;
    }

    public boolean isEmpty()
    {
        return localeValues.isEmpty();
    }

    @Override
    public String toString()
    {
        return "[key: " + key + "]";
    }

}
