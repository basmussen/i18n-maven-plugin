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
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.benasmussen.maven.plugin.i18n.domain.KeyEntry;
import com.benasmussen.maven.plugin.i18n.domain.ResourceEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json resource writer
 *
 * @author basmussen
 *
 */
public class JsonResourceWriter extends AbstractResourceWriter<LinkedHashMap<String, String>>
{
    public JsonResourceWriter()
    {
        setEscaping(Escaping.NONE);
    }

    @Override
    protected void before(ResourceEntry resourceEntry)
    {
        super.before(resourceEntry);

        List<String> locales = resourceEntry.getLocales();
        for (String locale : locales)
        {
            output.put(locale, new LinkedHashMap<String, String>());
        }

    }

    @Override
    public void writeEntry(KeyEntry keyEntry)
    {
        Set<String> locales = keyEntry.getLocaleValues().keySet();
        for (String locale : locales)
        {
            LinkedHashMap<String, String> jsonMap = output.get(locale);

            String value = keyEntry.getLocaleValues().get(locale);

            jsonMap.put(keyEntry.getKey(), escaping.apply(value));
        }
    }

    @Override
    protected void after(ResourceEntry resourceEntry)
    {
        super.after(resourceEntry);

        Set<String> keySet = output.keySet();
        for (String locale : keySet)
        {
            String filename = getFilename(resourceEntry, locale);

            Writer writer = null;
            try
            {
                File outputFile = new File(outputFolder, filename);
                writer = new OutputStreamWriter(new FileOutputStream(outputFile), getOutputEnconding());

                LinkedHashMap<String, String> jsonMap = output.get(locale);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(jsonMap);
                writer.write(json);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                IOUtils.closeQuietly(writer);
            }
        }

    }

    @Override
    public String getFilenameSuffix()
    {
        return ".json";
    }

}
