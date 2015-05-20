/*
 * Copyright (c)  [2011-2015] "Neo Technology" / "Graph Aware Ltd."
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 */

package org.neo4j.ogm.session;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.neo4j.ogm.cypher.Parameter;
import org.neo4j.ogm.session.result.QueryStatistics;
import org.neo4j.ogm.session.transaction.Transaction;

/**
 * Provides core functionality to persist objects to the graph and load them in a variety of ways.
 *
 * @author Vince Bickers
 * @author Luanne Misquitta
 */
public interface Session {

    /**
     * Loads an entity of type T that matches the specified ID to the default depth.
     * @param type  The type of entity to load
     * @param id    The ID of the node or relationship to match
     * @param <T>   The type of entity to load
     * @return The instance of T loaded from the database that matches the specified ID or <code>null</code> if no match is found
     */
    <T> T load(Class<T> type, Long id);

    /**
     * Loads an entity of type T that matches the specified ID to the default depth.
     * @param type  The type of entity to load
     * @param id    The ID of the node or relationship to match
     * @param depth The maximum number of relationships away from the identified object to follow when loading related entities.
     *              A value of 0 just loads the object's properties and no related entities.  A value of -1 implies no depth limit.
     * @param <T>   The type of entity to load
     * @return The instance of T loaded from the database that matches the specified ID or <code>null</code> if no match is found
     */
    <T> T load(Class<T> type, Long id, int depth);


    /**
     * Retrieves all the entities of the given class in the database that match the specified IDs hydrated to the default depth.
     * @param type  The type of entity to load
     * @param ids   The IDs of the node or relationship to match
     * @param <T>   The type of entity to load
     * @returnA     {@link Collection} containing instances of the given type in the database which match the specified IDs or an empty collection if none
     *              are found, never <code>null</code>
     */
    <T> Collection<T> loadAll(Class<T> type, Collection<Long> ids);

    /**
     * Retrieves all the entities of the given class in the database that match the specified IDs hydrated to the default depth.
     * @param type  The type of entity to load
     * @param ids   The IDs of the node or relationship to match
     * @param depth The maximum number of relationships away from the identified object to follow when loading related entities.
     *              A value of 0 just loads the object's properties and no related entities.  A value of -1 implies no depth limit.
     *  @param <T>   The type of entity to load
     * @returnA     {@link Collection} containing instances of the given type in the database which match the specified IDs or an empty collection if none
     *              are found, never <code>null</code>
     */
    <T> Collection<T> loadAll(Class<T> type, Collection<Long> ids, int depth);

    /**
     * Retrieves all the entities of the given class in the database hydrated to the default depth.
     * @param type The type of entity to return.
     * @param <T>  The type of entity to return.
     * @return A {@link Collection} containing all instances of the given type in the database or an empty collection if none
     *         are found, never <code>null</code>
     */
    <T> Collection<T> loadAll(Class<T> type);

    /**
     * Retrieves all the entities of the given class in the database hydrated to the default depth.
     * @param type  The type of entity to return.
     * @param depth The maximum number of relationships away from the identified object to follow when loading related entities.
     *              A value of 0 just loads the object's properties and no related entities.  A value of -1 implies no depth limit.
     * @param <T>  The type of entity to return.
     * @return A {@link Collection} containing all instances of the given type in the database or an empty collection if none
     *         are found, never <code>null</code>
     */
    <T> Collection<T> loadAll(Class<T> type, int depth);

    /**
     * Reloads all of the entities in the given {@link Collection} to the specified depth.  Of course, this will
     * only work for persistent objects (i.e., those with a non-null <code>@GraphId</code> field).
     *
     * @param objects The objects to re-hydrate
     * @param <T>  The type of entity to return.
     * @return A new {@link Collection} of entities matching those in the given collection hydrated to the given depth
     */
    <T> Collection<T> loadAll(Collection<T> objects);

