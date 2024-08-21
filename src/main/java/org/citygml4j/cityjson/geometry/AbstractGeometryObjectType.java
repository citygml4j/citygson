/*
 * citygson - A Gson based library for parsing and serializing CityJSON
 * https://github.com/citygml4j/citygson
 *
 * citygson is part of the citygml4j project
 *
 * Copyright 2018-2024 Claus Nagel <claus.nagel@gmail.com>
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
package org.citygml4j.cityjson.geometry;

public abstract class AbstractGeometryObjectType extends AbstractGeometryType {
    private Number lod = 0;

    public boolean isSetLod() {
        return lod != null;
    }

    public Number getLod() {
        return lod;
    }

    public void setLod(Number lod) {
        if (lod.doubleValue() >= 0 && lod.doubleValue() < 4)
            this.lod = lod;
    }

}
