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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiLineStringType extends AbstractGeometryObjectType {
	private final GeometryTypeName type = GeometryTypeName.MULTI_LINE_STRING;
	private List<List<Integer>> boundaries = new ArrayList<>();
	
	@Override
	public GeometryTypeName getType() {
		return type;
	}
	
	public void addLineString(List<Integer> lineString) {
		if (lineString != null && lineString.size() > 0)
			boundaries.add(lineString);
	}

	public List<List<Integer>> getLineStrings() {
		return boundaries;
	}

	public void setLineStrings(List<List<Integer>> lineStrings) {
		if (lineStrings != null)
			boundaries = lineStrings;
	}
	
	public void unsetLineStrings() {
		boundaries.clear();
	}

	@Override
	public void updateIndexes(Map<Integer, Integer> indexMap) {
		for (List<Integer> lineString : boundaries) {
			for (int index = 0; index < lineString.size(); index++) {
				Integer update = indexMap.get(lineString.get(index));
				if (update != null)
					lineString.set(index, update);
			}
		}
	}
}
