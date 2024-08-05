package com.eliasfs06.stock.manager.frontend.model.dto;

public class ProductConsumptionDto  {

    private Long id;
    private ProductDto productDto;
    private Integer quantity;

    public ProductConsumptionDto(ProductDto productDto, Integer quantity) {
        this.productDto = productDto;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDto getProduct() {
        return productDto;
    }

    public void setProduct(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
