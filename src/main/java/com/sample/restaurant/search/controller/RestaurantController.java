package com.sample.restaurant.search.controller;

import com.sample.restaurant.search.dto.RestaurantDetailsResponseDto;
import com.sample.restaurant.search.dto.SearchResponseDto;
import com.sample.restaurant.search.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing restaurant locations.
 * Provides endpoints to search for nearby restaurants and manage restaurant details.
 */
@RestController
@Validated
@RequestMapping("/locations")
@Tag(name = "Restaurant Locator API", description = "Operations for locating restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * Searches for restaurants near the specified coordinates.
     *
     * @param x the x-coordinate (longitude)
     * @param y the y-coordinate (latitude)
     * @return a SearchResponseDto containing the list of nearby restaurants
     */

    @GetMapping("/search")
    @Operation(summary = "Find nearby restaurants by coordinates")
    public ResponseEntity<SearchResponseDto> searchVisibleRestaurants(
            @Parameter(description = "X coordinate (longitude)") @RequestParam @Min(value = 0, message = "x must be a non-negative integer") final int x,
            @Parameter(description = "Y coordinate (latitude)") @RequestParam @Min(value = 0, message = "y must be a non-negative integer") final int y
    ) {
        return ResponseEntity.ok(restaurantService.restaurantLocator(x, y));
    }

    /**
     * Retrieves restaurant details by ID.
     *
     * @param id the unique identifier of the restaurant
     * @return a RestaurantDetailsResponseDto containing the restaurant details
     */

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant details by ID")
    public ResponseEntity<RestaurantDetailsResponseDto> getById(
            @Parameter(description = "Restaurant ID", required = true)
            @PathVariable final String id) {
        return ResponseEntity.ok(restaurantService.findRestaurantById(id));
    }

    /**
     * Adds or updates a restaurant by ID.
     *
     * @param id the unique identifier of the restaurant
     * @param restaurantDto the details of the restaurant to add or update
     * @return the updated or created RestaurantDetailsResponseDto
     */
    @PutMapping("/{id}")
    @Operation(summary = "Add or update a restaurant by ID")
    public ResponseEntity<RestaurantDetailsResponseDto> addOrUpdate(
            @Parameter(description = "Restaurant ID", required = true)
            @PathVariable final String id,
            @Valid @RequestBody final RestaurantDetailsResponseDto restaurantDto) {
       return ResponseEntity.ok(restaurantService.addOrUpdateRestaurant(id, restaurantDto));
    }
}