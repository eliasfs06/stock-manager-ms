package com.eliasfs06.product.service.service;

import com.eliasfs06.product.service.exceptionsHandler.BusinessException;
import com.eliasfs06.product.service.model.Product;
import com.eliasfs06.product.service.repository.ProductRepository;
import com.eliasfs06.product.service.service.helper.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductService extends GenericService<Product> {

    @Autowired
    private ProductRepository repository;

    public Page<Product> getPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> list;
        List<Product> products = repository.findAll();

        if (products.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            list = products.subList(startItem, toIndex);
        }

        return new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());
    }

    public List<Product> findAll(){
        return repository.findAll();
    }

    public void validateProduct(Product product) throws BusinessException {
        if(product.getName() == null || product.getName().isEmpty() ||
                product.getProductType() == null ||  product.getBrand() == null){
            throw new BusinessException(MessageCode.DEFAULT_EMPTY_FIELD_MSG);
        }
    }
}