    /**
     * Reloads all of the entities in the given {@link Collection} to the specified depth.  Of course, this will
     * only work for persistent objects (i.e., those with a non-null <code>@GraphId</code> field).
     *
     * @param objects The objects to re-hydrate
     * @param depth   The depth to which the objects should be hydrated
     * @param <T>   The type of entity to return.
     * @return A new {@link Collection} of entities matching those in the given collection hydrated to the given depth
     */
    <T> Collection<T> loadAll(Collection<T> objects, int depth);

    /**
     * Retrieves all the entities of the specified type, hydrated to the default depth, that contains a property matching the given name with the given value.
     *
     * @param type      The type of entity to return.
     * @param property  A {@link Parameter} describing the property to match on.
     * @param <T>       The type of entity to return.
     * @return  A {@link Collection} containing all the entities that match the given property or an empty {@link Collection} if
     *         there aren't any matches, never <code>null</code>
     */
    <T> Collection<T> loadByProperty(Class<T> type, Parameter property);

    /**
     * Retrieves all the entities of the specified type, hydrated to the specified depth, that contains a property matching the given name with the given value.
     *
     * @param type      The type of entity to return.
     * @param property  A {@link Parameter} describing the property to match on.
     * @param depth     The depth to which the objects should be hydrated
     * @param <T>       The type of entity to return.
     * @return  A {@link Collection} containing all the entities that match the given property or an empty {@link Collection} if
     *         there aren't any matches, never <code>null</code>
     */
    <T> Collection<T> loadByProperty(Class<T> type, Parameter property, int depth);

    /**
     * Retrieves all the entities of the specified type, hydrated to the default depth, that contains properties matching the given name with the given value.
     *
     * @param type        The type of entity to return.
     * @param properties  A {@link List} of {@link Parameter} describing the properties to match on.
     * @param <T>         The type of entity to return.
     * @return  A {@link Collection} containing all the entities that match the given properties or an empty {@link Collection} if
     *         there aren't any matches, never <code>null</code>
     */
    <T> Collection<T> loadByProperties(Class<T> type, List<Parameter> properties);

    /**
     * Retrieves all the entities of the specified type, hydrated to the specified depth, that contains properties matching the given name with the given value.
     *
     * @param type        The type of entity to return.
     * @param properties  A {@link List} of {@link Parameter} describing the properties to match on.
     * @param depth         The depth to which the objects should be hydrated
     * @param <T>         The type of entity to return.
     * @return  A {@link Collection} containing all the entities that match the given properties or an empty {@link Collection} if
     *         there aren't any matches, never <code>null</code>
     */
    <T> Collection<T> loadByProperties(Class<T> type, List<Parameter> properties, int depth);


    /**
     * Issue a single Cypher update operation (such as a <tt>CREATE</tt>, <tt>MERGE</tt> or <tt>DELETE</tt> statement).
     *
     * @param jsonStatements The Cypher statement to execute
     * @return {@link QueryStatistics} representing statistics about graph modifications as a result of the cypher execution.
     */
    QueryStatistics execute(String statement);

    /**
     * Delete all nodes and relationships from the graph database.
     */
    void purgeDatabase();

    /**
     * Clear from memory the graph objects known to the OGM.
     */
    void clear();

    /**
     * Saves the specified entity in the graph database.  If the entity is currently transient then the persistent version of
     * the entity will be returned, containing its new graph ID.
     *
     * @param object The entity to save
     * @return The saved entity
     */
    <T> void save(T object);

    /**
     * Saves the specified entity in the graph database up to the specified depth.  If the entity is currently transient then the persistent version of
     * the entity will be returned, containing its new graph ID.
     *
     * @param object The entity to save
     * @param depth  The depth to which the objects should be saved
     * @return The saved entity
     */
    <T> void save(T object, int depth);

    /**
     * Removes the given node or relationship entity from the graph.
     *
     * @param object The entity to delete
     */
    <T> void delete(T object);

