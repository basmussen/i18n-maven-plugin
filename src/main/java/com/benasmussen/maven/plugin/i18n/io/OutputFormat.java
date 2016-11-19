package com.benasmussen.maven.plugin.i18n.io;

/**
 * Supported output formats of the plugin.
 *
 * @author matemeszaros
 *         2015.10.26.
 */
public enum OutputFormat
{
    properties {
        @Override
        public ResourceWriter getWriter()
        {
            return new PropertiesResourceWriter();
        }
    },
    json {
        @Override
        public ResourceWriter getWriter()
        {
            return new JsonResourceWriter();
        }
    },
    xml {
        @Override
        public ResourceWriter getWriter()
        {
            return new XmlResourceWriter();
        }
    };

    public abstract ResourceWriter getWriter();
}
