package com.eliasfs06.product.service.model.enums;

public enum UserRole {

    ADMIN("Admin"),
    MANAGER("Manager"),
    CONSUMER("Consumer");

    private String description;

    UserRole(String description) {
        this.description = description;
    }


    public String getDescription(){
        return this.description;
    }
}
