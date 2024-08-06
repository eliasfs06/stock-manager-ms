package com.eliasfs06.product.acquistion.service.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductAcquisitionItemListResponseDTO {

    private List<ProductAcquisitionItemResponseDTO> productAcquisitionItens = new ArrayList<>();

    public List<ProductAcquisitionItemResponseDTO> getProductAcquisitionItens() {
        return productAcquisitionItens;
    }

    public void setProductAcquisitionItens(List<ProductAcquisitionItemResponseDTO> productAcquisitionItens) {
        this.productAcquisitionItens = productAcquisitionItens;
    }
}
