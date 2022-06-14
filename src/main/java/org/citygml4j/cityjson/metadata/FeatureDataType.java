/*
 * citygson - A Gson based library for parsing and serializing CityJSON
 * https://github.com/citygml4j/citygson
 *
 * citygson is part of the citygml4j project
 *
 * Copyright 2018-2022 Claus Nagel <claus.nagel@gmail.com>
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

package org.citygml4j.cityjson.metadata;

import java.util.HashMap;
import java.util.Map;

public class FeatureDataType {
    private Integer uniqueFeatureCount;
    private Integer aggregateFeatureCount;
    private Map<String, Integer> presentLoDs;

    public boolean isSetUniqueFeatureCount() {
        return uniqueFeatureCount != null;
    }

    public Integer getUniqueFeatureCount() {
        return uniqueFeatureCount;
    }

    public void setUniqueFeatureCount(Integer uniqueFeatureCount) {
        this.uniqueFeatureCount = uniqueFeatureCount;
    }

    public void unsetUniqueFeatureCount() {
        uniqueFeatureCount = null;
    }

    public boolean isSetAggregateFeatureCount() {
        return aggregateFeatureCount != null;
    }

    public Integer getAggregateFeatureCount() {
        return aggregateFeatureCount;
    }

    public void setAggregateFeatureCount(Integer aggregateFeatureCount) {
        this.aggregateFeatureCount = aggregateFeatureCount;
    }

    public void unsetAggregateFeatureCount() {
        aggregateFeatureCount = null;
    }

    public boolean isSetPresentLoDs() {
        return presentLoDs != null;
    }

    public void addPresentLoD(String lod) {
        if (lod != null && lod.matches("^[0-9](\\.[0-9])?$")) {
            if (presentLoDs == null) {
                presentLoDs = new HashMap<>();
            }

            presentLoDs.merge(lod, 1, Integer::sum);
        }
    }

    public void addPresentLoD(Number lod) {
        addPresentLoD(String.valueOf(lod));
    }

    public Map<String, Integer> getPresentLoDs() {
        return presentLoDs;
    }

    public void setPresentLoDs(Map<String, Integer> presentLoDs) {
        this.presentLoDs = presentLoDs;
    }

    public void unsetPresentLoDs() {
        presentLoDs = null;
    }
}
