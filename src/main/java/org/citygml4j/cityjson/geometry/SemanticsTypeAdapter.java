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
package org.citygml4j.cityjson.geometry;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.citygml4j.cityjson.CityJSONRegistry;
import org.citygml4j.cityjson.util.PropertyHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemanticsTypeAdapter extends TypeAdapter<SemanticsType> {
	private final Gson gson;
	private final TypeAdapterFactory factory;

	private final CityJSONRegistry registry = CityJSONRegistry.getInstance();
	private final Map<String, List<String>> predefinedAttributes = new HashMap<>();
	private final PropertyHelper propertyHelper = new PropertyHelper();

	public SemanticsTypeAdapter(Gson gson, TypeAdapterFactory factory) {
		this.gson = gson;
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(JsonWriter out, SemanticsType value) throws IOException {
		if (value != null) {
			if (value.type == null)
				value.type = registry.getSemanticSurfaceType(value);

			Class<?> typeOf = registry.getSemanticSurfaceClass(value.type);
			TypeAdapter<SemanticsType> delegate = (TypeAdapter<SemanticsType>) gson.getDelegateAdapter(factory, TypeToken.get(typeOf));

			JsonElement element = delegate.toJsonTree(value);
			if (element != null && element.isJsonObject()) {
				JsonObject object = element.getAsJsonObject();

				// serialize extension properties
				if (value.isSetAttributes()) {
					JsonObject properties = gson.toJsonTree(value.getAttributes()).getAsJsonObject();
					for (Map.Entry<String, JsonElement> entry : properties.entrySet())
						object.add(entry.getKey(), entry.getValue());
				}
			}

			Streams.write(element, out);
		} else
			out.nullValue();
	}

	@Override
	public SemanticsType read(JsonReader in) throws IOException {
		if (in.peek() != JsonToken.NULL) {
			JsonObject object = Streams.parse(in).getAsJsonObject();
			JsonPrimitive type = object.getAsJsonPrimitive("type");

			if (type != null) {
				Class<? extends SemanticsType> typeOf = registry.getSemanticSurfaceClass(type.getAsString());
				if (typeOf != null) {
					SemanticsType semantics = gson.getDelegateAdapter(factory, TypeToken.get(typeOf)).fromJsonTree(object);

					// deserialize extension properties
					List<String> predefined = predefinedAttributes.computeIfAbsent(semantics.getClass().getTypeName(),
							v -> propertyHelper.getDeclaredProperties(semantics.getClass()));

					for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
						String key = entry.getKey();
						if (predefined.contains(key))
							continue;

						Object value = propertyHelper.deserialize(entry.getValue());
						if (value != null)
							semantics.addAttribute(key, value);
					}

					return semantics;
				}
			}
		}

		return null;
	}
}
