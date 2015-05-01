package org.springframework.data.neo4j.integration.extensions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

/**
 * @author: Vince Bickers
 */
@ContextConfiguration(classes = {CustomPersistenceContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomGraphRepositoryTest {

    @Autowired
    private UserRepository repository;

    /**
     * asserts that the correct proxied object is created by Spring
     * and that we can integrate with it.
     */
    @Test
    public void shouldExposeCommonMethodOnExtendedRepository() {
        assertTrue(repository.sharedCustomMethod());
    }

}
