package com.sample.restaurant.search.mapper;

import com.sample.restaurant.search.dto.LocationDto;
import com.sample.restaurant.search.dto.RestaurantDetailsResponseDto;
import com.sample.restaurant.search.dto.SearchResponseDto;
import com.sample.restaurant.search.model.Coordinates;
import com.sample.restaurant.search.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Mapper interface for converting between Restaurant entities and response DTOs(SearchResponseDto & RestaurantDetailsResponseDto).
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface ResponseMapper {

    @Mapping(source = "restaurant.id", target = "id", qualifiedByName = "stringToUUID")
    @Mapping(target = "coordinates", expression = "java(formatCoordinates(restaurant.getCoordinates()))")
    @Mapping(target = "distance", expression = "java(calculateDistance(restaurant.getCoordinates(), x, y))")
    LocationDto toLocation(Restaurant restaurant, int x, int y);

    @Mapping(source = "coordinates", target = "coordinates", qualifiedByName = "formatCoordinates")
    @Mapping(source = "id", target = "id", qualifiedByName = "stringToUUID")
    RestaurantDetailsResponseDto toDto(Restaurant restaurant);

    /**
     * Calculates the distance from the restaurant's coordinates to the given x and y coordinates.
     *
     * @param coordinates The restaurant's coordinates.
     * @param x           The x coordinate of the user's location.
     * @param y           The y coordinate of the user's location.
     * @return The calculated distance.
     */
    default double calculateDistance(Coordinates coordinates, int x, int y) {
        if (coordinates == null) {
            return Double.MAX_VALUE;
        }
        return Math.hypot((x - coordinates.getX()), (double)y - coordinates.getY());

    }

    default List<LocationDto> toLocations(List<Restaurant> restaurants, int x, int y) {
        return restaurants.stream()
                .map(r -> toLocation(r, x, y))
                .sorted(Comparator.comparingDouble(LocationDto::getDistance))
                .toList();
    }

    /**
     * Converts a list of Restaurant entities to a SearchResponseDto.
     *
     * @param restaurants The list of Restaurant entities.
     * @param x           The x coordinate of the user's location.
     * @param y           The y coordinate of the user's location.
     * @return A SearchResponseDto containing the user location and a list of LocationDto.
     */
    default SearchResponseDto toSearchResponse(List<Restaurant> restaurants, int x, int y) {
        String userLocation = String.format("x=%d,y=%d", x, y);
        List<LocationDto> locations = toLocations(restaurants, x, y);
        return new SearchResponseDto(userLocation, locations.isEmpty() ? List.of() : locations);
    }

    @Named("formatCoordinates")
    default String formatCoordinates(Coordinates coords) {
        return coords != null
                ? String.format("x=%d,y=%d", coords.getX(), coords.getY())
                : "x=0,y=0";
    }

    @Named("stringToUUID")
    default UUID stringToUUID(String id) {
        return id != null ? UUID.fromString(id) : null;
    }
}

