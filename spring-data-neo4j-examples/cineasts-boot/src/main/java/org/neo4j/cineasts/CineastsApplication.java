package org.neo4j.cineasts;

import org.neo4j.cineasts.domain.Movie;
import org.neo4j.cineasts.movieimport.MovieDbApiClient;
import org.neo4j.cineasts.movieimport.MovieDbImportService;
import org.neo4j.cineasts.movieimport.MovieDbLocalStorage;
import org.neo4j.cineasts.repository.MovieRepository;
import org.neo4j.cineasts.service.DatabasePopulator;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@EnableNeo4jRepositories(basePackageClasses = MovieRepository.class)
@EnableTransactionManagement
//@Import(RepositoryRestMvcConfiguration.class)
@Configuration
@EnableAutoConfiguration
//@EnableWebMvcSecurity
@ComponentScan(basePackageClasses = {DatabasePopulator.class, MovieDbImportService.class})
public class CineastsApplication extends Neo4jConfiguration {

    private static final String DB_PATH = "data/graph.db";
    public static final String CACHE_PATH = "data/json";
    private static final String DEFAULT_KEY = "926d2a79e82920b62f03b1cb57e532e6";

    public CineastsApplication() {
        setBasePackage(Movie.class.getPackage().getName());
    }


    @Bean
    public MovieDbApiClient movieDbApiClient() {
        return new MovieDbApiClient(System.getProperty("themoviedb.key",DEFAULT_KEY));
    }
    @Bean
    public MovieDbLocalStorage movieDbLocalStorage() {
        return new MovieDbLocalStorage(System.getProperty("cineasts.cache", CACHE_PATH));
    }

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CineastsApplication.class, args);
    }

}
