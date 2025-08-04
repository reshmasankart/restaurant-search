package com.sample.restaurant.search.service.impl;

import com.sample.restaurant.search.dto.RestaurantDetailsResponseDto;
import com.sample.restaurant.search.dto.SearchResponseDto;
import com.sample.restaurant.search.exception.InvalidInputException;
import com.sample.restaurant.search.exception.RestaurantNotFoundException;
import com.sample.restaurant.search.mapper.RequestMapper;
import com.sample.restaurant.search.mapper.ResponseMapper;
import com.sample.restaurant.search.model.Restaurant;
import com.sample.restaurant.search.repository.RestaurantRepository;
import com.sample.restaurant.search.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of RestaurantService providing restaurant location search and CRUD operations.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final RestaurantRepository restaurantRepository;
    private final ResponseMapper responseMapper;
    private final RequestMapper requestMapper;


    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ResponseMapper mapper, RequestMapper requestMapper) {
        this.restaurantRepository = restaurantRepository;
        this.responseMapper = mapper;
        this.requestMapper = requestMapper;
    }

    /**
     * Searches for restaurants near the specified coordinates.
     * The method caches the results to improve performance on subsequent requests.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return a SearchResponseDto containing the list of nearby restaurants
     */
    @Cacheable(value = "restaurantSearchCache", key = "T(java.lang.String).format('x=%d,y=%d', #x, #y)")
    @Override
    public SearchResponseDto restaurantLocator(int x, int y) {
        logger.debug("Searching for restaurants near coordinates: x={}, y={}", x, y);
        var restaurants = restaurantRepository.findAll().stream()
                .filter(restaurant -> isRestaurantNearby(restaurant, x, y))
                .toList();
        return responseMapper.toSearchResponse(restaurants, x, y);

    }

    /**
     * Finds a restaurant by its ID.
     * The method caches the result to improve performance on subsequent requests.
     *
     * @param id the ID of the restaurant to find
     * @return the details of the restaurant
     * @throws RestaurantNotFoundException if no restaurant with the given ID is found
     */
    @Cacheable(value = "restaurants", key = "#id")
    @Override
    public RestaurantDetailsResponseDto findRestaurantById(String id) {
        // Validate and parse the UUID from the String id before proceeding
        validateAndParseUUID(id);
        // Fetch the restaurant by ID
        logger.info("Fetching restaurant by ID: {} ", id);
        return restaurantRepository.findById(id)
                .map(responseMapper::toDto)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with ID " + id + " not found"));
    }

    /**
     * Adds or updates a restaurant.
     * If the restaurant with the given ID exists, it updates it; otherwise, it creates a new one.
     * The method also clears the cache for the restaurant search results.
     *
     * @param id the ID of the restaurant to add or update
     * @param restaurantDto the details of the restaurant to add or update
     * @return the added or updated restaurant details
     */
    @CachePut(value = "restaurants", key = "#id")
    @CacheEvict(value = "restaurantSearchCache", allEntries = true)
    @Override
    public RestaurantDetailsResponseDto addOrUpdateRestaurant(String id, RestaurantDetailsResponseDto restaurantDto) {
        // Convert String id to UUID and set in DTO
        UUID uuid = validateAndParseUUID(id);
        restaurantDto.setId(uuid);
        logger.info("Adding or updating restaurant with ID: {}", id);
        // Map DTO to entity
        var restaurantEntity = requestMapper.toEntity(restaurantDto);

        // Save (add or update)
        var resultEntity = restaurantRepository.save(restaurantEntity);
        logger.info("Restaurant with ID: {} updated", resultEntity.getId());
        return responseMapper.toDto(resultEntity);
    }

    private UUID validateAndParseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (InvalidInputException e) {
            throw new InvalidInputException("Invalid UUID format: " + id);
        }
    }

    /**
     * Checks if a restaurant is within the specified radius of the given coordinates.
     * @param restaurant- the restaurant to check
     * @param x the x-coordinate
     * @param y the y-coordinate
     * distance is calculated using the Euclidean distance formula.If the distance is less than or equal to the restaurant's radius, it returns true.
     * @return  true if the restaurant is within the radius, false otherwise
     */
    private boolean isRestaurantNearby(Restaurant restaurant, int x, int y) {
        int dx = restaurant.getCoordinates().getX() - (x);
        int dy = restaurant.getCoordinates().getY() - y;
        double distance = Math.sqrt((double)(dx * dx) + dy * dy);
        return (distance <= restaurant.getRadius());
    }


}
