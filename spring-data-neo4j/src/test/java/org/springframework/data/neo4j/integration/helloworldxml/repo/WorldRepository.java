package org.springframework.data.neo4j.integration.helloworldxml.repo;

import org.springframework.data.neo4j.integration.helloworldxml.domain.World;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldRepository extends GraphRepository<World> {}
