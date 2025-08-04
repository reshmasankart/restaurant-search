package com.sample.restaurant.search.dto;

import java.util.List;

/**
 * Data Transfer Object for the search response containing user location and a list of restaurant locations.
 * This class is used to transfer search results in response to a user's location query.
 */
public class SearchResponseDto {
    private String userLocation;
    private List<LocationDto> locations;

    public SearchResponseDto(String userLocation, List<LocationDto> locations) {
        this.userLocation = userLocation;
        this.locations = locations;
    }

    public SearchResponseDto() {
    }

    public List<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDto> locations) {
        this.locations = locations;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }
}
