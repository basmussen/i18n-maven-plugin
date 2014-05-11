package com.benasmussen.maven.plugin.i18n;

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
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.benasmussen.maven.plugin.i18n.domain.ResourceEntry;
import com.benasmussen.maven.plugin.i18n.io.JsonResourceWriter;
import com.benasmussen.maven.plugin.i18n.io.PropertiesResourceWriter;
import com.benasmussen.maven.plugin.i18n.io.ResourceReader;
import com.benasmussen.maven.plugin.i18n.io.ResourceWriter;
import com.benasmussen.maven.plugin.i18n.io.XmlResourceWriter;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "i18n", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class InternationalizationMojo extends AbstractMojo
{

    private static final String DEFAULT_FILE = "src/main/i18n/i18n.xls";

    public static final String FORMAT_JSON = "json";
    public static final String FORMAT_XML = "xml";
    public static final String FORMAT_PROPERTIES = "properties";

    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.outputDirectory}", property = "outputDirectory", required = true)
    private File outputDirectory;

    /**
     * Output encoding
     */
    @Parameter(defaultValue = "UTF-8", property = "outputEncoding", required = true)
    private String outputEncoding;

    /**
     * Input excel files
     * 
     * <pre>
     *   <files>
     *     <param>translate.xls</param>
     *     <param>codes.xls</param>
     *   </files>
     * </pre>
     */
    @Parameter(property = "files", required = true)
    private List<String> files;

    @Parameter(property = "keyCell", defaultValue = "A2")
    private String keyCell;

    @Parameter(property = "localeCell", defaultValue = "B1")
    private String localeCell;

    /**
     * Output format
     * 
     * <pre>
     * Properties
     * JSON
     * XML
     * </pre>
     * 
     * <pre>
     *  <outputFormat>
     *     <format>properties</format>
     *     <format>json</format>
     *     <format>xml</format>
     *  </outputFormat>
     * </pre>
     */
    @Parameter(property = "outputFormat", required = true)
    private List<String> outputFormat;

    public void execute() throws MojoExecutionException
    {
        String currentFile = null;
        InputStream is = null;
        try
        {
            if (!outputDirectory.exists())
            {
                getLog().info("Create output directory: " + outputDirectory);
                outputDirectory.mkdirs();
            }

            // use default file if empty
            if (files == null || files.isEmpty())
            {
                files = new ArrayList<String>();
                files.add(DEFAULT_FILE);
            }

            // loop files
            for (String file : files)
            {
                currentFile = file;

                getLog().info("Process file " + file);

                is = new FileInputStream(file);

                // xls resource reader
                ResourceReader resourceReader = new ResourceReader(is);
                resourceReader.setKeyCell(keyCell);
                resourceReader.setLocaleCell(localeCell);

                resourceReader.process();

                List<ResourceEntry> resultEntries = resourceReader.getEntries();

                List<ResourceWriter> resourceWriter = new ArrayList<ResourceWriter>();

                // properties
                if (outputFormat.contains(FORMAT_PROPERTIES))
                {
                    resourceWriter.add(new PropertiesResourceWriter());
                }
                // json
                if (outputFormat.contains(FORMAT_JSON))
                {
                    resourceWriter.add(new JsonResourceWriter());
                }

                // xml
                if (outputFormat.contains(FORMAT_XML))
                {
                    resourceWriter.add(new XmlResourceWriter());
                }

                // process output writer
                for (ResourceWriter writer : resourceWriter)
                {
                    writer.setOutputEnconding(outputEncoding);
                    writer.setOutputFolder(outputDirectory);
                    writer.setResourceEntries(resultEntries);
                    writer.write();
                }

                IOUtils.closeQuietly(is);
            }
        }
        catch (Exception e)
        {
            throw new MojoExecutionException("Error processing file " + currentFile, e);
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    public void setOutputDirectory(File outputDirectory)
    {
        this.outputDirectory = outputDirectory;
    }

    public void setOutputEncoding(String outputEncoding)
    {
        this.outputEncoding = outputEncoding;
    }

    public void setFiles(List<String> files)
    {
        this.files = files;
    }

}
