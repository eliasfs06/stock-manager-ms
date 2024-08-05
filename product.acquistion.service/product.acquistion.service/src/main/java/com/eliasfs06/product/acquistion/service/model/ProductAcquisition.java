package com.eliasfs06.product.acquistion.service.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ProductAcquisition extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "MM-DD-YYYY")
    private Date aquisitionDate;

    @OneToMany(mappedBy = "productAcquisition", cascade = CascadeType.ALL)
    private List<ProductAcquisitionItem> itens = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getAquisitionDate() {
        return aquisitionDate;
    }

    public void setAquisitionDate(Date aquisitionDate) {
        this.aquisitionDate = aquisitionDate;
    }

    public List<ProductAcquisitionItem> getItens() {
        return itens;
    }

    public void setItens(List<ProductAcquisitionItem> itens) {
        this.itens = itens;
    }

}
