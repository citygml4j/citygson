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

package org.citygml4j.cityjson.metadata;

import com.google.gson.annotations.JsonAdapter;
import org.citygml4j.cityjson.feature.DateTimeAdapter;

import java.time.ZonedDateTime;

public class TemporalExtentType {
    @JsonAdapter(DateTimeAdapter.class)
    public ZonedDateTime startDate;
    @JsonAdapter(DateTimeAdapter.class)
    public ZonedDateTime endDate;

    public boolean isSetStartDate() {
        return startDate != null;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public void unsetStartDate() {
        startDate = null;
    }

    public boolean isSetEndDate() {
        return endDate != null;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public void unsetEndDate() {
        endDate = null;
    }
}
