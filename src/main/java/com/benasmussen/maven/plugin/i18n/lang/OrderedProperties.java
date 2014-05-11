package com.benasmussen.maven.plugin.i18n.lang;

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
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This class is a decorator of {@link Properties}.
 * <p>
 * It uses {@link LinkedHashMap} instead of {@link Hashtable} to preserve
 * properties order when loading from streams.
 * </p>
 */
public class OrderedProperties extends Properties
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Map<String, String> entries = new LinkedHashMap<String, String>();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Enumeration keys()
    {
        return Collections.enumeration(entries.keySet());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Enumeration elements()
    {
        return Collections.enumeration(entries.values());
    }

    public boolean contains(Object value)
    {
        return entries.containsValue(value);
    }

    @SuppressWarnings("unchecked")
    public void putAll(Map<? extends Object, ? extends Object> map)
    {
        entries.putAll((Map<? extends String, ? extends String>) map);
    }

    public int size()
    {
        return entries.size();
    }

    public boolean isEmpty()
    {
        return entries.isEmpty();
    }

    public boolean containsKey(Object key)
    {
        return entries.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return entries.containsValue(value);
    }

    public String get(Object key)
    {
        return entries.get(key);
    }

    public String put(Object key, Object value)
    {
        return entries.put((String) key, (String) value);
    }

    public Object remove(Object key)
    {
        return entries.remove(key);
    }

    public void clear()
    {
        entries.clear();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Set keySet()
    {
        return entries.keySet();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection values()
    {
        return entries.values();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Set entrySet()
    {
        return entries.entrySet();
    }

    public boolean equals(Object o)
    {
        return entries.equals(o);
    }

    public int hashCode()
    {
        return entries.hashCode();
    }
    
}
