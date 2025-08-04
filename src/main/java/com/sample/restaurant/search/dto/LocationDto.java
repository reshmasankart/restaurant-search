package com.sample.restaurant.search.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

/**
 * Data Transfer Object for representing a restaurant location.
 * This class is used to transfer location details
 */
public class LocationDto {

    @NotBlank(message = "Id is required")
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Coordinates are required")
    @Pattern(regexp = "^x=\\d+,y=\\d+$", message = "Coordinates must be in format x=3,y=2")
    private String coordinates;

    @PositiveOrZero(message = "Distance must be zero or positive")
    private double distance;

    public LocationDto() {
        // Default constructor for deserialization
    }

    public LocationDto(UUID id, String name, String coordinates, double distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.distance = distance;
    }

    public @NotBlank(message = "Coordinates are required") @Pattern(regexp = "^x=\\d+,y=\\d+$", message = "Coordinates must be in format x=3,y=2") String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@NotBlank(message = "Coordinates are required") @Pattern(regexp = "^x=\\d+,y=\\d+$", message = "Coordinates must be in format x=3,y=2") String coordinates) {
        this.coordinates = coordinates;
    }

    @PositiveOrZero(message = "Distance must be zero or positive")
    public double getDistance() {
        return distance;
    }

    public void setDistance(@PositiveOrZero(message = "Distance must be zero or positive") double distance) {
        this.distance = distance;
    }

    public @NotBlank(message = "Id is required") UUID getId() {
        return id;
    }

    public void setId(@NotBlank(message = "Id is required") UUID id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }
}

