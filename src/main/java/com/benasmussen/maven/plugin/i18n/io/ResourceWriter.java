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
import java.util.List;

import com.benasmussen.maven.plugin.i18n.domain.ResourceEntry;

public interface ResourceWriter
{
    void setOutputEnconding(String outputEnconding);

    void setOutputFolder(File outputFolder);

    void setResourceEntries(List<ResourceEntry> resourceEntries);

    void setEscaping(Escaping escaping);

    void write();
}
