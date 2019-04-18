# citygson
citygson is a [Gson](https://github.com/google/gson) based library for parsing and serializing [CityJSON](http://www.cityjson.org/) files. citygson provides a lightweight and easy-to-use object-based interface to CityJSON data.

citygson defines Java objects for all JSON elements in CityJSON version 0.9.0. Moreover, it offers a simple mechanism to register additional user-defined Java objects for mapping CityJSON Extensions. 

License
-------
citygson is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0). See the `LICENSE` file for more details.

Latest release
--------------
The latest stable release of citygml4j is 0.9.0.

Download the citygson 0.9.0 release binaries [here](https://github.com/citygml4j/citygson/releases/download/v0.9.0/citygson-0.9.0.zip). Previous releases are available from the [releases section](https://github.com/citygml4j/citygson/releases).

Contributing
------------
* To file bugs found in the software create a GitHub issue.
* To contribute code for fixing filed issues create a pull request with the issue id.
* To propose a new feature create a GitHub issue and open a discussion.

Building
--------
citygson requires Java 8 or higher. The project uses [Gradle](https://gradle.org/) as build system. To build the library from source, run the following command from the root of the repository. 

    > gradlew installDist

This will create a folder `build/install/citygson`. Simply put the `citygson-<version>.jar` library file and its mandatory dependencies from the `lib` folder on your classpath to start developing with citygson. Have fun :-)

Maven artifact
--------------
citygson is also available as [Maven](http://maven.apache.org/) artifact from the [Maven Central Repository](https://search.maven.org/search?q=a:citygson) and from [JCenter](https://bintray.com/bintray/jcenter). To add citygson  to your project with Maven, add the following code to your `pom.xml`. You may need to adapt the citygson version number.

```xml
<dependency>
  <groupId>org.citygml4j</groupId>
  <artifactId>citygson</artifactId>
  <version>0.9.0</version>
</dependency>
```

Here is how you use citygson with your Gradle project:

```gradle
repositories {
  mavenCentral()
}

dependencies {
  compile 'org.citygml4j:citygson:0.9.0'
}
```

More information
----------------
CityJSON is a data format for encoding a subset of the [OGC CityGML](http://www.opengeospatial.org/standards/citygml) data model using JSON instead of GML. The [CityJSON specification](https://github.com/tudelft3d/cityjson) is developed and maintained on GitHub by the [3D geoinformation group at TU Delft](https://3d.bk.tudelft.nl/). 
