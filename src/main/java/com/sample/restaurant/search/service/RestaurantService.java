package com.sample.restaurant.search.service;

import com.sample.restaurant.search.dto.RestaurantDetailsResponseDto;
import com.sample.restaurant.search.dto.SearchResponseDto;
import jakarta.validation.Valid;


public interface RestaurantService {

    SearchResponseDto restaurantLocator(int x, int y);
    RestaurantDetailsResponseDto findRestaurantById(String id);
    RestaurantDetailsResponseDto addOrUpdateRestaurant(String id, @Valid RestaurantDetailsResponseDto restaurantDto);
}
