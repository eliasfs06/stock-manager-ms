package com.eliasfs06.stock.manager.frontend.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

 public class ProductAcquisitionDto {
 
    private Long id;
    private Date aquisitionDate;
    private List<ProductAcquisitionItemDTO> itens = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAquisitionDate() {
        return aquisitionDate;
    }

    public void setAquisitionDate(Date aquisitionDate) {
        this.aquisitionDate = aquisitionDate;
    }

     public List<ProductAcquisitionItemDTO> getItens() {
         return itens;
     }

     public void setItens(List<ProductAcquisitionItemDTO> itens) {
         this.itens = itens;
     }
 }
