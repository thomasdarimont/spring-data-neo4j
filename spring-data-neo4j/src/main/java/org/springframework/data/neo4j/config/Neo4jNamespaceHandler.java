package org.springframework.data.neo4j.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.data.neo4j.repository.config.Neo4jRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

public class Neo4jNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {

		RepositoryConfigurationExtension extension = new Neo4jRepositoryConfigurationExtension();
		RepositoryBeanDefinitionParser repositoryBeanDefinitionParser = new RepositoryBeanDefinitionParser(extension);

		registerBeanDefinitionParser("repositories", repositoryBeanDefinitionParser);
		registerBeanDefinitionParser("config", new DataGraphBeanDefinitionParser());
	}
}
