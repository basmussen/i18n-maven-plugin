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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.benasmussen.maven.plugin.i18n.domain.ResourceEntry;
import com.benasmussen.maven.plugin.i18n.io.Escaping;
import com.benasmussen.maven.plugin.i18n.io.OutputFormat;
import com.benasmussen.maven.plugin.i18n.io.ResourceReader;
import com.benasmussen.maven.plugin.i18n.io.ResourceWriter;

/**
 * Goal which create internationalization resource files from i18n.xls
 */
@SuppressWarnings("unused")
@Mojo(name = "i18n", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class InternationalizationMojo extends AbstractMojo
{

    private static final String DEFAULT_FILE = "src/main/i18n/i18n.xls";

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
     * Excel files
     */
    @Parameter(property = "files", required = true)
    private List<String> files;

    /**
     * Excel key cell
     */
    @Parameter(property = "keyCell", defaultValue = "A2")
    private String keyCell;

    /**
     * Excel locale cell
     */
    @Parameter(property = "localeCell", defaultValue = "B1")
    private String localeCell;

    /**
     * Resource file output format. Supported values <br>
     * properties <br/>
     * json <br/>
     * xml
     */
    @Parameter(property = "outputFormat", required = true)
    private List<OutputFormat> outputFormat;

    /**
     * Override escaping method for special characters when writing resources
     *
     * All defaults can be overriden with the <code>escaping</code> parameter.
     *
     * @see #escaping
     */
    @Parameter(property = "escapings")
    private Map<OutputFormat, Escaping> escapings = new HashMap<>();

    /**
     * Overrides the default escaping method for add formats.
     * Supported values: <br/>
     * <code>NONE</code><br/>
     * <code>JAVA</code> <br/>
     * <code>HTML</code><br/>
     * <code>XML</code><br/>
     *
     * Defaults:<br/>
     * properties: JAVA<br/>
     * json: NONE<br/>
     * xml: NONE
     *
     * For custom overrides use the <code>escapings</code> map.
     *
     * @see #escapings
     */
    @Parameter(property = "escaping")
    private Escaping escaping;

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
				
				File f = new File(file);

				if (f.exists()) 
				{
					getLog().info("Process file " + file);
					
					is = new FileInputStream(f);

					// xls resource reader
					ResourceReader resourceReader = new ResourceReader(is);
					resourceReader.setKeyCell(keyCell);
					resourceReader.setLocaleCell(localeCell);

					resourceReader.process();

					List<ResourceEntry> resultEntries = resourceReader.getEntries();

					// process output writer
					for (OutputFormat format : outputFormat)
					{
						// get writer based on specified format
						ResourceWriter writer = format.getWriter();
						// overwrite the default escaping
						if (escapings.containsKey(format))
						{
							writer.setEscaping(escapings.get(format));
						}
						else if (escaping != null)
						{
							writer.setEscaping(escaping);
						}
						// set other properties
						writer.setOutputEnconding(outputEncoding);
						writer.setOutputFolder(outputDirectory);
						writer.setResourceEntries(resultEntries);
						writer.write();
					}

					IOUtils.closeQuietly(is);
				}
				else
				{
					getLog().info("Skipped, file not found: " + file);
				}
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

    public void setKeyCell(String keyCell)
    {
        this.keyCell = keyCell;
    }

    public void setLocaleCell(String localeCell)
    {
        this.localeCell = localeCell;
    }

    public void setOutputFormat(List<OutputFormat> outputFormat)
    {
        this.outputFormat = outputFormat;
    }

    public void setEscapings(Map<OutputFormat, Escaping> escapings)
    {
        this.escapings = escapings;
    }

    public void setEscaping(Escaping escaping)
    {
        this.escaping = escaping;
    }
}
