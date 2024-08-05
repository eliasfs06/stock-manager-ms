package com.eliasfs06.stock.manager.frontend.model.dto;

import com.eliasfs06.stock.manager.frontend.model.enums.Brand;
import com.eliasfs06.stock.manager.frontend.model.enums.ProductType;
import jakarta.persistence.Enumerated;

public class ProductDto {
    private Long id;

    private String name;

    private Brand brand;

    private ProductType productType;

    private String description;

    private Integer stockQuantity;

    private Boolean freeToConsume = true;

    public ProductDto() {
    }

    public ProductDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getFreeToConsume() {
        return freeToConsume;
    }

    public void setFreeToConsume(Boolean freeToConsume) {
        this.freeToConsume = freeToConsume;
    }
}
