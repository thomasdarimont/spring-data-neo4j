package org.springframework.data.neo4j.integration.extensions;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * Note how the repository base class for all our repositories is overridden
 * using the 'repositoryBaseClass' attribute.
 * This annotation change allows all our repositories to easily extend one or more
 * additional interfaces.
 *
 * @author: Vince Bickers
 */
@Configuration
@EnableNeo4jRepositories(repositoryBaseClass = CustomGraphRepositoryImpl.class)
@EnableTransactionManagement
public class CustomPersistenceContext extends Neo4jConfiguration {

    @Override
    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory("org.springframework.data.neo4j.integration.extensions.domain");
    }

    @Bean
    @Override
    public Neo4jServer neo4jServer() {
        return new RemoteServer("http://localhost:7879");
    }

}

