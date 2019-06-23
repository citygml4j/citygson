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
import org.citygml4j.cityjson.feature.AbstractCityObjectType;
import org.citygml4j.cityjson.feature.CityObjectTypeAdapter;
import org.citygml4j.cityjson.feature.CityObjectTypeFilter;
import org.citygml4j.cityjson.feature.CityObjectsAdapter;
import org.citygml4j.cityjson.geometry.AbstractGeometryType;
import org.citygml4j.cityjson.geometry.GeometryTypeAdapter;
import org.citygml4j.cityjson.geometry.SemanticsType;
import org.citygml4j.cityjson.geometry.SemanticsTypeAdapter;
import org.citygml4j.cityjson.geometry.VerticesList;
import org.citygml4j.cityjson.geometry.VerticesListAdapter;
import org.citygml4j.cityjson.metadata.ThematicModelType;
import org.citygml4j.cityjson.metadata.feature.AbstractFeatureDataType;
import org.citygml4j.cityjson.metadata.feature.CityObjectGroupDataType;
import org.citygml4j.cityjson.metadata.feature.CityObjectGroupDataTypeAdapter;
import org.citygml4j.cityjson.metadata.feature.FeatureDataTypeAdapter;

import java.util.Map;

public class CityJSONTypeAdapterFactory implements TypeAdapterFactory {
    private TypeToken<?> cityObjects = new TypeToken<Map<String, AbstractCityObjectType>>() {};
    private TypeToken<?> semantics = TypeToken.get(SemanticsType.class);
    private TypeToken<?> solidTexture = new TypeToken<Map<String, SolidTextureObject>>() {};
    private TypeToken<?> surfaceCollectionTexture = new TypeToken<Map<String, SurfaceCollectionTextureObject>>() {};
    private TypeToken<?> solidCollectionTexture = new TypeToken<Map<String, SolidCollectionTextureObject>>() {};
    private TypeToken<?> solidMaterial = new TypeToken<Map<String, SolidMaterialObject>>() {};
    private TypeToken<?> surfaceCollectionMaterial = new TypeToken<Map<String, SurfaceCollectionMaterialObject>>() {};
    private TypeToken<?> solidCollectionMaterial = new TypeToken<Map<String, SolidCollectionMaterialObject>>() {};
    private TypeToken<?> featureMetaData = new TypeToken<Map<ThematicModelType, AbstractFeatureDataType>>() {};

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

        else if (type.equals(featureMetaData))
            return (TypeAdapter<T>) new FeatureDataTypeAdapter(gson);

        else if (CityObjectGroupDataType.class.isAssignableFrom(type.getRawType()))
            return (TypeAdapter<T>) new CityObjectGroupDataTypeAdapter(gson);

        return null;
    }
}
