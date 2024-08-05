package com.eliasfs06.stock.manager.frontend.model.dto;

import com.eliasfs06.stock.manager.frontend.model.enums.RequestStatus;

import java.util.Date;

public class ProductConsumptionRequestDto {

    private Long id;
    private ProductDto productDto;
    private Integer quantity;
    private String description;
    private Date requestDate;
    private RequestStatus requestStatus;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public static class ProductAcquisitionDtoItemDto {

        private Long id;
        private ProductDto productDto;
        private Integer quantity;
        private ProductAcquisitionDto productAcquisition;

        public ProductAcquisitionDtoItemDto() {
        }

        public ProductAcquisitionDtoItemDto(ProductDto productDto, Integer quantity) {
            this.productDto = productDto;
            this.quantity = quantity;
        }

        public ProductAcquisitionDtoItemDto(ProductAcquisitionDto productAcquisition) {
            this.productAcquisition = productAcquisition;
        }

        public ProductAcquisitionDtoItemDto(ProductDto productDto, Integer quantity, ProductAcquisitionDto productAcquisition) {
            this.productDto = productDto;
            this.quantity = quantity;
            this.productAcquisition = productAcquisition;
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

        public ProductAcquisitionDto getProductAcquisitionDto() {
            return productAcquisition;
        }

        public void setProductAcquisitionDto(ProductAcquisitionDto productAcquisition) {
            this.productAcquisition = productAcquisition;
        }
    }
}
