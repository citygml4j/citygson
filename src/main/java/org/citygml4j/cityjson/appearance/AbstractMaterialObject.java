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
package org.citygml4j.cityjson.appearance;

import java.util.List;

public abstract class AbstractMaterialObject {
	protected transient String theme;
	private Integer value;

	public abstract boolean isSetValues();
	public abstract void addNullValue();
	public abstract int getNumValues();
	public abstract List<Integer> flatValues();
	public abstract void unsetValues();

	AbstractMaterialObject() {
	}

	public AbstractMaterialObject(String theme) {
		this.theme = theme != null ? theme : "";
	}

	public String getTheme() {
		return theme;
	}

	public boolean isSetValue() {
		return value != null;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void unsetValue() {
		value = null;
	}

	public boolean collapseValues() {
		List<Integer> values = flatValues();
		if (values == null || values.isEmpty())
			return false;

		Integer compareTo = values.get(0);
		for (int i = 1 ; i < values.size(); i++) {
			if (values.get(i) != compareTo)
				return false;
		}

		unsetValues();
		if (compareTo != null)
			setValue(compareTo);

		return true;
	}

}
