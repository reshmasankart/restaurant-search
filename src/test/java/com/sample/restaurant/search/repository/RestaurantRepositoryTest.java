package com.sample.restaurant.search.repository;

import com.sample.restaurant.search.model.Coordinates;
import com.sample.restaurant.search.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantRepositoryTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Test
    void shouldSaveAndFindRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("1");
        restaurant.setName("Testaurant");
        restaurant.setCoordinates(new Coordinates(1, 2));

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantRepository.findById("1")).thenReturn(Optional.of(restaurant));

        Restaurant saved = restaurantRepository.save(restaurant);
        Optional<Restaurant> found = restaurantRepository.findById("1");

        assertThat(saved.getName()).isEqualTo("Testaurant");
        assertThat(found).isPresent();
        assertThat(found.get().getCoordinates().getX()).isEqualTo(1);
        assertThat(found.get().getCoordinates().getY()).isEqualTo(2);

        verify(restaurantRepository).save(restaurant);
        verify(restaurantRepository).findById("1");
    }
}