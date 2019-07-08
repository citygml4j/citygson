Change Log
==========

### 1.1.0 - 2019-07-08

##### Breaking changes
* Renamed `getParent()` method of `AbstractCityObjectType` into `getParents()`.

##### Fixes
* Reworked type adapters to reduce memory footprint when reading CityJSON files.

### 1.0.0 - 2019-04-29

* Support for CityJSON 1.0.
* In CityJSON 1.0, the `"extensions"` property has been changed to additionally document the version number of the CityJSON Extension used in the dataset (see [change log](https://github.com/tudelft3d/cityjson/blob/master/changelog.md#100---2019-04-26)). This required a minor but breaking change in the `CityJSON` class.    

### 0.9.0 - 2019-04-18

This is the initial release of **citygson**.

* citygson has been developed as part of the [citygml4j](https://github.com/citygml4j/citygml4j) library for parsing and writing CityJSON files based on the [Gson](https://github.com/google/gson) binding framework. It has now been moved to its own GitHub place and hopefully is useful for developers who only need an object-based access to CityJSON datasets without the additional functionality offered by citygml4j.
* This version of citygson support CityJSON 0.9.