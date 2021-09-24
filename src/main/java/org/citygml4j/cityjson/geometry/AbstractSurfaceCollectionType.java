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

import org.citygml4j.cityjson.appearance.SurfaceCollectionMaterialObject;
import org.citygml4j.cityjson.appearance.SurfaceCollectionTextureObject;

import java.util.*;

public abstract class AbstractSurfaceCollectionType extends AbstractGeometryObjectType
implements GeometryWithSemantics, GeometryWithAppearance<SurfaceCollectionMaterialObject, SurfaceCollectionTextureObject> {
	private final GeometryTypeName type;
	private List<List<List<Integer>>> boundaries = new ArrayList<>();
	private SurfaceCollectionSemanticsObject semantics;
	private Map<String, SurfaceCollectionMaterialObject> material;
	private Map<String, SurfaceCollectionTextureObject> texture;
	
	protected AbstractSurfaceCollectionType(GeometryTypeName type) {
		this.type = type;
	}
	
	@Override
	public GeometryTypeName getType() {
		return type;
	}
	
	public void addSurface(List<List<Integer>> surface) {
		if (surface != null && surface.size() > 0)
			boundaries.add(surface);
	}

	public List<List<List<Integer>>> getSurfaces() {
		return boundaries;
	}

	public void setSurfaces(List<List<List<Integer>>> surfaces) {
		if (surfaces != null)
			boundaries = surfaces;
	}
	
	public void unsetSurfaces() {
		boundaries.clear();
	}
	
	@Override
	public boolean isSetSemantics() {
		return semantics != null;
	}

	@Override
	public SurfaceCollectionSemanticsObject getSemantics() {
		return semantics;
	}

	public void setSemantics(SurfaceCollectionSemanticsObject semantics) {
		this.semantics = semantics;
	}
	
	@Override
	public void unsetSemantics() {
		semantics = null;
	}
	
	@Override
	public boolean isSetMaterial() {
		return material != null;
	}
	
	public void addMaterial(SurfaceCollectionMaterialObject materialObject) {
		if (material == null)
			material = new HashMap<>();
		
		material.put(materialObject.getTheme(), materialObject);
	}
	
	@Override
	public Collection<SurfaceCollectionMaterialObject> getMaterial() {
		return material.values();
	}
	
	@Override
	public SurfaceCollectionMaterialObject getMaterial(String theme) {
		return material != null ? material.get(theme) : null;
	}

	public void setMaterial(List<SurfaceCollectionMaterialObject> material) {
		if (material == null)
			this.material = null;
		else {
			for (SurfaceCollectionMaterialObject object : material)
				this.material.put(object.getTheme(), object);
		}
	}
	
	@Override
	public void removeMaterial(SurfaceCollectionMaterialObject materialObject) {
		if (this.material != null)
			this.material.remove(materialObject.getTheme());
	}
	
	@Override
	public void removeMaterial(String theme) {
		if (this.material != null)
			this.material.remove(theme);
	}
	
	@Override
	public void unsetMaterial() {
		material = null;
	}

	@Override
	public boolean isSetTexture() {
		return texture != null;
	}
	
	public void addTexture(SurfaceCollectionTextureObject textureObject) {
		if (texture == null)
			texture = new HashMap<>();
		
		texture.put(textureObject.getTheme(), textureObject);
	}
	
	@Override
	public Collection<SurfaceCollectionTextureObject> getTexture() {
		return texture.values();
	}
	
	@Override
	public SurfaceCollectionTextureObject getTexture(String theme) {
		return texture != null ? texture.get(theme) : null;
	}

	public void setTexture(List<SurfaceCollectionTextureObject> texture) {
		if (texture == null)
			this.texture = null;
		else {
			for (SurfaceCollectionTextureObject object : texture)
				this.texture.put(object.getTheme(), object);
		}
	}
	
	@Override
	public void removeTexture(SurfaceCollectionTextureObject textureObject) {
		if (texture != null)
			texture.remove(textureObject.getTheme());
	}
	
	@Override
	public void removeTexture(String theme) {
		if (texture != null)
			texture.remove(theme);
	}
	
	@Override
	public void unsetTexture() {
		texture = null;
	}
	
	@Override
	public void unsetAppearance() {
		material = null;
		texture = null;
	}

	@Override
	public void updateIndexes(Map<Integer, Integer> indexMap) {
		for (List<List<Integer>> surface : boundaries) {
			for (List<Integer> ring : surface) {
				for (int index = 0; index < ring.size(); index++) {
					Integer update = indexMap.get(ring.get(index));
					if (update != null)
						ring.set(index, update);
				}
			}
		}
	}
}
