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
package org.citygml4j.cityjson.geometry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GeometryTypeAdapter extends TypeAdapter<AbstractGeometryType> {
	private final Gson gson;
	private final TypeAdapterFactory factory;

	public GeometryTypeAdapter(Gson gson, TypeAdapterFactory factory) {
		this.gson = gson;
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(JsonWriter out, AbstractGeometryType value) throws IOException {
		if (value != null) {
			TypeAdapter<AbstractGeometryType> delegate = (TypeAdapter<AbstractGeometryType>) gson.getDelegateAdapter(factory, TypeToken.get(value.getClass()));
			Streams.write(delegate.toJsonTree(value), out);
		} else
			out.nullValue();
	}

	@Override
	public AbstractGeometryType read(JsonReader in) throws IOException {
		if (in.peek() != JsonToken.NULL) {
			JsonObject object = Streams.parse(in).getAsJsonObject();
			JsonElement type = object.get("type");

			if (type != null) {
				GeometryTypeName name = GeometryTypeName.fromValue(type.getAsString());
				if (name != null)
					return gson.getDelegateAdapter(factory, TypeToken.get(name.getTypeClass())).fromJsonTree(object);
			}
		}

		return null;
	}
}
