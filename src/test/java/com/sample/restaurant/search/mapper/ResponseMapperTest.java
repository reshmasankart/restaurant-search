package com.sample.restaurant.search.mapper;

import com.sample.restaurant.search.dto.LocationDto;
import com.sample.restaurant.search.dto.RestaurantDetailsResponseDto;
import com.sample.restaurant.search.model.Coordinates;
import com.sample.restaurant.search.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ResponseMapperTest {


    private ResponseMapper responseMapper;

    @BeforeEach
    void setUp() {
        // Initialize the mapper using MapStruct's factory method
        responseMapper = Mappers.getMapper(ResponseMapper.class);
    }

    @Test
    void testToDto() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID().toString());
        restaurant.setName("Indian Spice");
        restaurant.setRadius(3);
        restaurant.setOpeningHours("9-5");
        restaurant.setType("Restaurant");
        restaurant.setCoordinates(new Coordinates(5, 10));

        RestaurantDetailsResponseDto dto = responseMapper.toDto(restaurant);

        assertNotNull(dto);
        assertEquals("x=5,y=10", dto.getCoordinates());
        assertEquals(restaurant.getId(), dto.getId().toString());
    }

    @Test
    void testToLocation() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID().toString());
        restaurant.setName("Indian Spice");
        restaurant.setCoordinates(new Coordinates(0, 4));
        restaurant.setRadius(5);
        restaurant.setType("Restaurant");

        LocationDto dto = responseMapper.toLocation(restaurant, 0, 0);

        assertNotNull(dto);
        assertEquals("x=0,y=4", dto.getCoordinates());
        assertEquals(4.0, dto.getDistance());
    }

    @Test
    void testToLocationsSortedByDistance() {
        Restaurant r1 = new Restaurant();
        r1.setId(UUID.randomUUID().toString());
        r1.setCoordinates(new Coordinates(3, 4)); // Distance 5

        Restaurant r2 = new Restaurant();
        r2.setId(UUID.randomUUID().toString());
        r2.setCoordinates(new Coordinates(0, 1)); // Distance 1

        List<LocationDto> locations = responseMapper.toLocations(List.of(r1, r2), 0, 0);

        assertEquals(2, locations.size());
        assertTrue(locations.get(0).getDistance() < locations.get(1).getDistance());
    }



}
