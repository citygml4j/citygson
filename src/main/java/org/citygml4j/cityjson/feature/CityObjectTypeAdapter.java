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

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.citygml4j.cityjson.CityJSONRegistry;
import org.citygml4j.cityjson.util.PropertyHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CityObjectTypeAdapter extends TypeAdapter<AbstractCityObjectType> {
	public static final String UNKNOWN_EXTENSION = "org.citygml4j.unknownExtension";

	private final Gson gson;
	private final TypeAdapterFactory factory;
	private final CityObjectTypeFilter typeFilter;
	private final boolean processUnknownExtensions;

	private final CityJSONRegistry registry = CityJSONRegistry.getInstance();
	private final Map<String, List<String>> predefinedProperties = new HashMap<>();
	private final PropertyHelper propertyHelper = new PropertyHelper();

	public CityObjectTypeAdapter(Gson gson, CityObjectTypeFilter typeFilter, boolean processUnknownExtensions, TypeAdapterFactory factory) {
		this.gson = gson;
		this.typeFilter = typeFilter;
		this.processUnknownExtensions = processUnknownExtensions;
		this.factory = factory;
	}

	public CityObjectTypeAdapter(Gson gson, TypeAdapterFactory factory) {
		this(gson, null, false, factory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(JsonWriter out, AbstractCityObjectType value) throws IOException {
		if (value != null) {
			if (value.type == null)
				value.type = CityJSONRegistry.getInstance().getCityObjectType(value);

			if (typeFilter != null && !typeFilter.accept(value.type))
				return;

			TypeAdapter<AbstractCityObjectType> delegate = (TypeAdapter<AbstractCityObjectType>) gson.getDelegateAdapter(factory, TypeToken.get(value.getClass()));
			JsonElement element = delegate.toJsonTree(value);

			if (element != null && element.isJsonObject()) {
				JsonObject result = element.getAsJsonObject();

				// serialize extension properties
				if (value.isSetExtensionProperties()) {
					JsonObject properties = gson.toJsonTree(value.getExtensionProperties()).getAsJsonObject();
					for (Map.Entry<String, JsonElement> entry : properties.entrySet())
						result.add(entry.getKey(), entry.getValue());
				}

				if (value.attributes != null) {
					JsonObject object = result.getAsJsonObject("attributes");

					// serialize extension attributes
					if (value.attributes.isSetExtensionAttributes()) {
						JsonObject attributes = gson.toJsonTree(value.attributes.getExtensionAttributes()).getAsJsonObject();
						for (Map.Entry<String, JsonElement> entry : attributes.entrySet())
							object.add(entry.getKey(), entry.getValue());
					}

					// remove empty attributes
					if (object.entrySet().isEmpty() || object.entrySet().stream().allMatch(e -> e.getValue().isJsonNull()))
						result.getAsJsonObject().remove("attributes");
				}
			}

			Streams.write(element, out);
		} else
			out.nullValue();
	}

	@Override
	public AbstractCityObjectType read(JsonReader in) throws IOException {
		if (in.peek() != JsonToken.NULL) {
			JsonObject object = Streams.parse(in).getAsJsonObject();
			JsonElement type = object.get("type");

			if (type != null) {
				Class<? extends AbstractCityObjectType> typeOf = registry.getCityObjectClass(type.getAsString());

				// map unknown extensions to generic city objects
				boolean unknownExtension = false;
				if (typeOf == null && processUnknownExtensions) {
					typeOf = GenericCityObjectType.class;
					unknownExtension = true;
				}

				if (typeOf != null && (typeFilter == null || typeFilter.accept(type.getAsString()))) {
					AbstractCityObjectType cityObject = gson.getDelegateAdapter(factory, TypeToken.get(typeOf)).fromJsonTree(object);
					cityObject.type = type.getAsString();

					// deserialize extension properties
					Map<String, Object> extensionProperties = deserialize(object.entrySet(), typeOf, cityObject);
					if (!extensionProperties.isEmpty())
						cityObject.setExtensionProperties(extensionProperties);

					if (cityObject.attributes != null) {
						JsonObject attributes = object.get("attributes").getAsJsonObject();

						Class<? extends Attributes> attributesClass = cityObject.getAttributesClass();
						if (attributesClass != Attributes.class)
							cityObject.attributes = gson.fromJson(attributes, attributesClass);

						// deserialize extension attributes
						Map<String, Object> extensionAttributes = deserialize(attributes.entrySet(), attributesClass, cityObject);
						if (!extensionAttributes.isEmpty())
							cityObject.attributes.setExtensionAttributes(extensionAttributes);
					}

					if (unknownExtension)
						cityObject.setLocalProperty(UNKNOWN_EXTENSION, true);

					return cityObject;
				}
			}
		}

		return null;
	}

	private Map<String, Object> deserialize(Set<Map.Entry<String, JsonElement>> entrySet, Class<?> typeClass, AbstractCityObjectType cityObject) {
		Map<String, Object> properties = new HashMap<>();
		List<String> predefined = predefinedProperties.computeIfAbsent(
				typeClass.getTypeName(),
				v -> propertyHelper.getDeclaredProperties(typeClass));

		for (Map.Entry<String, JsonElement> entry : entrySet) {
			// skip properties defined by the type class
			String key = entry.getKey();
			if (predefined.contains(key))
				continue;

			// check whether we found a registered extension property
			Type extensionAttributeType = registry.getExtensionPropertyClass(entry.getKey(), cityObject);
			Object value = extensionAttributeType != null ?
					gson.fromJson(entry.getValue(), extensionAttributeType) :
					propertyHelper.deserialize(entry.getValue());

			if (value != null)
				properties.put(key, value);
		}

		return properties;
	}
}
