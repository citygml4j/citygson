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

package org.citygml4j.cityjson.feature;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;

public class DateTimeAdapter extends TypeAdapter<ZonedDateTime> {
    // formatter to parse both "full-date" and "date-time" according to RFC 3339, Section 5.6
    private DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .optionalStart()
            .appendLiteral('T')
            .append(DateTimeFormatter.ISO_LOCAL_TIME)
            .optionalStart()
            .appendOffsetId()
            .optionalEnd()
            .optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT)
            .withChronology(IsoChronology.INSTANCE);

    @Override
    public void write(JsonWriter out, ZonedDateTime value) throws IOException {
        if (value != null) {
            // return "date-time" only if the time is not set to start of day
            DateTimeFormatter formatter = value.toLocalTime().equals(LocalTime.MIN) ?
                    DateTimeFormatter.ISO_LOCAL_DATE : DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            out.value(value.format(formatter));
        } else
            out.nullValue();
    }

    @Override
    public ZonedDateTime read(JsonReader in) throws IOException {
        if (in.peek() != JsonToken.NULL) {
            try {
                return ZonedDateTime.parse(in.nextString(), formatter);
            } catch (DateTimeParseException e) {
                //
            }
        }

        return null;
    }
}
