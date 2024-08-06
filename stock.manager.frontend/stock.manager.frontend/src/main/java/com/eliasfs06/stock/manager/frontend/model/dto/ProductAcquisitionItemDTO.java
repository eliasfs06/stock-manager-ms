package com.eliasfs06.stock.manager.frontend.model.dto;

public class ProductAcquisitionItemDTO {
    private Integer productId;
    private Integer quantity;
    private ProductDto product;

    public ProductAcquisitionItemDTO(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductAcquisitionItemDTO() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }
}
