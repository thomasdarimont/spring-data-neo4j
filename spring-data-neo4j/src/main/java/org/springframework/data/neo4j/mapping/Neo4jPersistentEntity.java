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
import org.springframework.data.mapping.*;
import org.springframework.data.util.TypeInformation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: Vince Bickers
 */
public class Neo4jPersistentEntity<T> implements PersistentEntity<T, Neo4jPersistentProperty> {

    private static final Logger logger = LoggerFactory.getLogger(Neo4jPersistentEntity.class);

    private String name;
    private Neo4jPersistentProperty idProperty;
    private Map<String, Neo4jPersistentProperty> properties = new HashMap<>();
    private Set<Association> associations = new HashSet<>();
    private Class<T> type;

    private Class<?> typeAlias;
    private TypeInformation<T> typeInformation;
    private PreferredConstructor preferredConstructor;
    private Neo4jPersistentProperty versionProperty;

    public Neo4jPersistentEntity(Class<T> clazz, ClassInfo classInfo, FieldInfo identityField) {

        this.name = classInfo.name();
        this.type = clazz;

        for (FieldInfo fieldInfo : classInfo.fieldsInfo().fields()) {
            String name = fieldInfo.getName();
            Neo4jPersistentProperty property = new Neo4jPersistentProperty(this, classInfo, fieldInfo, (fieldInfo == identityField));
            this.properties.put(name, property);
            logger.info("[entity].adding property mapping for " + fieldInfo.getName() + " to entity " + this.name);

            if (fieldInfo == identityField) {
                logger.info("[entity].-> this is the identity property");
                this.idProperty = property;
            }
            else if (property.isAssociation()) {
                logger.info("[entity].-> this is an association property");
                associations.add(new Association(property, null));
            } else {
                logger.info("[entity].-> this is a simple property");
            }
        }


    }

    @Override
    public String getName() {
        logger.info("[entity].getName() returns");
        return this.name;
    }

    @Override
    public PreferredConstructor<T, Neo4jPersistentProperty> getPersistenceConstructor() {
        logger.info("[entity].getPersistenceConstructor() returns");
        return this.preferredConstructor;
    }

    @Override
    public boolean isConstructorArgument(PersistentProperty<?> property) {
        logger.info("[entity].isConstructorArgument() returns false for: " + property);
        return false;
    }

    @Override
    public boolean isIdProperty(PersistentProperty<?> property) {
        logger.info("[entity].isIdPropertyCalled() returns ? " + property);
        return this.idProperty == property;
    }

    @Override
    public boolean isVersionProperty(PersistentProperty<?> property) {
        logger.info("[entity].isIdPropertyCalled() returns false for: " + property);
        return false;
    }

    @Override
    public Neo4jPersistentProperty getIdProperty() {
        logger.info("[entity].getIdProperty() returns " + this.idProperty);
        return this.idProperty;
    }

    @Override
    public Neo4jPersistentProperty getVersionProperty() {
        logger.info("[entity].getVersionProperty() returns " + this.versionProperty);
        return this.versionProperty;
    }

    @Override
    public Neo4jPersistentProperty getPersistentProperty(String name) {
        logger.info("[entity].getPersistentProperty() returns " + this.properties.get(name) + " for: " + name);
        return this.properties.get(name);
    }

    @Override
    public Neo4jPersistentProperty getPersistentProperty(Class<? extends Annotation> annotationType) {
        logger.info("[entity].getPersistentProperty() returns null for annotation type: " + annotationType);
        return null;
    }

    @Override
    public boolean hasIdProperty() {
        logger.info("[entity].hasIdProperty() returns " + (this.idProperty != null));
        return this.idProperty != null;
    }

    @Override
    public boolean hasVersionProperty() {
        logger.info("[entity].hasVersionProperty() returns " + (this.versionProperty != null));
        return this.versionProperty != null;
    }

    @Override
    public Class<T> getType() {
        logger.info("[entity].getType() returns " + this.type);
        return this.type;
    }

    @Override
    public Object getTypeAlias() {
        logger.info("[entity].getTypeAlias() returns " + this.typeAlias);
        return this.typeAlias;
    }

    @Override
    public TypeInformation<T> getTypeInformation() {
        logger.info("[entity].getTypeInformation() returns " + this.typeInformation);
        return this.typeInformation;
    }

    @Override
    public void doWithProperties(PropertyHandler<Neo4jPersistentProperty> handler) {
        logger.info("[entity].doWithProperties(1) called");
    }

    @Override
    public void doWithProperties(SimplePropertyHandler handler) {
        logger.info("[entity].doWithProperties(2) called");     }

    @Override
    public void doWithAssociations(AssociationHandler<Neo4jPersistentProperty> handler) {
        logger.info("[entity].doWithAssociations(1) called");
    }

    @Override
    public void doWithAssociations(SimpleAssociationHandler handler) {
        logger.info("[entity].doWithAssociations(2) called");
        for (Association<?> association : associations) {
            handler.doWithAssociation(association);
        }

    }

    @Override
    public <A extends Annotation> A findAnnotation(Class<A> annotationType) {
        logger.info("[entity].findAnnotation called");
        return null;
    }

    @Override
    public PersistentPropertyAccessor getPropertyAccessor(Object bean) {
        logger.info("[entity].getPropertyAccessor called");
        return null;
    }

    @Override
    public IdentifierAccessor getIdentifierAccessor(Object bean) {
        logger.info("[entity].getIdentifierAccessor called");
        return null;
    }
}
