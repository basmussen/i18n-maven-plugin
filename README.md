# [i18n Maven Plugin]( http://github.com/basmussen/i18n-maven-plugin/ )

Handle internationalization and localization easily with the Apache Maven Plugin i18n.

To get started and see how the plungin works, check out the project page!


Convert your Excel sheets to resource files. Your input file `i18n.xls` will be converted to different output resource files.

**Properties**

```
customer.properties
customer_en.properties
invoice.properties
invoice_en.properties
```

**JSON**

```
customer.json
customer_en.json
invoice.json
invoice_en.json
```

**XML**

```
customer.xml
customer_en.xml
invoice.xml
invoice_en.xml
```



[bootstrap]: http://basmussen.github.io/i18n-maven-plugin

## Usage

To use this i18n Maven Plugin, include it in your `pom.xml` file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<build>
  ..
  <plugins>
    ...
    <plugin>
      <groupId>com.benasmussen.maven</groupId>
      <artifactId>i18n-maven-plugin</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <executions>
        <execution>
          <id>i18n</id>
          <phase>generate-resources</phase>
          <goals>
            <goal>i18n</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
    ...
  </plugins>
  ...
</build>
```

### Configuration

Refer to [documentation][i18n-config] for all configuration options.

[i18n-config]: http://basmussen.github.io/i18n-maven-plugin/config.html

A sample configuration file is given below:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<build>
  ..
  <plugins>
    ...
    <plugin>
      <groupId>com.benasmussen.maven</groupId>
      <artifactId>i18n-maven-plugin</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <executions>
        <execution>
          <id>i18n</id>
          <phase>generate-resources</phase>
          <goals>
            <goal>i18n</goal>
          </goals>
        </execution>
      </executions>
      <configuration>
        <localeCell>C1</localeCell>
        <keyCell>B4</keyCell>
        <files>
          <file>src/main/i18n/codes.xls</file>
        </files>
        <outputFormat>
          <format>properties</format>
          <format>json</format>
          <format>xml</format>
        </outputFormat>
      </configuration>
    </plugin>
    ...
  </plugins>
  ...
</build>
```



## Bug tracker

Have a bug or a feature request? Please create an issue here on GitHub.

http://github.com/basmussen/i18n-maven-plugin/issues


## Contributing

Fork the repository and submit pull requests.


## Author

**Ben Asmussen**

+ http://github.com/basmussen


## Copyright and license

Copyright 2014 Ben Asmussen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at:

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
