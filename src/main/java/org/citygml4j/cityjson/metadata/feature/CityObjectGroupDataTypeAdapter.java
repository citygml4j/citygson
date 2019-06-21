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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.citygml4j.cityjson.metadata.LoDType;
import org.citygml4j.cityjson.metadata.ThematicModelType;

import java.io.IOException;
import java.util.Map;

public class CityObjectGroupDataTypeAdapter extends TypeAdapter<CityObjectGroupDataType> {
    private final Gson gson;

    public CityObjectGroupDataTypeAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void write(JsonWriter out, CityObjectGroupDataType value) throws IOException {
        JsonObject object = new JsonObject();

        if (value.isSetUniqueFeatureCount())
            object.add("uniqueFeatureCount", new JsonPrimitive(value.getUniqueFeatureCount()));

        if (value.isSetAggregateFeatureCount())
            object.add("aggregateFeatureCount", new JsonPrimitive(value.getAggregateFeatureCount()));

        if (value.isSetPresentLoDs())
            object.add("presentLoDs", gson.toJsonTree(value.getPresentLoDs()));

        if (value.isSetMemberMetadata()) {
            for (Map.Entry<ThematicModelType, AbstractFeatureDataType> entry : value.memberMetadata.entrySet())
                object.add(entry.getKey().getValue(), gson.toJsonTree(entry.getValue()));
        }

        Streams.write(object, out);
    }

    @Override
    public CityObjectGroupDataType read(JsonReader in) throws IOException {
        CityObjectGroupDataType featureMetadata = new CityObjectGroupDataType();
        JsonObject object = Streams.parse(in).getAsJsonObject();

        Map<ThematicModelType, AbstractFeatureDataType> memberMetadata = new LinkedTreeMap<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            switch (entry.getKey()) {
                case "uniqueFeatureCount":
                    featureMetadata.setUniqueFeatureCount(entry.getValue().getAsInt());
                    break;
                case "aggregateFeatureCount":
                    featureMetadata.setAggregateFeatureCount(entry.getValue().getAsInt());
                    break;
                case "presentLoDs":
                    featureMetadata.setPresentLoDs(gson.fromJson(entry.getValue(), new TypeToken<Map<LoDType, Integer>>() {}.getType()));
                    break;
                default:
                    ThematicModelType type = ThematicModelType.fromValue(entry.getKey());
                    if (type != null) {
                        AbstractFeatureDataType value = gson.fromJson(entry.getValue(), type.getMetadataClass());
                        if (value != null)
                            memberMetadata.put(type, value);
                    }
            }
        }

        if (!memberMetadata.isEmpty())
            featureMetadata.memberMetadata = memberMetadata;

        return featureMetadata;
    }
}
