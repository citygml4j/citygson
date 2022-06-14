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

import com.google.gson.annotations.JsonAdapter;
import org.citygml4j.cityjson.feature.DateAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemanticsType {
	String type;
	private Integer parent;
	private List<Integer> children;
	private String id;
	private String description;
	private String name;
	@JsonAdapter(DateAdapter.class)
	private LocalDate creationDate;
	@JsonAdapter(DateAdapter.class)
	private LocalDate terminationDate;
	private transient Map<String, Object> attributes;

	public SemanticsType() {
		type = null;
	}
	
	public SemanticsType(String type) {
		this.type = type;
	}
	
	public final String getType() {
		return type;
	}

	public boolean isSetParent() {
		return parent != null;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public void unsetParent() {
		parent = null;
	}

	public boolean isSetChildren() {
		return children != null && !children.isEmpty();
	}

	public void addChild(int child) {
		if (children == null)
			children = new ArrayList<>();

		children.add(child);
	}

	public List<Integer> getChildren() {
		return children;
	}

	public void setChildren(List<Integer> Children) {
		this.children = Children;
	}

	public void unsetChildren() {
		children = null;
	}

	public boolean isSetId() {
		return id != null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void unsetId() {
		id = null;
	}

	public boolean isSetDescription() {
		return description != null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void unsetDescription() {
		description = null;
	}

	public boolean isSetName() {
		return name != null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void unsetName() {
		name = null;
	}

	public boolean isSetCreationDate() {
		return creationDate != null;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate.toLocalDate();
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate.toLocalDate();
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public void unsetCreationDate() {
		creationDate = null;
	}

	public boolean isSetTerminationDate() {
		return terminationDate != null;
	}

	public LocalDate getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(ZonedDateTime terminationDate) {
		this.terminationDate = terminationDate.toLocalDate();
	}

	public void setTerminationDate(LocalDateTime terminationDate) {
		this.terminationDate = terminationDate.toLocalDate();
	}

	public void setTerminationDate(LocalDate terminationDate) {
		this.terminationDate = terminationDate;
	}

	public void unsetTerminationDate() {
		terminationDate = null;
	}

	public boolean isSetAttributes() {
		return attributes != null;
	}
	
	public void addAttribute(String name, Object value) {
		if (attributes == null)
			attributes = new HashMap<>();
		
		attributes.put(name, value);
	}
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Map<String, Object> properties) {
		if (type != null)
			this.attributes = properties;
	}
	
	public void removeAttribute(String name) {
		if (attributes != null)
			attributes.remove(name);
	}
	
	public void unsetAttributes() {
		attributes = null;
	}
}
