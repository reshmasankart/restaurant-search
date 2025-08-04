package com.sample.restaurant.search.repository;

import com.sample.restaurant.search.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Restaurant entities in MongoDB.
 * This interface extends MongoRepository to provide CRUD operations for Restaurant objects.
 */
@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
}