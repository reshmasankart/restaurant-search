package com.sample.restaurant.search.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


import java.util.UUID;

/**
 * Data Transfer Object for restaurant details response.
 * This class is used to transfer detailed information about a restaurant in response.
 */
public class RestaurantDetailsResponseDto {

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    @NotNull
    private UUID id;

    @JsonProperty("opening-hours")
    @NotBlank
    private String openingHours;

    @NotBlank
    private String image;

    @Min(value = 1, message = "Radius must be at least 1")
    private int radius;

    @NotBlank
    @Pattern(regexp = "^x=\\d+,y=\\d+$", message = "Coordinates must be in format x=3,y=2")
    private String coordinates;

    public @NotBlank @Pattern(regexp = "^x=\\d+,y=\\d+$", message = "Coordinates must be in format x=3,y=2") String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@NotBlank @Pattern(regexp = "^x=\\d+,y=\\d+$", message = "Coordinates must be in format x=3,y=2") String coordinates) {
        this.coordinates = coordinates;
    }

    public @NotNull UUID getId() {
        return id;
    }

    public void setId(@NotNull UUID id) {
        this.id = id;
    }

    public @NotBlank String getImage() {
        return image;
    }

    public void setImage(@NotBlank String image) {
        this.image = image;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(@NotBlank String openingHours) {
        this.openingHours = openingHours;
    }

    @Min(value = 1, message = "Radius must be at least 1")
    public int getRadius() {
        return radius;
    }

    public void setRadius(@Min(value = 1, message = "Radius must be at least 1") int radius) {
        this.radius = radius;
    }

    public @NotBlank String getType() {
        return type;
    }

    public void setType(@NotBlank String type) {
        this.type = type;
    }
}
