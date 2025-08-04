package com.sample.restaurant.search.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class RestaurantConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    /**
     * MongoDB client configuration.
     * This bean creates a MongoClient instance using the URI specified in application properties.
     * It is used to connect to the MongoDB database for storing and retrieving restaurant data.
     */
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "restaurantdb");
    }

    /**
     * OpenAPI configuration for the Restaurant API.
     * This bean provides metadata about the API, such as title, version, and description.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restaurant API")
                        .version("1.0")
                        .description("API for finding and managing nearby restaurants"));
    }
}
