package org.neo4j.cineasts.integrationtests;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertThat;

@Ignore("slow test")
public class DatabasePopulationIT {
    @Autowired RestTemplate rest;

    @Test
    public void shouldPopulateDatabase() throws Exception {
        String result = rest.getForObject("http://localhost:8080/populate", String.class);

        assertThat(result, Matchers.containsString("Neo"));
    }
}
