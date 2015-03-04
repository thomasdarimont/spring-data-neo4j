package org.springframework.data.neo4j.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.server.Neo4jServer;

@Configuration
public class ProgrammaticNeo4jConfiguration extends Neo4jConfiguration {

    private Neo4jServer neo4jServer;
    private SessionFactory sessionFactory;

    @Override
    public Neo4jServer neo4jServer() {
        return neo4jServer;
    }

    public Neo4jServer getNeo4jServer() {
        return neo4jServer;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setNeo4jServer(Neo4jServer neo4jServer) {
        this.neo4jServer = neo4jServer;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
