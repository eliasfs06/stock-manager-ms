package com.eliasfs06.product.service.model;

import com.eliasfs06.product.service.model.enums.Brand;
import com.eliasfs06.product.service.model.enums.ProductType;
import jakarta.persistence.*;

@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private String description;

    private Integer stockQuantity;

    private Boolean freeToConsume = true;

    public Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    @Override
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
