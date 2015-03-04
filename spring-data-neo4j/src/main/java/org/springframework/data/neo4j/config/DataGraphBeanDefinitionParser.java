package org.springframework.data.neo4j.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.data.neo4j.server.RemoteServer;
import org.w3c.dom.Element;

import static org.springframework.util.StringUtils.hasText;

public class DataGraphBeanDefinitionParser extends AbstractBeanDefinitionParser {

    private static final String NEO4J_SERVER_REF = "neo4j-server-ref";
    private static final String NEO4J_SERVER_URL = "neo4j-server-url";
    private static final String NEO4J_SERVER = "neo4jServer";

    private static final String SESSION_FACTORY_REF = "session-factory-ref";
    private static final String BASE_PACKAGE = "base-package";
    private static final String SESSION_SCOPE = "session-scope";
    private static final String SESSION_FACTORY = "sessionFactory";

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext context) {
        BeanDefinitionBuilder configBuilder = BeanDefinitionBuilder.rootBeanDefinition(ProgrammaticNeo4jConfiguration.class);
        setupNeo4jServer(element, context, configBuilder);
        setupSessionFactory(element, context, configBuilder);
        return getSourcedBeanDefinition(configBuilder, element, context);
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    private void setupNeo4jServer(Element element, ParserContext context, BeanDefinitionBuilder configBuilder) {
        String neo4jServerRef = element.getAttribute(NEO4J_SERVER_REF);
        if (!hasText(neo4jServerRef)) {
            neo4jServerRef = handleCreatingServer(element, context, configBuilder);
        }
        configBuilder.addPropertyReference(NEO4J_SERVER, neo4jServerRef);
    }

    private String handleCreatingServer(Element element, ParserContext context, BeanDefinitionBuilder configBuilder) {
        String neo4jServerUrl = element.getAttribute(NEO4J_SERVER_URL);

        if (!hasText(neo4jServerUrl)) {
            return null;
        }

        BeanDefinitionBuilder serverDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(RemoteServer.class);
        serverDefinitionBuilder.addConstructorArgValue(neo4jServerUrl);
        serverDefinitionBuilder.setScope("singleton");
        context.getRegistry().registerBeanDefinition(NEO4J_SERVER, serverDefinitionBuilder.getBeanDefinition());

        return NEO4J_SERVER;
    }

    private void setupSessionFactory(Element element, ParserContext context, BeanDefinitionBuilder configBuilder) {
        String sessionFactoryRef = element.getAttribute(SESSION_FACTORY_REF);
        if (!hasText(sessionFactoryRef)) {
            sessionFactoryRef = handleCreatingSessionFactory(element, context, configBuilder);
        }
        configBuilder.addPropertyReference(SESSION_FACTORY, sessionFactoryRef);
    }

    private String handleCreatingSessionFactory(Element element, ParserContext context, BeanDefinitionBuilder configBuilder) {
        String basePackage = element.getAttribute(BASE_PACKAGE);

        if (!hasText(basePackage)) {
            return null;
        }

        BeanDefinitionBuilder sessionFactoryDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(SessionFactory.class);
        sessionFactoryDefinitionBuilder.addConstructorArgValue(basePackage.split(","));
        if (element.getAttribute(SESSION_SCOPE) == null) {
            sessionFactoryDefinitionBuilder.setScope("session");
        }
        else {
            sessionFactoryDefinitionBuilder.setScope(element.getAttribute(SESSION_SCOPE));
        }
        context.getRegistry().registerBeanDefinition(SESSION_FACTORY, sessionFactoryDefinitionBuilder.getBeanDefinition());

        return SESSION_FACTORY;
    }

    private AbstractBeanDefinition getSourcedBeanDefinition(BeanDefinitionBuilder builder, Element source, ParserContext context) {
        AbstractBeanDefinition definition = builder.getBeanDefinition();
        definition.setSource(context.extractSource(source));
        return definition;
    }
}
