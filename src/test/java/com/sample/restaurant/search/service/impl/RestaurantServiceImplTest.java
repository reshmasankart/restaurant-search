package com.sample.restaurant.search.service.impl;

import com.sample.restaurant.search.dto.LocationDto;
import com.sample.restaurant.search.dto.RestaurantDetailsResponseDto;
import com.sample.restaurant.search.dto.SearchResponseDto;
import com.sample.restaurant.search.exception.RestaurantNotFoundException;
import com.sample.restaurant.search.mapper.RequestMapper;
import com.sample.restaurant.search.mapper.ResponseMapper;
import com.sample.restaurant.search.model.Coordinates;
import com.sample.restaurant.search.model.Restaurant;
import com.sample.restaurant.search.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ResponseMapper responseMapper;
    @Mock
    private RequestMapper requestMapper;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Test
    void testFindRestaurantById_found() {
        String id = UUID.randomUUID().toString();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("Test Restaurant");
        restaurant.setType("Restaurant");
        RestaurantDetailsResponseDto dto = new RestaurantDetailsResponseDto();

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));
        when(responseMapper.toDto(restaurant)).thenReturn(dto);

        RestaurantDetailsResponseDto result = restaurantService.findRestaurantById(id);

        assertNotNull(result);
        verify(restaurantRepository).findById(id);
        verify(responseMapper).toDto(restaurant);
        assertEquals(dto, result);
    }

    @Test
    void testFindRestaurantById_NotFound() {
        String id = UUID.randomUUID().toString();
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.findRestaurantById(id);
        });

        verify(restaurantRepository).findById(id);
        verifyNoInteractions(responseMapper); // MAPPER should not be called
    }


    @Test
    void testRestaurantLocator_found() {
        int x = 10;
        int y = 20;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID().toString());
        restaurant.setName("Test Restaurant");
        restaurant.setType("Restaurant");
        restaurant.setCoordinates(new Coordinates(x, y));

        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        when(responseMapper.toSearchResponse(List.of(restaurant), x, y)).thenReturn(new SearchResponseDto("x=10,y=20", List.of(new LocationDto())));

        SearchResponseDto response = restaurantService.restaurantLocator(x, y);

        assertNotNull(response);
        assertEquals(String.format("x=%d,y=%d", x, y), response.getUserLocation());
        assertEquals(1, response.getLocations().size());
        verify(responseMapper).toSearchResponse(anyList(), eq(x), eq(y));
        verify(restaurantRepository).findAll();

    }

     @Test
    void testAddOrUpdateRestaurant_success() {
        RestaurantDetailsResponseDto dto = new RestaurantDetailsResponseDto();
        var id = UUID.randomUUID();
        dto.setId(id);
        dto.setName("New Restaurant");
        dto.setType("Restaurant");
        dto.setCoordinates("x=2,y=5");

        Restaurant restaurant = new Restaurant.Builder()
                .id(dto.getId().toString())
                .name(dto.getName())
                .type(dto.getType())
                .coordinates(stringToCoordinates(dto.getCoordinates()))
                .build();

        when(requestMapper.toEntity(any())).thenReturn(restaurant);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
        when(responseMapper.toDto(any())).thenReturn(dto);

        RestaurantDetailsResponseDto result = restaurantService.addOrUpdateRestaurant(id.toString(),dto);
        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        verify(requestMapper).toEntity(dto);
        verify(restaurantRepository).save(restaurant);
    }

    private Coordinates stringToCoordinates(String coordinates) {
        String[] parts = coordinates.split(",");
        int x = Integer.parseInt(parts[0].split("=")[1]);
        int y = Integer.parseInt(parts[1].split("=")[1]);
        return new Coordinates(x, y);
    }
}
