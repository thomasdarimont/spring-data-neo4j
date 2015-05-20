/*
<<<<<<< HEAD
<<<<<<< HEAD
 * Copyright (c)  [2011-2015] "Neo Technology" / "Graph Aware Ltd."
=======
 * Copyright (c)  [2011-2015] "Pivotal Software, Inc." / "Neo Technology" / "Graph Aware Ltd."
>>>>>>> DATAGRAPH-629 - Support finders which accept multiple properties or operators other than equals.
=======
 * Copyright (c)  [2011-2015] "Neo Technology" / "Graph Aware Ltd."
>>>>>>> DATAGRAPH-629 - Support finders which accept multiple properties or operators other than equals.
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 */

package org.neo4j.ogm.cypher;

/**
 * A parameter which forms a filter on a query.

 * @author Luanne Misquitta
 */
public class Parameter {

	/**
	 * The property name on the entity to be used in the filter
	 */
	private String propertyName;

	/**
	 * The value of the property to filter on
	 */
	private Object propertyValue;

	/**
	 * The position of the property as specified in a derived finder method
	 */
	private Integer propertyPosition;

	/**
	 * The comparison operator to use in the property filter
	 */
	private ComparisonOperator comparisonOperator = ComparisonOperator.EQUALS;

	/**
	 * The boolean operator used to append this parameter to the previous ones
	 */
	private BooleanOperator booleanOperator = BooleanOperator.NONE;

	/**
	 * The parent entity which owns this parameter
	 */
	private Class ownerEntityType;

	/**
	 * The label of the entity which contains the nested property
	 */
	private String nestedEntityTypeLabel;

	/**
	 * The property name of the nested property on the parent entity
	 */
	private String nestedPropertyName;

	/**
	 * The type of the entity that owns the nested property
	 */
	private Class nestedPropertyType;

	/**
	 * The relationship type to be used for a nested property
	 */
	private String relationshipType;

	/**
	 * The relationship direction from the parent entity to the nested property
	 */
	private String relationshipDirection;


	public Parameter() {
	}

	/**
	 * Construct a parameter using the name of the field on the entity and the value to filter by
	 * @param propertyName the name of the field on the entity
	 * @param propertyValue the value to filter by
	 */
	public Parameter(String propertyName, Object propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	public String getRelationshipDirection() {
		return relationshipDirection;
	}

	/**
	 * Set the relationship direction from the parent entity to the nested property.
	 * Used internally in conjunction with Spring derived finders.
	 * @param relationshipDirection the relationship direction as defined in {@see org.neo4j.ogm.annotation.Relationship}
	 */
	public void setRelationshipDirection(String relationshipDirection) {
		this.relationshipDirection = relationshipDirection;
	}

	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Set the name of the field on the entity to use in the filter
	 * @param propertyName the name of the field on the entity
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * Set the value of the property to filter by
	 * @param propertyValue the property value
	 */
	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Integer getPropertyPosition() {
		return propertyPosition;
	}

	/**
	 * Set the position of the property.
	 * Used internally in conjuction with Spring derived finders.
	 * @param propertyPosition the property position
	 */
	public void setPropertyPosition(Integer propertyPosition) {
		this.propertyPosition = propertyPosition;
	}

	public ComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Set the {@see ComparisonOperator} to be used when filtering
	 * @param comparisonOperator the {@see ComparisonOperator}
	 */
	public void setComparisonOperator(ComparisonOperator comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	}

	public BooleanOperator getBooleanOperator() {
		return booleanOperator;
	}

	/**
	 * Set the {@see BooleanOperator} used to append this parameter to previous ones in the query
	 * @param booleanOperator the {@see BooleanOperator}
	 */
	public void setBooleanOperator(BooleanOperator booleanOperator) {
		this.booleanOperator = booleanOperator;
	}

	public Class getOwnerEntityType() {
		return ownerEntityType;
	}

	/**
	 * Set the parent entity that owns this parameter.
	 * Used internally in conjunction with Spring derived finders.
	 * @param ownerEntityType the parent entity type
	 */
	public void setOwnerEntityType(Class ownerEntityType) {
		this.ownerEntityType = ownerEntityType;
	}

	public String getNestedPropertyName() {
		return nestedPropertyName;
	}

	/**
	 * Set the nested property name based on the field defined in the parent entity.
	 * Used internally in conjunction with Spring derived finders.
	 * @param nestedPropertyName the nested property name
	 */
	public void setNestedPropertyName(String nestedPropertyName) {
		this.nestedPropertyName = nestedPropertyName;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	/**
	 * Set the relationship type to be used for the nested property.
	 * Used internally in conjunction with Spring derived finders.
	 * @param relationshipType the relationship type
	 */
	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	/**
	 * Is this parameter a nested one?
	 * @return true if nested i.e. connected by a relationship to the entity, false otherwise
	 */
	public boolean isNested() {
		return this.nestedPropertyName != null;
	}

	public Class getNestedPropertyType() {
		return nestedPropertyType;
	}

	/**
	 * Set the type of entity that owns the property defined on it.
	 * Used internally in conjunction with Spring derived finders.
	 * @param nestedPropertyType the nested property type
	 */
	public void setNestedPropertyType(Class nestedPropertyType) {
		this.nestedPropertyType = nestedPropertyType;
	}

	public String getNestedEntityTypeLabel() {
		return nestedEntityTypeLabel;
	}

	/**
	 * Set the label of the nested entity type.
	 * Used internally in conjunction with Spring derived finders.
	 * @param nestedEntityTypeLabel the label of the nested entity type
	 */
	public void setNestedEntityTypeLabel(String nestedEntityTypeLabel) {
		this.nestedEntityTypeLabel = nestedEntityTypeLabel;
	}


}
