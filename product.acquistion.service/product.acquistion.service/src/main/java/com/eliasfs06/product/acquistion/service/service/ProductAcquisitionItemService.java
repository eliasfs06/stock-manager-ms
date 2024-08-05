package com.eliasfs06.product.acquistion.service.service;

import com.eliasfs06.product.acquistion.service.model.Product;
import com.eliasfs06.product.acquistion.service.model.ProductAcquisitionItem;
import com.eliasfs06.product.acquistion.service.repository.ProductAcquisitionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductAcquisitionItemService extends GenericService<ProductAcquisitionItem> {

    @Value("${gateway.host}")
    private String gateway;

    @Value("${product.service.path}")
    private String productServicePath;

    @Autowired
    private ProductAcquisitionItemRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public List<ProductAcquisitionItem> findByProductAcquisition(Long id) {
        return repository.findByProductAcquisition(id);
    }

    public ProductAcquisitionItem save(ProductAcquisitionItem productAcquisitionItem) {
        Product product = productAcquisitionItem.getProduct();
        product.setStockQuantity(product.getStockQuantity() == null ? productAcquisitionItem.getQuantity() : product.getStockQuantity() + productAcquisitionItem.getQuantity());
        saveProduct(product);
        return repository.save(productAcquisitionItem);
    }

    public void deleteItem(Long id) {
        ProductAcquisitionItem productAcquisitionItem = get(id);
        Product product = productAcquisitionItem.getProduct();
        product.setStockQuantity(product.getStockQuantity() - productAcquisitionItem.getQuantity());
        saveProduct(product);
        repository.save(productAcquisitionItem);
    }

    public void saveProduct(Product product){
        String path = gateway + productServicePath + "/save";
        restTemplate.postForObject(path, product, Product.class);
    }

}