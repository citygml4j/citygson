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

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerticesListAdapter extends TypeAdapter<VerticesList> {
	private boolean asInteger;
	
	public VerticesListAdapter serializeAsInteger(boolean asInteger) {
		this.asInteger = asInteger;
		return this;
	}

	@Override
	public void write(JsonWriter out, VerticesList value) throws IOException {
		out.beginArray();

		for (List<Double> vertex : value.getVertices()) {
			if (vertex != null) {
				out.beginArray();
				for (double coordinate : vertex) {
					if (asInteger)
						out.value((int) coordinate);
					else
						out.value(coordinate);
				}

				out.endArray();
			} else
				out.nullValue();
		}

		out.endArray();
	}

	@Override
	public VerticesList read(JsonReader in) throws IOException {
		VerticesList vertices = new VerticesList();
		in.beginArray();

		while (in.hasNext()) {
			if (in.peek() == JsonToken.NULL) {
				vertices.addVertex(null);
				in.nextNull();
				continue;
			}

			List<Double> vertex = new ArrayList<>();
			in.beginArray();
			if (in.peek() == JsonToken.NUMBER) {
				vertex.add(in.nextDouble());
				vertex.add(in.nextDouble());
				vertex.add(in.nextDouble());
			}

			vertices.addVertex(vertex);
			in.endArray();
		}

		in.endArray();
		return vertices;
	}
}
