package com.eliasfs06.stock.manager.frontend.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProductType {
    HYGIENE("Hygiene"),
    OFFICE_SUPPLIES("Office Supplies"),
    ELECTRONICS("Electronics"),
    BEAUTY("Beauty"),
    HOUSEHOLD("Household"),
    CLOTHING("Clothing"),
    SPORTS("Sports"),
    FOOD("Food"),
    TOYS("Toys"),
    BOOKS("Books"),
    FURNITURE("Furniture"),
    JEWELRY("Jewelry"),
    AUTOMOTIVE("Automotive"),
    PET_SUPPLIES("Pet Supplies"),
    HEALTH("Health"),
    BABY("Baby"),
    MUSIC("Music"),
    ART("Art"),
    GARDEN("Garden"),
    CRAFTS("Crafts");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static ProductType fromValue(String value) {
        for (ProductType type : ProductType.values()) {
            if (type.description.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }
}

