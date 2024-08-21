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
package org.citygml4j.cityjson;

import com.google.gson.annotations.SerializedName;
import org.citygml4j.cityjson.appearance.AppearanceType;
import org.citygml4j.cityjson.extension.ExtensibleType;
import org.citygml4j.cityjson.extension.ExtensionType;
import org.citygml4j.cityjson.feature.AbstractCityObjectType;
import org.citygml4j.cityjson.geometry.*;
import org.citygml4j.cityjson.metadata.MetadataType;

import java.util.*;
import java.util.stream.Collectors;

public class CityJSON implements ExtensibleType {
	private final String type = "CityJSON";
	private final String version = "1.0";
	MetadataType metadata;
	Map<String, ExtensionType> extensions;
	@SerializedName("CityObjects")
	Map<String, AbstractCityObjectType> cityObjects = new LinkedHashMap<>();
	VerticesList vertices = new VerticesList();
	TransformType transform;
	AppearanceType appearance;
	@SerializedName("geometry-templates")
	GeometryTemplatesType geometryTemplates;

	private transient Map<String, Object> extensionProperties;

	public String getType() {
		return type;
	}

	public boolean isSetVersion() {
		return version != null;
	}

	public String getVersion() {
		return version;
	}

	public boolean isSetMetadata() {
		return metadata != null;
	}

	public MetadataType getMetadata() {
		return metadata;
	}

	public void setMetadata(MetadataType metadata) {
		this.metadata = metadata;
	}
	
	public void unsetMetadata() {
		metadata = null;
	}

	public boolean hasExtensions() {
		return extensions != null && !extensions.isEmpty();
	}

	public void addExtension(String identifier, ExtensionType extension) {
		if (extensions == null)
			extensions = new HashMap<>();

		extensions.put(identifier, extension);
	}

	public Map<String, ExtensionType> getExtensions() {
		return extensions;
	}

	public void setExtensions(Map<String, ExtensionType> extensions) {
		if (extensions != null && !extensions.isEmpty())
			this.extensions = extensions;
	}

	public void unsetExtensions() {
		extensions = null;
	}
	
	public boolean hasCityObjects() {
		return !cityObjects.isEmpty();
	}
	
	public void addCityObject(AbstractCityObjectType cityObject) {
		if (!cityObject.isSetGmlId())
			cityObject.setGmlId("UUID_" + UUID.randomUUID().toString());

		cityObjects.put(cityObject.getGmlId(), cityObject);
	}

	@Override
	public void addChild(AbstractCityObjectType child) {
		addCityObject(child);
	}

	public AbstractCityObjectType getCityObject(String gmlId) {
		return cityObjects.get(gmlId);
	}

	public boolean hasCityObject(String gmlId) {
		return cityObjects.containsKey(gmlId);
	}

	public <T extends AbstractCityObjectType> List<T> getCityObjects(Class<T> type) {
		return cityObjects.values().stream().filter(type::isInstance).map(type::cast).collect(Collectors.toList());
	}
	
	public <T extends AbstractCityObjectType> T getCityObject(String gmlId, Class<T> type) {
		AbstractCityObjectType cityObject = cityObjects.get(gmlId);
		return type.isInstance(cityObject) ? type.cast(cityObject) : null;
	}

	public Collection<AbstractCityObjectType> getCityObjects() {
		return cityObjects.values();
	}

	public void setCityObjects(List<AbstractCityObjectType> cityObjects) {
		if (cityObjects != null) {
			for (AbstractCityObjectType cityObject : cityObjects)
				addCityObject(cityObject);
		}
	}
	
	public void removeCityObject(AbstractCityObjectType cityObject) {
		cityObjects.remove(cityObject.getGmlId());
	}
	
	public void removeCityObject(String gmlId) {
		cityObjects.remove(gmlId);
	}
	
	public void unsetCityObjects() {
		cityObjects.clear();
	}

	public void addVertex(List<Double> vertex) {
		if (vertex != null && vertex.size() == 3)
			vertices.addVertex(vertex);
	}

