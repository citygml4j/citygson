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

import org.citygml4j.cityjson.extension.ExtensibleType;
import org.citygml4j.cityjson.extension.Extension;
import org.citygml4j.cityjson.geometry.AbstractGeometryType;
import org.citygml4j.cityjson.geometry.GeometryTypeName;

import java.util.*;

public abstract class AbstractCityObjectType implements ExtensibleType, Extension {
	String type;
	Attributes attributes;
	private List<Double> geographicalExtent;
	private Set<String> children;
	private Set<String> parents;
	private List<AbstractGeometryType> geometry = new ArrayList<>();

	private transient String gmlId;
	private transient Map<String, Object> extensionProperties;
	private transient HashMap<String, Object> localProperties;

	public AbstractCityObjectType() {
	}

	public AbstractCityObjectType(String gmlId) {
		setGmlId(gmlId);
	}

	public abstract Attributes newAttributes();
	public abstract boolean isValidGeometryType(GeometryTypeName type);

	protected final <T extends Attributes> T newAttributes(T attributes) {
		this.attributes = attributes != null ? attributes : new Attributes();
		return attributes;
	}

	public Attributes getAttributes() {
		return attributes != null ? attributes : newAttributes();
	}

	public final boolean isSetAttributes() {
		return attributes != null;
	}

	public final void unsetAttributes() {
		attributes = null;
	}

	public boolean isSetGmlId() {
		return gmlId != null;
	}

	public String getGmlId() {
		return gmlId;
	}

	public void setGmlId(String gmlId) {
		this.gmlId = gmlId != null && !gmlId.isEmpty() ? gmlId : "UUID_" + UUID.randomUUID().toString();
	}

	public final String getType() {
		return type;
	}

	public boolean isSetGeographicalExtent() {
		return geographicalExtent != null && geographicalExtent.size() >= 6;
	}

	public List<Double> getGeographicalExtent() {
		return isSetGeographicalExtent() ? geographicalExtent.subList(0, 6) : null;
	}

	public void setGeographicalExtent(List<Double> geographicalExtent) {
		if (geographicalExtent == null)
			this.geographicalExtent = null;
		else if (geographicalExtent.size() >= 6)
			this.geographicalExtent = geographicalExtent.subList(0, 6);
	}

	public void unsetGeographicalExtent() {
		geographicalExtent = null;
	}

	public boolean isSetChildren() {
		return children != null && !children.isEmpty();
	}

	public void addChild(String child) {
		if (children == null)
			children = new HashSet<>();

		children.add(child);
	}

	@Override
	public void addChild(AbstractCityObjectType child) {
		addChild(child.gmlId);
		child.addParent(gmlId);
	}

	public Set<String> getChildren() {
		return children;
	}

	public void setChildren(Set<String> children) {
		this.children = children;
	}

	public void unsetChildren() {
		children = null;
	}

	public boolean isSetParents() {
		return parents != null && !parents.isEmpty();
	}

	public void addParent(String parent) {
		if (parents == null)
			parents = new HashSet<>();

		parents.add(parent);
	}

	public void addParent(AbstractCityObjectType parent) {
		addParent(parent.gmlId);
		parent.addChild(gmlId);
	}

	public Set<String> getParents() {
		return parents;
	}

	public void setParents(Set<String> parents) {
		this.parents = parents;
	}

	public void unsetParents() {
		parents = null;
	}

	public boolean isSetGeometry() {
		return !geometry.isEmpty();
	}
		
	public void addGeometry(AbstractGeometryType geometry) {
		if (geometry != null && isValidGeometryType(geometry.getType()))
			this.geometry.add(geometry);
	}

	public List<AbstractGeometryType> getGeometry() {
		return geometry;
	}

	public void setGeometry(List<AbstractGeometryType> geometry) {
		if (geometry != null) {
			this.geometry = new ArrayList<>(geometry);
			this.geometry.removeIf(g -> !isValidGeometryType(g.getType()));
		}
	}
	
	public void removeGeometry(AbstractGeometryType geometry) {
		this.geometry.remove(geometry);
	}
	
	public void unsetGeometry() {
		geometry.clear();
	}

	public boolean isSetExtensionProperties() {
		return extensionProperties != null;
	}

	public void addExtensionProperty(String name, Object value) {
		if (extensionProperties == null)
			extensionProperties = new HashMap<>();

		extensionProperties.put(name, value);
	}

	public Map<String, Object> getExtensionProperties() {
		return extensionProperties;
	}

	public void setExtensionProperties(Map<String, Object> extensionProperties) {
		this.extensionProperties = extensionProperties;
	}

	public void removeExtensionProperty(String name) {
		if (extensionProperties != null)
			extensionProperties.remove(name);
	}

	public void unsetExtensionProperties() {
		extensionProperties = null;
	}

	public Object getLocalProperty(String name) {
		if (localProperties != null)
			return localProperties.get(name);

		return null;
	}

	public void setLocalProperty(String name, Object value) {
		if (localProperties == null)
			localProperties = new HashMap<String, Object>();

		localProperties.put(name, value);
	}

	public boolean hasLocalProperty(String name) {
		return localProperties != null && localProperties.containsKey(name);
	}

	public Object unsetLocalProperty(String name) {
		if (localProperties != null)
			return localProperties.remove(name);

		return null;
	}

	Class<? extends Attributes> getAttributesClass() {
		Attributes tmp = attributes;
		newAttributes();
		Class<? extends Attributes> attributesClass = attributes != null ? attributes.getClass() : Attributes.class;
		attributes = tmp;

		return attributesClass;
	}

}
