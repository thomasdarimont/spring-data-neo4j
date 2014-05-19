package org.neo4j.cineasts.service;

import org.springframework.data.neo4j.template.Neo4jOperations;

import java.util.Map;

public class Neo4jDatabaseCleaner {
    private Neo4jOperations template;

    public Neo4jDatabaseCleaner(Neo4jOperations template) {
        this.template = template;
    }

    public Map<String, Object> cleanDb() {
        return template.query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r count(distinct n) as nodes, count(distinct r) as relationships", null).single();
    }
}