	public List<List<Double>> getVertices() {
		return vertices.getVertices();
	}

	public void setVertices(List<List<Double>> vertices) {
		if (vertices != null)
			this.vertices.setVertices(vertices);
	}
	
	public void unsetVertices() {
		vertices.clear();
	}

	public boolean isSetTransform() {
		return transform != null;
	}

	public TransformType getTransform() {
		return transform;
	}

	public void setTransform(TransformType transform) {
		this.transform = transform;
	}
	
	public void unsetTransform() {
		transform = null;
	}

	public boolean isSetAppearance() {
		return appearance != null;
	}

	public AppearanceType getAppearance() {
		return appearance;
	}

	public void setAppearance(AppearanceType appearance) {
		this.appearance = appearance;
	}
	
	public void unsetAppearance() {
		appearance = null;
	}

	public boolean isSetGeometryTemplates() {
		return geometryTemplates != null;
	}

	public GeometryTemplatesType getGeometryTemplates() {
		return geometryTemplates;
	}

	public void setGeometryTemplates(GeometryTemplatesType geometryTemplates) {
		this.geometryTemplates = geometryTemplates;
	}

	public void unsetGeometryTemplates() {
		geometryTemplates = null;
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

	public List<Double> calcBoundingBox() {
		Double[] bbox = new Double[]{
				Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE,
				-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE};
		
		for (List<Double> vertex : vertices.getVertices()) {
			if (vertex.size() > 2) {
				double x = vertex.get(0);
				double y = vertex.get(1);
				double z = vertex.get(2);
				
				if (x < bbox[0]) bbox[0] = x;
				if (y < bbox[1]) bbox[1] = y;
				if (z < bbox[2]) bbox[2] = z;
				if (x > bbox[3]) bbox[3] = x;
				if (y > bbox[4]) bbox[4] = y;
				if (z > bbox[5]) bbox[5] = z;
			}
		}

		if (transform != null) {
			for (int i = 0; i < bbox.length; i++) {
				bbox[i] = bbox[i] * transform.getScale().get(i % 3) + transform.getTranslate().get(i % 3);
			}
		}

		return Arrays.asList(bbox);
	}
	
	public Map<String, Integer> calcPresentLoDs() {
		Map<String, Integer> lods = new HashMap<>();
		for (AbstractCityObjectType cityObject : cityObjects.values()) {
			for (AbstractGeometryType geometry : cityObject.getGeometry()) {
				if (geometry instanceof AbstractGeometryObjectType) {
					String lod = String.valueOf(((AbstractGeometryObjectType) geometry).getLod());
					if (lod != null) {
						lods.merge(lod, 1, Integer::sum);
					}
				}
			}
		}

		if (geometryTemplates != null) {
			for (AbstractGeometryObjectType geometry : geometryTemplates.getTemplates()) {
				String lod = String.valueOf(geometry.getLod());
				if (lod != null) {
					lods.merge(lod, 1, Integer::sum);
				}
			}
		}
		
		return lods;
	}

	public void removeDuplicateVertices() {
		Map<String, Integer> indexes = new HashMap<>();
		Map<Integer, Integer> indexMap = new HashMap<>();
		int oldIndex = 0, newIndex = 0;

		for (Iterator<List<Double>> iter = getVertices().iterator(); iter.hasNext(); ) {
			List<Double> vertex = iter.next();
			String key = vertex.get(0).intValue() + " " + vertex.get(1).intValue() + " " + vertex.get(2).intValue();

			Integer index = indexes.get(key);
			if (index == null) {
				indexes.put(key, newIndex);
				indexMap.put(oldIndex, newIndex);
				newIndex++;
			} else {
				iter.remove();
				indexMap.put(oldIndex, index);
			}

			oldIndex++;
		}

		indexes.clear();
		if (getVertices().size() != indexMap.size()) {
			for (AbstractCityObjectType cityObject : getCityObjects()) {
				for (AbstractGeometryType geometry : cityObject.getGeometry())
					geometry.updateIndexes(indexMap);
			}
		}

		indexMap.clear();
	}

}
