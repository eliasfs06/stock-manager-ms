package com.eliasfs06.product.acquistion.service.model.dto;

import com.eliasfs06.product.acquistion.service.model.Product;

public class ProductAcquisitionItemDTO {
    private Integer productId;
    private Integer quantity;
    private Product product;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
