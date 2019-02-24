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

package org.citygml4j.cityjson.metadata.feature;

import com.google.gson.annotations.SerializedName;
import org.citygml4j.cityjson.metadata.ThematicModelType;

public class BuildingDataType extends AbstractFeatureDataType {
    @SerializedName("BuildingParts")
    private Integer buildingParts;
    @SerializedName("BuildingInstallations")
    private Integer buildingInstallations;

    public boolean isSetBuildingParts() {
        return buildingParts != null;
    }

    public Integer getBuildingParts() {
        return buildingParts;
    }

    public void setBuildingParts(Integer buildingParts) {
        this.buildingParts = buildingParts;
    }

    public void unsetBuildingParts() {
        buildingParts = null;
    }

    public boolean isSetBuildingInstallations() {
        return buildingInstallations != null;
    }

    public Integer getBuildingInstallations() {
        return buildingInstallations;
    }

    public void setBuildingInstallations(Integer buildingInstallations) {
        this.buildingInstallations = buildingInstallations;
    }

    public void unsetBuildingInstallations() {
        buildingInstallations = null;
    }

    @Override
    public ThematicModelType getType() {
        return ThematicModelType.BUILDING;
    }
}
