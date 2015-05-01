package org.springframework.data.neo4j.integration.extensions;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * This interface declares a custom method that we want to
 * be available to all repositories that extend this class
 *
 * @author: Vince Bickers
 */
@NoRepositoryBean
public interface CustomGraphRepository<T> extends GraphRepository<T> {

    boolean sharedCustomMethod();

}
