package com.sample.restaurant.search.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a restaurant entity in the system.
 * This class is used to define the properties of a restaurant.
 */
@Document(collection = "restaurants")
public class Restaurant {
    @Id
    private String id;
    private String name;
    private String type;
    private String openingHours;
    private String image;
    private int radius;
    private Coordinates coordinates;

    public Restaurant() {
        // Default constructor for MongoDB
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Restaurant restaurant = new Restaurant();

        public Builder id(String id) {
            restaurant.id = id;
            return this;
        }

        public Builder name(String name) {
            restaurant.name = name;
            return this;
        }

        public Builder type(String type) {
            restaurant.type = type;
            return this;
        }

        public Builder openingHours(String openingHours) {
            restaurant.openingHours = openingHours;
            return this;
        }

        public Builder image(String image) {
            restaurant.image = image;
            return this;
        }

        public Builder radius(int radius) {
            restaurant.radius = radius;
            return this;
        }

        public Builder coordinates(Coordinates coordinates) {
            restaurant.coordinates = coordinates;
            return this;
        }

        public Restaurant build() {
            return restaurant;
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
