# citygson
citygson is a [Gson](https://github.com/google/gson) based library for parsing and serializing
[CityJSON](http://www.cityjson.org/) files. citygson provides a lightweight and easy-to-use object-based
interface to CityJSON data by defining Java objects for all JSON elements in CityJSON. Moreover, it offers
a simple mechanism to register additional user-defined Java objects for mapping CityJSON Extensions.

:information_source: citygson is in **maintenance mode** with no significant active development planned. Existing
bugs will be fixed, but large new features will likely not be added. citygson was mainly developed for citygml4j v2.
The latest [citygml4j v3](https://github.com/citygml4j/citygml4j) brings its own serialization module for the
CityJSON encoding and, thus, is not dependent on citygson anymore.

License
-------
citygson is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
See the `LICENSE` file for more details.

Latest release
--------------
The latest stable release of citygson is 1.2.0.

Download the citygson 1.2.0 release binaries [here](https://github.com/citygml4j/citygson/releases/download/v1.2.0/citygson-1.2.0.zip).
Previous releases are available from the [releases section](https://github.com/citygml4j/citygson/releases).

Contributing
------------
* To file bugs found in the software create a GitHub issue.
* To contribute code for fixing filed issues create a pull request with the issue id.
* To propose a new feature create a GitHub issue and open a discussion.

Supported CityJSON versions
---------------------------
citygson only supports CityJSON 1.0. There are no plans to add support for more recent versions of CityJSON.

Building
--------
citygson requires Java 11 or higher. The project uses [Gradle](https://gradle.org/) as build system.
To build the library from source, run the following command from the root of the repository.

    > gradlew installDist

This will create a folder `build/install/citygson`. Simply put the `citygson-<version>.jar` library file and its
mandatory dependencies from the `lib` folder on your classpath to start developing with citygson. Have fun :-)

Maven artifact
--------------
citygson is also available as [Maven](http://maven.apache.org/) artifact from the
[Maven Central Repository](https://search.maven.org/search?q=a:citygson).
To add citygson  to your project with Maven, add the following code to your `pom.xml`.
You may need to adapt the citygson version number.

```xml
<dependency>
  <groupId>org.citygml4j</groupId>
  <artifactId>citygson</artifactId>
  <version>1.2.0</version>
</dependency>
```

Here is how you use citygson with your Gradle project:

```gradle
repositories {
  mavenCentral()
}

dependencies {
  compile 'org.citygml4j:citygson:1.2.0'
}
```

More information
----------------
CityJSON is a data format for encoding a subset of the [OGC CityGML](http://www.opengeospatial.org/standards/citygml)
data model using JSON instead of GML. The [CityJSON specification](https://github.com/cityjson/specs) is developed
and maintained on GitHub by the [3D geoinformation group at TU Delft](https://3d.bk.tudelft.nl/).

citygson has been developed in the context of the [CityGML](http://www.opengeospatial.org/standards/citygml)
Java API [citygml4j](https://github.com/citygml4j/citygml4j) and is used as CityJSON parser in this project.
citygml4j adds another data abstraction layer and object model that can be populated from both CityJSON data
and GML-encoded CityGML data. This way, citygml4j users don't have to choose between the encodings but can
write code that supports both of them.