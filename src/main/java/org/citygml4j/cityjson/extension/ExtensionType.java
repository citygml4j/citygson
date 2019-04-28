/*
 * citygson - A Gson based library for parsing and serializing CityJSON
 * https://github.com/citygml4j/citygson
 *
 * citygson is part of the citygml4j project
 *
 * Copyright 2018-2019 Claus Nagel <claus.nagel@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.citygml4j.cityjson.extension;

public class ExtensionType {
    private String url;
    private String version;

    public boolean isSetUrl() {
        return url != null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSetVersion() {
        return version != null;
    }

    public boolean isValidVersion(String version) {
        return version != null && version.matches("^\\d\\.\\d$");
    }

    public boolean isValidVersion(int major, int minor) {
        return major >= 0 && major < 10 && minor >= 0 && minor < 10;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (isValidVersion(version))
            this.version = version;
    }

    public void setVersion(int major, int minor) {
        if (isValidVersion(major, minor))
            this.version = major + "." + minor;
    }
}
