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

package org.citygml4j.cityjson.feature;

import org.citygml4j.cityjson.geometry.AbstractGeometryType;
import org.citygml4j.cityjson.geometry.GeometryTypeName;

import java.util.ArrayList;
import java.util.List;

public class CityObjectGroupType extends AbstractCityObjectType {
    private List<String> members = new ArrayList<>();

    public CityObjectGroupType() {
    }

    public CityObjectGroupType(String gmlId) {
        super(gmlId);
    }

    @Override
    public void addGeometry(AbstractGeometryType geometry) {
        // only a single geometry representation is allowed
        if (geometry != null && isValidGeometryType(geometry.getType())) {
            unsetGeometry();
            super.addGeometry(geometry);
        }
    }

    @Override
    public void setGeometry(List<AbstractGeometryType> geometry) {
        // only a single geometry representation is allowed
        if (geometry != null)
            geometry.stream().filter(g -> isValidGeometryType(g.getType())).findFirst().ifPresent(this::addGeometry);
    }

    @Override
    public Attributes newAttributes() {
        return super.newAttributes(new Attributes());
    }

    public boolean isSetMembers() {
        return !members.isEmpty();
    }

    public void addMember(String member) {
        members.add(member);
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        if (members != null)
            this.members = members;
    }

    public void unsetMembers() {
        members.clear();
    }

    @Override
    public boolean isValidGeometryType(GeometryTypeName type) {
        return type != GeometryTypeName.GEOMETRY_INSTANCE;
    }

}
