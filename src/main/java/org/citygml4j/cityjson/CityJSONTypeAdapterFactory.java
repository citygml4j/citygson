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

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.citygml4j.cityjson.appearance.*;
import org.citygml4j.cityjson.feature.AbstractCityObjectType;
import org.citygml4j.cityjson.feature.CityObjectTypeAdapter;
import org.citygml4j.cityjson.feature.CityObjectTypeFilter;
import org.citygml4j.cityjson.feature.CityObjectsAdapter;
import org.citygml4j.cityjson.geometry.*;

import java.util.Map;

public class CityJSONTypeAdapterFactory implements TypeAdapterFactory {
    private TypeToken<?> cityObjects = new TypeToken<Map<String, AbstractCityObjectType>>() {
    };
    private TypeToken<?> semantics = TypeToken.get(SemanticsType.class);
    private TypeToken<?> solidTexture = new TypeToken<Map<String, SolidTextureObject>>() {
    };
    private TypeToken<?> surfaceCollectionTexture = new TypeToken<Map<String, SurfaceCollectionTextureObject>>() {
    };
    private TypeToken<?> solidCollectionTexture = new TypeToken<Map<String, SolidCollectionTextureObject>>() {
    };
    private TypeToken<?> solidMaterial = new TypeToken<Map<String, SolidMaterialObject>>() {
    };
    private TypeToken<?> surfaceCollectionMaterial = new TypeToken<Map<String, SurfaceCollectionMaterialObject>>() {
    };
    private TypeToken<?> solidCollectionMaterial = new TypeToken<Map<String, SolidCollectionMaterialObject>>() {
    };

    private CityObjectTypeFilter typeFilter;
    private boolean processUnknownExtensions;
    private boolean serializeVerticesAsInteger;

    public CityJSONTypeAdapterFactory withTypeFilter(CityObjectTypeFilter inputFilter) {
        this.typeFilter = inputFilter;
        return this;
    }

    public CityJSONTypeAdapterFactory processUnknownExtensions(boolean processUnknownExtensions) {
        this.processUnknownExtensions = processUnknownExtensions;
        return this;
    }

    public CityJSONTypeAdapterFactory serializeVerticesAsInteger(boolean serializeVerticesAsInteger) {
        this.serializeVerticesAsInteger = serializeVerticesAsInteger;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (AbstractCityObjectType.class.isAssignableFrom(type.getRawType()))
            return (TypeAdapter<T>) new CityObjectTypeAdapter(gson, typeFilter, processUnknownExtensions, this);

        else if (AbstractGeometryType.class.isAssignableFrom(type.getRawType()))
            return (TypeAdapter<T>) new GeometryTypeAdapter(gson, this);

        else if (VerticesList.class.isAssignableFrom(type.getRawType()))
            return (TypeAdapter<T>) new VerticesListAdapter(serializeVerticesAsInteger);

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

        else if (CityJSON.class.isAssignableFrom(type.getRawType()))
            return (TypeAdapter<T>) new CityJSONAdapter(gson);

        else if (type.equals(cityObjects))
            return (TypeAdapter<T>) new CityObjectsAdapter(gson);

        return null;
    }
}
