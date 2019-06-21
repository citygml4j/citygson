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

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.citygml4j.cityjson.metadata.ThematicModelType;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FeatureDataTypeAdapter extends TypeAdapter<Map<ThematicModelType, AbstractFeatureDataType>> {
    private final Gson gson;

    public FeatureDataTypeAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void write(JsonWriter out, Map<ThematicModelType, AbstractFeatureDataType> value) throws IOException {
        if (value != null)
            Streams.write(gson.toJsonTree(value), out);
        else
            out.nullValue();
    }

    @Override
    public Map<ThematicModelType, AbstractFeatureDataType> read(JsonReader in) throws IOException {
        Map<ThematicModelType, AbstractFeatureDataType> featureMetadata = null;

        if (in.peek() != JsonToken.NULL) {
            featureMetadata = new LinkedHashMap<>();
            in.beginObject();

            while (in.hasNext()) {
                ThematicModelType type = ThematicModelType.fromValue(in.nextName());
                if (type != null) {
                    AbstractFeatureDataType value = gson.fromJson(in, type.getMetadataClass());
                    if (value != null)
                        featureMetadata.put(type, value);
                }
            }

            in.endObject();
        } else
            in.nextNull();

        return featureMetadata;
    }
}
