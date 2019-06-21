/*
 * citygml4j - The Open Source Java API for CityGML
 * https://github.com/citygml4j
 *
 * Copyright 2013-2019 Claus Nagel <claus.nagel@gmail.com>
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

package org.citygml4j.cityjson.appearance;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextureAdapter<T extends AbstractTextureObject> extends TypeAdapter<Map<String, T>> {
    private final Gson gson;
    private final Class<T> typeOfT;
    private final TypeAdapterFactory factory;

    public TextureAdapter(Gson gson, Class<T> typeOfT, TypeAdapterFactory factory) {
        this.gson = gson;
        this.typeOfT = typeOfT;
        this.factory = factory;
    }

    @Override
    public void write(JsonWriter out, Map<String, T> value) throws IOException {
        if (value != null) {
            out.beginObject();

            for (Map.Entry<String, T> entry : value.entrySet()) {
                out.name(entry.getKey());
                Streams.write(gson.getDelegateAdapter(factory, TypeToken.get(typeOfT)).toJsonTree(entry.getValue()), out);
            }

            out.endObject();
        } else
            out.nullValue();
    }

    @Override
    public Map<String, T> read(JsonReader in) throws IOException {
        Map<String, T> materials = new HashMap<>();
        in.beginObject();

        while (in.hasNext()) {
            String theme = in.nextName();
            T material = gson.getDelegateAdapter(factory, TypeToken.get(typeOfT)).fromJsonTree(Streams.parse(in));
            material.theme = theme;
            materials.put(theme, material);
        }

        in.endObject();
        return materials;
    }
}
