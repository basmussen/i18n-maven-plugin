# [i18n Maven Plugin]( http://github.com/basmussen/i18n-maven-plugin/ )

Handle internationalization and localization easily with the i18n Maven Plugin.

To get started and see how the plungin works, check out the [project page][project-page]!

[project-page]: http://basmussen.github.io/i18n-maven-plugin/


Convert your Excel sheets to resource files. Your input file `i18n.xls` will be converted to different output resource files.

**Properties**

```
customer.properties
customer_en.properties
invoice.properties
invoice_en.properties
```
```
#Generated file customer.properties
#Sat May 10 01:56:34 CEST 2014
CUSTOMER=Customer
FIRSTNAME=Firstname
LASTNAME=Lastname

```

**JSON**

```
customer.json
customer_en.json
invoice.json
invoice_en.json
```

```
{
  "CUSTOMER": "Customer",
  "FIRSTNAME": "Firstname",
  "LASTNAME": "Lastname"
}
```

**XML**

```
customer.xml
customer_en.xml
invoice.xml
invoice_en.xml
```

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
  <comment>Generated file customer.xml</comment>
  <entry key="LASTNAME">Lastname</entry>
  <entry key="CUSTOMER">Customer</entry>
  <entry key="FIRSTNAME">Firstname</entry>
</properties>
```

##  Usage Configuration

Refer to [documentation][i18n-config] for all configuration options.

[i18n-config]: http://basmussen.github.io/i18n-maven-plugin/gettings-started.html


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
