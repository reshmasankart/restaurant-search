package com.sample.restaurant.search.mapper;

import com.sample.restaurant.search.dto.RestaurantDetailsResponseDto;
import com.sample.restaurant.search.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension .class)
class RequestMapperTest {


   private RequestMapper requestMapper;

    @BeforeEach
    void setUp() {
        // Initialize the mapper using MapStruct's factory method
        requestMapper = Mappers.getMapper(RequestMapper.class);
    }

     @Test
    void testToEntity() {
        RestaurantDetailsResponseDto dto = new RestaurantDetailsResponseDto();
        UUID uuid = UUID.randomUUID();
        dto.setId(uuid);
        dto.setCoordinates("x=3,y=7");
        dto.setName("Indian Spice");
        dto.setOpeningHours("10AM-22PM");
        dto.setType("Restaurant");
        dto.setRadius(2);

        Restaurant entity = requestMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Indian Spice", entity.getName());
        assertEquals(3, entity.getCoordinates().getX());
        assertEquals(7, entity.getCoordinates().getY());
        assertEquals(uuid.toString(), entity.getId());
    }


    @Test
    void testStringToCoordinates_InvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            requestMapper.stringToCoordinates("invalid");
        });
    }


}

