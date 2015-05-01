package org.springframework.data.neo4j.integration.extensions;

import org.neo4j.ogm.session.Session;
import org.springframework.data.neo4j.repository.GraphRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * The class implementing our custom interface extension.
 *
 * @author: Vince Bickers
 */
@Repository
public class CustomGraphRepositoryImpl<T> extends GraphRepositoryImpl<T> implements CustomGraphRepository<T> {

    public CustomGraphRepositoryImpl(Class<T> clazz, Session session) {
        super(clazz, session);
    }

    @Override
    public boolean sharedCustomMethod() {
        return true;
    }
}
