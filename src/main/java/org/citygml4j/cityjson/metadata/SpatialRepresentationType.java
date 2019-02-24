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
package org.citygml4j.cityjson.metadata;

import com.google.gson.annotations.SerializedName;

public enum SpatialRepresentationType {
	@SerializedName("vector")
	VECTOR("vector"),
	@SerializedName("grid")
	GRID("grid"),
	@SerializedName("TIN")
	TIN("TIN"),
	@SerializedName("textTable")
	TEXT_TABLE("textTable"),
	@SerializedName("stereoModel")
	STEREO_MODEL("stereoModel"),
	@SerializedName("video")
	VIDEO("video");

	private final String value;

	private SpatialRepresentationType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static SpatialRepresentationType fromValue(String value) {
		for (SpatialRepresentationType type : SpatialRepresentationType.values()) {
			if (type.value.equals(value))
				return type;
		}
		
		return null;
	}
	
}
