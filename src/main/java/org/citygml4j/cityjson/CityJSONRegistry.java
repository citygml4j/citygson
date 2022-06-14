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

package org.citygml4j.cityjson;

import org.citygml4j.cityjson.extension.ExtensibleType;
import org.citygml4j.cityjson.extension.ExtensionException;
import org.citygml4j.cityjson.feature.*;
import org.citygml4j.cityjson.geometry.InternalSemanticsType;
import org.citygml4j.cityjson.geometry.SemanticsType;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CityJSONRegistry {
    private static CityJSONRegistry instance;

    private final Map<String, Class<? extends AbstractCityObjectType>> types;
    private final Map<String, Class<? extends SemanticsType>> semanticSurfaces;
    private final Map<Class<? extends ExtensibleType>, Map<String, Type>> properties;

    private final Set<String> coreTypes;

    private CityJSONRegistry() {
        types = new ConcurrentHashMap<>();
        semanticSurfaces = new ConcurrentHashMap<>();
        properties = new ConcurrentHashMap<>();

        types.put("Building", BuildingType.class);
        types.put("BuildingPart", BuildingPartType.class);
        types.put("BuildingInstallation", BuildingInstallationType.class);
        types.put("Bridge", BridgeType.class);
        types.put("BridgePart", BridgePartType.class);
        types.put("BridgeInstallation", BridgeInstallationType.class);
        types.put("BridgeConstructionElement", BridgeConstructionElementType.class);
        types.put("TINRelief", TINReliefType.class);
        types.put("WaterBody", WaterBodyType.class);
        types.put("PlantCover", PlantCoverType.class);
        types.put("SolitaryVegetationObject", SolitaryVegetationObjectType.class);
        types.put("LandUse", LandUseType.class);
        types.put("CityFurniture", CityFurnitureType.class);
        types.put("GenericCityObject", GenericCityObjectType.class);
        types.put("Road", RoadType.class);
        types.put("Railway", RailwayType.class);
        types.put("TransportSquare", TransportSquareType.class);
        types.put("Tunnel", TunnelType.class);
        types.put("TunnelPart", TunnelPartType.class);
        types.put("TunnelInstallation", TunnelInstallationType.class);
        types.put("CityObjectGroup", CityObjectGroupType.class);

        semanticSurfaces.put("RoofSurface", InternalSemanticsType.class);
        semanticSurfaces.put("GroundSurface", InternalSemanticsType.class);
        semanticSurfaces.put("WallSurface", InternalSemanticsType.class);
        semanticSurfaces.put("ClosureSurface", InternalSemanticsType.class);
        semanticSurfaces.put("OuterCeilingSurface", InternalSemanticsType.class);
        semanticSurfaces.put("OuterFloorSurface", InternalSemanticsType.class);
        semanticSurfaces.put("Window", InternalSemanticsType.class);
        semanticSurfaces.put("Door", InternalSemanticsType.class);
        semanticSurfaces.put("TrafficArea", InternalSemanticsType.class);
        semanticSurfaces.put("AuxiliaryTrafficArea", InternalSemanticsType.class);
        semanticSurfaces.put("WaterSurface", InternalSemanticsType.class);
        semanticSurfaces.put("WaterGroundSurface", InternalSemanticsType.class);
        semanticSurfaces.put("WaterClosureSurface", InternalSemanticsType.class);

        coreTypes = new HashSet<>(types.keySet());
    }

    public static synchronized CityJSONRegistry getInstance() {
        if (instance == null)
            instance = new CityJSONRegistry();

        return instance;
    }

    public boolean isCoreCityObject(String type) {
        return coreTypes.contains(type);
    }

    public String getCityObjectType(AbstractCityObjectType cityObject) {
        String type = null;
        for (Map.Entry<String, Class<? extends AbstractCityObjectType>> entry : types.entrySet()) {
            if (cityObject.getClass() == entry.getValue()) {
                type = entry.getKey();
                break;
            }
        }

        if (type == null)
            type = cityObject.getClass().getTypeName();

        return type;
    }

    public Class<? extends AbstractCityObjectType> getCityObjectClass(String type) {
        Class<? extends AbstractCityObjectType> typeClass = types.get(type);
        if (typeClass == null) {
            try {
                Class<?> tmp = Class.forName(type);
                if (AbstractCityObjectType.class.isAssignableFrom(tmp))
                    typeClass = tmp.asSubclass(AbstractCityObjectType.class);
            } catch (ClassNotFoundException e) {
                //
            }
        }

        return typeClass;
    }

    public void registerCityObject(String type, Class<? extends AbstractCityObjectType> typeClass) throws ExtensionException {
        if (type == null)
            throw new ExtensionException("The city object type must not be null.");

        if (typeClass == null)
            throw new ExtensionException("The city object type class must not be null.");

        if (types.containsKey(type))
            throw new ExtensionException("The city object type '" + type + "' is already registered.");

        if (types.containsValue(typeClass))
            throw new ExtensionException("The city object type class '" + typeClass.getTypeName() + "' is already registered.");

        types.put(type, typeClass);
    }

    public void unregisterCityObject(String type) {
        types.remove(type);
    }

    public String getSemanticSurfaceType(SemanticsType semanticsType) {
        String type = null;
        for (Map.Entry<String, Class<? extends SemanticsType>> entry : semanticSurfaces.entrySet()) {
            if (semanticsType.getClass() == entry.getValue()) {
                type = entry.getKey();
                break;
            }
        }

        if (type == null)
            type = semanticsType.getClass().getTypeName();

        return type;
    }

    public Class<? extends SemanticsType> getSemanticSurfaceClass(String type) {
        Class<? extends SemanticsType> typeClass = semanticSurfaces.get(type);
        if (typeClass == null) {
            try {
                Class<?> tmp = Class.forName(type);
                if (SemanticsType.class.isAssignableFrom(tmp))
                    typeClass = tmp.asSubclass(SemanticsType.class);
            } catch (ClassNotFoundException e) {
                //
            }
        }

        return typeClass;
    }

    public void registerSemanticSurface(String type, Class<? extends SemanticsType> semanticSurfaceClass) throws ExtensionException {
        if (type == null)
            throw new ExtensionException("The semantic surface type must not be null.");

        if (semanticSurfaceClass == null)
            throw new ExtensionException("The semantic surface class must not be null.");

        if (semanticSurfaces.containsKey(type))
            throw new ExtensionException("The semantic surface type '" + type + "' is already registered.");

        if (semanticSurfaces.containsValue(semanticSurfaceClass))
            throw new ExtensionException("The semantic surface class '" + semanticSurfaceClass.getTypeName() + "' is already registered.");

        semanticSurfaces.put(type, semanticSurfaceClass);
    }

    public void unregisterSemanticSurface(String type) {
        semanticSurfaces.remove(type);
    }

    public Type getExtensionPropertyClass(String propertyName, ExtensibleType target) {
        for (Map.Entry<Class<? extends ExtensibleType>, Map<String, Type>> entry : properties.entrySet()) {
            if (entry.getKey().isInstance(target))
                return entry.getValue().get(propertyName);
        }

        return null;
    }

    public boolean hasExtensionProperty(String propertyName, ExtensibleType target) {
        return getExtensionPropertyClass(propertyName, target) != null;
    }

    public boolean hasExtensionProperty(String propertyName, Class<? extends ExtensibleType> targetClass) {
        return properties.getOrDefault(targetClass, Collections.emptyMap()).containsKey(propertyName);
    }

    public void registerExtensionProperty(String name, Type attributeType, Class<? extends ExtensibleType> targetClass) throws ExtensionException {
        if (name == null)
            throw new ExtensionException("The extension property name must not be null.");

        if (attributeType == null)
            throw new ExtensionException("The extension property type must not be null.");

        if (targetClass == null)
            throw new ExtensionException("The extension property target class must not be null.");

        for (Map.Entry<Class<? extends ExtensibleType>, Map<String, Type>> entry : properties.entrySet()) {
            if (entry.getKey().isAssignableFrom(targetClass) || targetClass.isAssignableFrom(entry.getKey())) {
                if (entry.getValue().containsKey(name))
                    throw new ExtensionException("The extension property '" + name + "' is already registered with " + entry.getKey().getTypeName());
            }
        }

        Map<String, Type> property = properties.computeIfAbsent(targetClass, v -> new ConcurrentHashMap<>());
        property.put(name, attributeType);
    }

    public void unregisterExtensionProperty(String name, Class<? extends ExtensibleType> targetClass) {
        if (targetClass != null) {
            for (Map.Entry<Class<? extends ExtensibleType>, Map<String, Type>> entry : properties.entrySet()) {
                if (entry.getKey().isAssignableFrom(targetClass) || targetClass.isAssignableFrom(entry.getKey()))
                    entry.getValue().remove(name);
            }
        }
    }
}
