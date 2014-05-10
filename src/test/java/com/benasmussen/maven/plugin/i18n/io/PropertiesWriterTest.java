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

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.benasmussen.maven.plugin.i18n.domain.KeyEntry;
import com.benasmussen.maven.plugin.i18n.domain.ResourceEntry;

/**
 * Properties writer test
 * 
 * @author Ben Asmussen
 *
 */
public class PropertiesWriterTest
{

    private PropertiesResourceWriter resourceWriter;

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    private File tempDirectory = null;

    @Before
    public void setUp() throws Exception
    {
        tempDirectory = temp.getRoot();
        resourceWriter = new PropertiesResourceWriter();
        resourceWriter.setOutputFolder(tempDirectory);
    }

    @After
    public void tearDown() throws Exception
    {
        tempDirectory.delete();
    }

    @Test
    public void testWrite() throws FileNotFoundException, IOException
    {
        ResourceEntry resourceEntry = new ResourceEntry("customer");
        resourceEntry.getLocales().add("DEFAULT");
        resourceEntry.getLocales().add("de");

        KeyEntry keyEntry1 = new KeyEntry("CUSTOMER");
        resourceEntry.add(keyEntry1);

        keyEntry1.getLocaleValues().put("DEFAULT", "Customer");
        keyEntry1.getLocales().add("DEFAULT");
        keyEntry1.getLocales().add("de");
        keyEntry1.getLocaleValues().put("de", "Kunde");

        List<ResourceEntry> entries = new LinkedList<ResourceEntry>();
        entries.add(resourceEntry);

        resourceWriter.setResourceEntries(entries);

        resourceWriter.write();

        // locale default
        File fileDefault = new File(tempDirectory, "customer.properties");
        assertTrue(fileDefault.exists());

        Properties propertiesDefault = new Properties();
        propertiesDefault.load(new FileReader(fileDefault));
        assertEquals("Customer", propertiesDefault.get("CUSTOMER"));

        // locale de
        File fileDe = new File(tempDirectory, "customer_de.properties");
        assertTrue(fileDe.exists());

        Properties propertiesDe = new Properties();
        propertiesDe.load(new FileReader(fileDe));

        assertEquals("Kunde", propertiesDe.get("CUSTOMER"));

    }

}
