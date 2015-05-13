/*
 * Copyright (c)  [2011-2015] "Pivotal Software, Inc." / "Neo Technology" / "Graph Aware Ltd."
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 */
package org.springframework.data.neo4j.mapping;

import org.neo4j.ogm.metadata.info.ClassInfo;
import org.neo4j.ogm.metadata.info.FieldInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;

import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.util.TypeInformation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: Vince Bickers
 */
public class Neo4jPersistentProperty implements PersistentProperty<Neo4jPersistentProperty> {

    private static final Logger logger = LoggerFactory.getLogger(Neo4jPersistentProperty.class);

    private Field field;
    private Class<?> type;
    private String name;
    private boolean isCollectionLike;
    private boolean isArray;
    private Neo4jPersistentEntity owner;
    private boolean isAssociation;
    private Class<?> rawType;
    private Class<?> actualType;
    private Class<?> componentType;
    private boolean isIdProperty;

    private Class<?> mapValueType;

    private TypeInformation<?> typeInformation;
    private boolean isEntity;

    private boolean isMap;
    private boolean isTransient;
    private boolean isWritable;

    public Neo4jPersistentProperty(Neo4jPersistentEntity owner, ClassInfo classInfo, FieldInfo fieldInfo, boolean idProperty) {
        try {
            this.field = classInfo.getField(fieldInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.owner = owner;
        this.name = fieldInfo.getName();

        this.type = field.getType();
        this.rawType = this.type;
        this.actualType = this.type;

        this.isAssociation = !fieldInfo.isSimple();
        this.isCollectionLike = !fieldInfo.isScalar();
        this.isArray = fieldInfo.isArray();
        this.isIdProperty = idProperty;

        // set rawtype, actualtype, componenttype and mapvaluetype according to other property attributes
        if (fieldInfo.isCollection()) {
            this.actualType = classInfo.getType(fieldInfo.getTypeParameterDescriptor());
        }

        else if (fieldInfo.isArray()) {
            this.componentType = classInfo.getType(fieldInfo.getTypeParameterDescriptor());
        }

        // todo: mapValueType
    }

    @Override
    public PersistentEntity<?, Neo4jPersistentProperty> getOwner() {
        logger.info("[property].getOwner() returns " + this.owner);
        return this.owner;
    }

    @Override
    public String getName() {
        logger.info("[property].getName() returns " + this.name);
        return this.name;
    }

    @Override
    public Class<?> getType() {
        logger.info("[property].getType() returns " + this.type);
        return this.type;
    }

    @Override
    public TypeInformation<?> getTypeInformation() {
        logger.info("[property].getTypeInformation() returns " + this.typeInformation);
        return this.typeInformation;
    }

    @Override
    public Iterable<? extends TypeInformation<?>> getPersistentEntityType() {
        logger.info("[property].getPersistentEntityType() returns " + null);
        return null;
    }

    @Override
    public Method getGetter() {
        logger.info("[property].getGetter() returns " + null);
        return null;
    }

    @Override
    public Method getSetter() {
        logger.info("[property].getSetter() returns " + null);
        return null;
    }

    @Override
    public Field getField() {
        logger.info("[property].getField() returns " + this.field);
        return this.field;
    }

    @Override
    public String getSpelExpression() {
        logger.info("[property].getSpelExpression() returns " + null);
        return null;
    }

    @Override
    public Association<Neo4jPersistentProperty> getAssociation() {
        logger.info("[property].getAssociation() returns " + null);
        return null;
    }

    @Override
    public boolean isEntity() {
        logger.info("[property].isEntity() returns " + this.isEntity);
        return this.isEntity;
    }

    @Override
    public boolean isIdProperty() {
        logger.info("[property].isIdProperty() returns " + this.isIdProperty);
        return this.isIdProperty;
    }

    @Override
    public boolean isVersionProperty() {
        logger.info("[property].isVersionProperty() returns " + false);
        return false;
    }

    @Override
    public boolean isCollectionLike() {
        logger.info("[property].isCollectionLike() returns " + this.isCollectionLike);
        return this.isCollectionLike;
    }

    @Override
    public boolean isMap() {
        logger.info("[property].isMap() returns " + this.isMap);
        return this.isMap;
    }

    @Override
    public boolean isArray() {
        logger.info("[property].isArray() returns " + this.isArray);
        return this.isArray;
    }

    @Override
    public boolean isTransient() {
        logger.info("[property].isTransient() returns " + this.isTransient);
        return this.isTransient;
    }

    @Override
    public boolean isWritable() {
        logger.info("[property].isWritable() returns " + true);
        return true;
    }

    @Override
    public boolean isAssociation() {
        logger.info("[property].isAssociation() returns " + this.isAssociation);
        return this.isAssociation;
    }

    /**
     * Returns the component type of the type if it is a {@link java.util.Collection}. Will return the type of the key if
     * the property is a {@link java.util.Map}.
     *
     * @return the component type, the map's key type or {@literal null} if neither {@link java.util.Collection} nor
     *         {@link java.util.Map}.
     */
    @Override
    public Class<?> getComponentType() {
        logger.info("[property].getComponentType() returns " + this.componentType);
        return this.componentType;
    }

    /**
     * Returns the raw type as it's pulled from from the reflected property.
     *
     * @return the raw type of the property.
     */
    @Override
    public Class<?> getRawType() {
        logger.info("[property].getRawType() returns " + this.rawType);
        return this.rawType;
    }

    /**
     * Returns the type of the values if the property is a {@link java.util.Map}.
     *
     * @return the map's value type or {@literal null} if no {@link java.util.Map}
     */
    @Override
    public Class<?> getMapValueType() {
        logger.info("[property].getMapValueType() returns " + this.mapValueType);
        return this.mapValueType;
    }

    /**
     * Returns the actual type of the property. This will be the original property type if no generics were used, the
     * component type for collection-like types and arrays as well as the value type for map properties.
     *
     * @return
     */
    @Override
    public Class<?> getActualType() {
        logger.info("[property].getActualType() returns " + this.actualType);
        return this.actualType;
    }

    @Override
    public <A extends Annotation> A findAnnotation(Class<A> annotationType) {
        logger.info("[property].getAnnotation() returns " + field.getAnnotation(annotationType));
        return field.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A findPropertyOrOwnerAnnotation(Class<A> annotationType) {
        logger.info("[property].findPropertyOrOwnerAnnotation() returns " + "?");
        A annotation = findAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        return (A) owner.findAnnotation(annotationType);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        logger.info("[property].isAnnotationPresent() returns " + field.isAnnotationPresent(annotationType));
        return field.isAnnotationPresent(annotationType);
    }

    @Override
    // force to use field access
    public boolean usePropertyAccess() {
        //logger.info("[property].usePropertyAccess() returns " + false);
        return false;
    }
}
