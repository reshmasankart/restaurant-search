package com.sample.restaurant.search;

import com.sample.restaurant.search.model.Coordinates;
import com.sample.restaurant.search.model.Restaurant;
import com.sample.restaurant.search.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantSearchApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private RestaurantRepository restaurantRepository;

	@BeforeEach
	void setup() {
		Restaurant mockRestaurant = new Restaurant();
		mockRestaurant.setName("Mantra Restaurant");
		mockRestaurant.setId("51e1545c-8b65-4d83-82f9-7fcad4a23111");
		mockRestaurant.setCoordinates(new Coordinates(2, 2));
		mockRestaurant.setRadius(1);

		when(restaurantRepository.findAll()).thenReturn(List.of(mockRestaurant));
		when(restaurantRepository.findById("51e1545c-8b65-4d83-82f9-7fcad4a23111"))
				.thenReturn(java.util.Optional.of(mockRestaurant));
		when(restaurantRepository.save(any(Restaurant.class))).thenReturn(mockRestaurant);

	}

	@Test
	void testSearchRestaurantsApi() throws Exception {
		mockMvc.perform(get("/locations/search")
						.param("x", "3")
						.param("y", "2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userLocation").value("x=3,y=2"))
				.andExpect(jsonPath("$.locations").isArray())
				.andExpect(jsonPath("$.locations[0].name").value("Mantra Restaurant"))
				.andExpect(jsonPath("$.locations[0].coordinates").value("x=2,y=2"))
				.andExpect(jsonPath("$.locations[0].distance").value(1.0));
	}

	@Test
	void testGetRestaurantById() throws Exception {
		// Use a valid restaurant ID from your dataset
		String restaurantId = "51e1545c-8b65-4d83-82f9-7fcad4a23111";

		mockMvc.perform(get("/locations/{id}", restaurantId)
						.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(restaurantId))
				.andExpect(jsonPath("$.coordinates").exists())
				.andExpect(jsonPath("$.name").exists()); // assuming name is in the response
	}

	@Test
	void testAddOrUpdateRestaurant() throws Exception {
		var restaurantId = "51e1545c-8b65-4d83-82f9-7fcad4a23111";
		String newRestaurantJson = """
				{
				    "name": "Da Jia Le",
				    "type": "Restaurant",
				    "id": "51e1545c-8b65-4d83-82f9-7fcad4a23111",
				    "opening-hours": "10:00AM-11:00PM",
				    "image": "https://tinyurl.com",
				    "coordinates": "x=5,y=5",
				    "radius": 1
				}
			""";

		mockMvc.perform(put("/locations/{id}", restaurantId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(newRestaurantJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Da Jia Le"))
				.andExpect(jsonPath("$.id").value(restaurantId))
				.andExpect(jsonPath("$.coordinates").value("x=5,y=5"));

	}


}
