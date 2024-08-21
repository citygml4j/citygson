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
package org.citygml4j.cityjson.feature;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CityObjectsAdapter extends TypeAdapter<Map<String, AbstractCityObjectType>> {
	private final Gson gson;

	public CityObjectsAdapter(Gson gson) {
		this.gson = gson;
	}

	@Override
	public void write(JsonWriter out, Map<String, AbstractCityObjectType> value) throws IOException {
		if (value != null) {
			out.beginObject();

			for (Map.Entry<String, AbstractCityObjectType> entry : value.entrySet()) {
				out.name(entry.getKey());
				Streams.write(gson.toJsonTree(entry.getValue(), AbstractCityObjectType.class), out);
			}

			out.endObject();
		} else
			out.nullValue();
	}

	@Override
	public Map<String, AbstractCityObjectType> read(JsonReader in) throws IOException {
		Map<String, AbstractCityObjectType> cityObjects = null;

		if (in.peek() != JsonToken.NULL) {
			cityObjects = new HashMap<>();
			in.beginObject();

			while (in.hasNext()) {
				String gmlId = in.nextName();

				if (in.peek() == JsonToken.NULL) {
					in.nextNull();
					continue;
				}

				AbstractCityObjectType cityObject = gson.fromJson(in, AbstractCityObjectType.class);
				if (cityObject != null) {
					cityObject.setGmlId(gmlId);
					cityObjects.put(gmlId, cityObject);
				}
			}

			in.endObject();
		}

		return cityObjects;
	}
}