    /**
     * Removes all nodes or relationship entities of the given type from the graph.
     *
     * @param type  The type of entity to delete
     */
    <T> void deleteAll(Class<T> type);


    /**
     * Get the existing transaction if available
     *
     * @return an active Transaction, or null if none exists
     */
    Transaction getTransaction();

    /**
     * Begin a new transaction. If an existing transaction already exists, users must
     * decide whether to commit or rollback. Only one transaction can be bound to a thread
     * at any time, so active transactions that have not been closed but are no longer bound
     * to the thread must be handled by client code.
     *
     * @return a new active Transaction
     */
    Transaction beginTransaction();

    /**
     * Given a non modifying cypher statement this method will return a domain object that is hydrated to the
     * level specified in the given cypher query or a scalar (depending on the parametrized type).
     *
     * @param objectType The type that should be returned from the query.
     * @param cypher The parametrizable cypher to execute.
     * @param parameters Any scalar parameters to attach to the cypher.
     *
     * @param <T> A domain object or scalar.
     *
     * @return An instance of the objectType that matches the given cypher and parameters. Null if no object
     * is matched
     *
     * @throws java.lang.RuntimeException If more than one object is found.
     */
    <T> T queryForObject(Class<T> objectType, String cypher,  Map<String, ?> parameters);

    /**
     * Given a non modifying cypher statement this method will return a collection of domain objects that is hydrated to
     * the level specified in the given cypher query or a collection of scalars (depending on the parametrized type).
     *
     * @param objectType The type that should be returned from the query.
     * @param cypher The parametrizable cypher to execute.
     * @param parameters Any parameters to attach to the cypher.
     *
     * @param <T> A domain object or scalar.
     *
     * @return A collection of domain objects or scalars as prescribed by the parametrized type.
     */
    <T> Iterable<T> query(Class<T> objectType, String cypher, Map<String, ?> parameters);

    /**
     * Given a non modifying cypher statement this method will return a collection of Map's which represent Neo4j
     * objects as properties.
     *
     * Each element is a map which you can access by the name of the returned field
     *
     * TODO: Decide if we want to keep this behaviour?
     * TODO: Are we going to use the neo4jOperations conversion method to cast the value object to its proper class?
     *
     * @param cypher  The parametrisable cypher to execute.
     * @param parameters Any parameters to attach to the cypher.
     *
     * @return An {@link Iterable} of {@link Map}s with each entry representing a neo4j object's properties.
     */
    Iterable<Map<String, Object>> query(String cypher, Map<String, ?> parameters);

    /**
     * This method allows a cypher statement with a modification statement to be executed.
     *
     * <p>Parameters may be scalars or domain objects themselves.</p>
     *
     * @param cypher The parametrisable cypher to execute.
     * @param parameters Any parameters to attach to the cypher. These may be domain objects or scalars. Note that
     *                   if a complex domain object is provided only the properties of that object will be set.
     *                   If relationships of a provided object also need to be set then the cypher should reflect this
     *                   and further domain object parameters provided.
     * @return {@link QueryStatistics} representing statistics about graph modifications as a result of the cypher execution.
     */
    QueryStatistics execute(String cypher, Map<String, Object> parameters);

    /**
     * Applies the given {@link GraphCallback} in the scope of this {@link Session}, giving fine-grained control over
     * behaviour.
     *
     * @param graphCallback The {@link GraphCallback} to execute
     * @return The result of calling the given {@link GraphCallback}
     * @throws NullPointerException if invoked with <code>null</code>
     */
    <T> T doInTransaction(GraphCallback<T> graphCallback);

    /**
     * Counts all the <em>node</em> entities of the specified type.
     *
     * @param entity The {@link Class} denoting the type of entity to count
     * @return The number of entities in the database of the given type
     */
    long countEntitiesOfType(Class<?> entity);


}
