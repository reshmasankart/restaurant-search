package com.sample.restaurant.search.mapper;

import com.sample.restaurant.search.dto.RestaurantDetailsResponseDto;
import com.sample.restaurant.search.model.Coordinates;
import com.sample.restaurant.search.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Mapper interface for converting between RestaurantDetailsResponseDto and Restaurant entity.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface RequestMapper {


    @Mapping(source = "coordinates", target = "coordinates", qualifiedByName = "stringToCoordinates")
    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    Restaurant toEntity(RestaurantDetailsResponseDto dto);

    @Named("uuidToString")
    default String uuidToString(UUID id) {
        return id != null ? id.toString() : null;
    }


    @Named("stringToCoordinates")
    default Coordinates stringToCoordinates(String coordinates) {
        if (coordinates == null || !coordinates.matches("^x=\\d+,y=\\d+$")) {
            throw new IllegalArgumentException("Invalid coordinates format");
        }
        String[] parts = coordinates.split(",");
        int x = Integer.parseInt(parts[0].split("=")[1]);
        int y = Integer.parseInt(parts[1].split("=")[1]);
        return new Coordinates(x, y);
    }
}
