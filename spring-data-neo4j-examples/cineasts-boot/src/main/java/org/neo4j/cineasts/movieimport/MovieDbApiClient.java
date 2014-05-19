package org.neo4j.cineasts.movieimport;

import org.codehaus.jackson.map.ObjectMapper;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

public class MovieDbApiClient {

    private final String baseUrl = "https://api.themoviedb.org/";
    private final String apiKey;
    protected final ObjectMapper mapper;
    private String baseImageUrl;

    public MovieDbApiClient(String apiKey) {
        this.apiKey = apiKey;
        mapper = new ObjectMapper();
    }
    
    private Map getConfiguration() {
    	return loadJsonData(buildConfigurationUrl());
    }
    
    public String getBaseImageUrl() {
    	if (baseImageUrl == null) {
    		Map config = getConfiguration();
    		Map imageConfig = (Map) config.get("images");
    		baseImageUrl = (String) imageConfig.get("base_url");
    	}
    	return baseImageUrl;
    }

    public Map getMovie(String id) {
        return loadJsonData(buildMovieUrl(id));
    }

    private Map loadJsonData(String url) {
        try {
            Map value = mapper.readValue(new URL(url), Map.class);
            if (value == null) return Collections.singletonMap("not_found",System.currentTimeMillis());
            return (Map) value;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get data from " + url, e);
        }
    }
    
    private String buildConfigurationUrl() {
    	return String.format("%s3/configuration?api_key=%s", baseUrl, apiKey);
    }

    private String buildMovieUrl(String movieId) {
        return String.format("%s3/movie/%s?api_key=%s&append_to_response=casts,trailers", baseUrl, movieId, apiKey);
    }

    public Map getPerson(String id) {
        return loadJsonData(buildPersonUrl(id));
    }

    private String buildPersonUrl(String personId) {
        return String.format("%s3/person/%s?api_key=%s", baseUrl, personId, apiKey);
    }
}
