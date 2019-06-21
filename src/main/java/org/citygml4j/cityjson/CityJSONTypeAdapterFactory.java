/*
 * citygml4j - The Open Source Java API for CityGML
 * https://github.com/citygml4j
 *
 * Copyright 2013-2019 Claus Nagel <claus.nagel@gmail.com>
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

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.citygml4j.cityjson.appearance.MaterialAdapter;
import org.citygml4j.cityjson.appearance.SolidCollectionMaterialObject;
import org.citygml4j.cityjson.appearance.SolidCollectionTextureObject;
import org.citygml4j.cityjson.appearance.SolidMaterialObject;
import org.citygml4j.cityjson.appearance.SolidTextureObject;
import org.citygml4j.cityjson.appearance.SurfaceCollectionMaterialObject;
import org.citygml4j.cityjson.appearance.SurfaceCollectionTextureObject;
import org.citygml4j.cityjson.appearance.TextureAdapter;
import org.citygml4j.cityjson.geometry.AbstractGeometryType;
import org.citygml4j.cityjson.geometry.GeometryTypeAdapter;
import org.citygml4j.cityjson.geometry.SemanticsType;
import org.citygml4j.cityjson.geometry.SemanticsTypeAdapter;

import java.util.Map;

public class CityJSONTypeAdapterFactory implements TypeAdapterFactory {

    private TypeToken<?> semantics = TypeToken.get(SemanticsType.class);

    private TypeToken<?> solidTexture = new TypeToken<Map<String, SolidTextureObject>>() {};
    private TypeToken<?> surfaceCollectionTexture = new TypeToken<Map<String, SurfaceCollectionTextureObject>>() {};
    private TypeToken<?> solidCollectionTexture = new TypeToken<Map<String, SolidCollectionTextureObject>>() {};

    private TypeToken<?> solidMaterial = new TypeToken<Map<String, SolidMaterialObject>>() {};
    private TypeToken<?> surfaceCollectionMaterial = new TypeToken<Map<String, SurfaceCollectionMaterialObject>>() {};
    private TypeToken<?> solidCollectionMaterial = new TypeToken<Map<String, SolidCollectionMaterialObject>>() {};

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (AbstractGeometryType.class.isAssignableFrom(type.getRawType()))
            return (TypeAdapter<T>) new GeometryTypeAdapter(gson, this);

        else if (type.equals(semantics))
            return (TypeAdapter<T>) new SemanticsTypeAdapter(gson, this);

        else if (type.equals(surfaceCollectionTexture))
            return (TypeAdapter<T>) new TextureAdapter<>(gson, SurfaceCollectionTextureObject.class);

        else if (type.equals(solidTexture))
            return (TypeAdapter<T>) new TextureAdapter<>(gson, SolidTextureObject.class);

        else if (type.equals(solidCollectionTexture))
            return (TypeAdapter<T>) new TextureAdapter<>(gson, SolidCollectionTextureObject.class);

        else if (type.equals(surfaceCollectionMaterial))
            return (TypeAdapter<T>) new MaterialAdapter<>(gson, SurfaceCollectionMaterialObject.class);

        else if (type.equals(solidMaterial))
            return (TypeAdapter<T>) new MaterialAdapter<>(gson, SolidMaterialObject.class);

        else if (type.equals(solidCollectionMaterial))
            return (TypeAdapter<T>) new MaterialAdapter<>(gson, SolidCollectionMaterialObject.class);

        return null;
    }
}
