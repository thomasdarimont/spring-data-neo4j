package org.springframework.data.neo4j.integration.extensions;

import org.springframework.data.neo4j.integration.movies.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author: Vince Bickers
 */
@Repository
public interface UserRepository extends CustomGraphRepository<User> {
}
