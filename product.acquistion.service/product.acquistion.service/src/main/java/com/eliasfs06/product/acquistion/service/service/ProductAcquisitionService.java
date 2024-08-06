package com.eliasfs06.product.acquistion.service.service;

import com.eliasfs06.product.acquistion.service.model.Product;
import com.eliasfs06.product.acquistion.service.model.ProductAcquisition;
import com.eliasfs06.product.acquistion.service.model.ProductAcquisitionItem;
import com.eliasfs06.product.acquistion.service.model.dto.*;
import com.eliasfs06.product.acquistion.service.model.exceptionsHandler.BusinessException;
import com.eliasfs06.product.acquistion.service.repository.ProductAcquisitionRepository;
import com.eliasfs06.product.acquistion.service.service.helper.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ProductAcquisitionService extends GenericService<ProductAcquisition> {

    @Value("${gateway.host}")
    private String gateway;

    @Value("${product.service.path}")
    private String productServicePath;

    @Autowired
    private ProductAcquisitionRepository repository;

    @Autowired
    private ProductAcquisitionItemService productAcquisitionItemService;

    @Autowired
    private RestTemplate restTemplate;

    public Page<ProductAcquisitionDto> getPageResponse(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductAcquisitionItemDTO> listItens = new ArrayList<>();
        List<ProductAcquisitionDto> list = new ArrayList<>();
        List<ProductAcquisition> productAcquisitions = this.findAll();

        for(ProductAcquisition productAcquisition : productAcquisitions){
            List<ProductAcquisitionItem> itens = productAcquisitionItemService.findByProductAcquisition(productAcquisition.getId());
            for(ProductAcquisitionItem item : itens){
                ProductAcquisitionItemDTO itemDto = new ProductAcquisitionItemDTO();
                itemDto.setProductId(Math.toIntExact(item.getProduct().getId()));
                itemDto.setQuantity(item.getQuantity());
                itemDto.setProduct(item.getProduct());
                listItens.add(itemDto);
            }
            ProductAcquisitionDto productAcquisitionDto = new ProductAcquisitionDto();
            productAcquisitionDto.setAquisitionDate(productAcquisition.getAquisitionDate());
            productAcquisitionDto.setItens(listItens);
            list.add(productAcquisitionDto);
        }

        if (list.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            list = list.subList(startItem, toIndex);
        }

        return new PageImpl<ProductAcquisitionDto>(list, PageRequest.of(currentPage, pageSize), list.size());
    }

    public List<ProductAcquisition> findAll(){
        return repository.findAll();
    }

    public ProductAcquisition save(List<ProductAcquisitionItemDTO> productAcquisitionItens) throws BusinessException {
        if(productAcquisitionItens.isEmpty()){
            throw new BusinessException(MessageCode.DEFAULT_EMPTY_FIELD_MSG);
        }

        ProductAcquisition productAcquisition = new ProductAcquisition();
        productAcquisition.setAquisitionDate(new Date());

        repository.save(productAcquisition);

        List<ProductAcquisitionItem> itens = new ArrayList<>();
        productAcquisitionItens.forEach(item -> {
            Product product = getProduct(Long.valueOf(item.getProductId()));
            ProductAcquisitionItem newItem = new ProductAcquisitionItem(product, item.getQuantity(), productAcquisition);
            productAcquisitionItemService.save(newItem);
            itens.add(newItem);
        });

        productAcquisition.setItens(itens);

        return productAcquisition;
    }

    public void deleteAcquisition(Long id) throws BusinessException {
        ProductAcquisition productAcquisition = get(id);

        verifyQuantityInStockToDelete(productAcquisition);

        for(int i = 0; i < productAcquisition.getItens().size(); i ++){
            ProductAcquisitionItem item = productAcquisition.getItens().get(i);
            productAcquisitionItemService.deleteItem(item.getId());
        }
        repository.deleteById(id);
    }

    private void verifyQuantityInStockToDelete(ProductAcquisition productAcquisition) throws BusinessException {
        Map<Product, Integer> totalPerProduct = new HashMap<>();

        for(int i = 0; i < productAcquisition.getItens().size(); i ++){
            ProductAcquisitionItem item = productAcquisition.getItens().get(i);
            int total = totalPerProduct.getOrDefault(item.getProduct(), 0);
            totalPerProduct.put(item.getProduct(), total + item.getQuantity());
        }

        for (Map.Entry<Product, Integer> entry : totalPerProduct.entrySet()) {
            Integer totalInStock = getProduct(entry.getKey().getId()).getStockQuantity();
            if(totalInStock == null || totalInStock < entry.getValue()){
                throw new BusinessException(MessageCode.CANT_DELETE_ACQUISITION);
            }
        }

    }

    public Product getProduct(Long id) {
        String path = gateway + productServicePath + "/get/" + id;
        return restTemplate.getForObject(path, Product.class);
    }

}
