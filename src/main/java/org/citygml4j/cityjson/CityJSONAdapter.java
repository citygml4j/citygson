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

package org.citygml4j.cityjson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.citygml4j.cityjson.appearance.AppearanceType;
import org.citygml4j.cityjson.extension.ExtensionType;
import org.citygml4j.cityjson.feature.AbstractCityObjectType;
import org.citygml4j.cityjson.geometry.GeometryTemplatesType;
import org.citygml4j.cityjson.geometry.TransformType;
import org.citygml4j.cityjson.geometry.VerticesList;
import org.citygml4j.cityjson.metadata.MetadataType;
import org.citygml4j.cityjson.util.PropertyHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class CityJSONAdapter extends TypeAdapter<CityJSON> {
    private final Gson gson;
    private final CityJSONRegistry registry = CityJSONRegistry.getInstance();
    private final PropertyHelper propertyHelper = new PropertyHelper();

    private List<String> predefinedProperties;

    public CityJSONAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void write(JsonWriter out, CityJSON value) throws IOException {
        if (value != null) {
            out.beginObject();

            out.name("type");
            out.value(value.getType());

            out.name("version");
            out.value(value.getVersion());

            if (value.metadata != null) {
                out.name("metadata");
                Streams.write(gson.toJsonTree(value.metadata, MetadataType.class), out);
            }

            if (value.extensions != null) {
                out.name("extensions");
                Streams.write(gson.toJsonTree(value.extensions, new TypeToken<Map<String, ExtensionType>>() {}.getType()), out);
            }

            // serialize extension properties
            if (value.isSetExtensionProperties()) {
                JsonObject properties = gson.toJsonTree(value.getExtensionProperties()).getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : properties.entrySet()) {
                    out.name(entry.getKey());
                    Streams.write(entry.getValue(), out);
                }
            }

            if (value.cityObjects != null) {
                out.name("CityObjects");
                Streams.write(gson.toJsonTree(value.cityObjects, new TypeToken<Map<String, AbstractCityObjectType>>() {}.getType()), out);
            }

            if (value.vertices != null) {
                out.name("vertices");
                Streams.write(gson.toJsonTree(value.vertices, VerticesList.class), out);
            }

            if (value.transform != null) {
                out.name("transform");
                Streams.write(gson.toJsonTree(value.transform, TransformType.class), out);
            }

            if (value.appearance != null) {
                out.name("appearance");
                Streams.write(gson.toJsonTree(value.appearance, AppearanceType.class), out);
            }

            if (value.geometryTemplates != null) {
                out.name("geometry-templates");
                Streams.write(gson.toJsonTree(value.geometryTemplates, GeometryTemplatesType.class), out);
            }

            out.endObject();
        } else
            out.nullValue();
    }

    @Override
    public CityJSON read(JsonReader in) throws IOException {
        CityJSON cityJSON = null;

        if (predefinedProperties == null)
            predefinedProperties = propertyHelper.getDeclaredProperties(CityJSON.class);

        if (in.peek() != JsonToken.NULL) {
            cityJSON = new CityJSON();
            in.beginObject();

            while (in.hasNext()) {
                String key = in.nextName();
                switch (key) {
                    case "metadata":
                        cityJSON.metadata = gson.fromJson(in, MetadataType.class);
                        break;
                    case "extensions":
                        cityJSON.extensions = gson.fromJson(in, new TypeToken<Map<String, ExtensionType>>() {}.getType());
                        break;
                    case "CityObjects":
                        cityJSON.cityObjects = gson.fromJson(in, new TypeToken<Map<String, AbstractCityObjectType>>() {}.getType());
                        break;
                    case "vertices":
                        cityJSON.vertices = gson.fromJson(in, VerticesList.class);
                        break;
                    case "transform":
                        cityJSON.transform = gson.fromJson(in, TransformType.class);
                        break;
                    case "appearance":
                        cityJSON.appearance = gson.fromJson(in, AppearanceType.class);
                        break;
                    case "geometry-templates":
                        cityJSON.geometryTemplates = gson.fromJson(in, GeometryTemplatesType.class);
                        break;
                    default:
                        // deserialize extension properties
                        if (!predefinedProperties.contains(key)) {
                            Type extensionAttributeType = registry.getExtensionPropertyClass(key, cityJSON);
                            Object value = extensionAttributeType != null ?
                                    gson.fromJson(in, extensionAttributeType) :
                                    propertyHelper.deserialize(Streams.parse(in));

                            if (value != null)
                                cityJSON.addExtensionProperty(key, value);
                        } else
                            in.skipValue();
                }
            }

            in.endObject();
        }

        return cityJSON;
    }


}
