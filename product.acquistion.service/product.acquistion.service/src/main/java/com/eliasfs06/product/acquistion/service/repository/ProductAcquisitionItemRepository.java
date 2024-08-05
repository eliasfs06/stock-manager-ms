package com.eliasfs06.product.acquistion.service.repository;

import com.eliasfs06.product.acquistion.service.model.ProductAcquisitionItem;
import com.eliasfs06.product.acquistion.service.repository.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAcquisitionItemRepository extends GenericRepository<ProductAcquisitionItem> {

    @Query("FROM ProductAcquisitionItem item WHERE item.productAcquisition.id = ?1 " +
            "AND item.productAcquisition.active = true AND item.active = true")
    public List<ProductAcquisitionItem> findByProductAcquisition(Long id);
}
