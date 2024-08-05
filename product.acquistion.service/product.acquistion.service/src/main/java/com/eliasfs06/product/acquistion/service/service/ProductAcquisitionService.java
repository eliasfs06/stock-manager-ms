package com.eliasfs06.product.acquistion.service.service;

import com.eliasfs06.product.acquistion.service.model.Product;
import com.eliasfs06.product.acquistion.service.model.ProductAcquisition;
import com.eliasfs06.product.acquistion.service.model.ProductAcquisitionItem;
import com.eliasfs06.product.acquistion.service.model.dto.ProductAcquisitionItemDTO;
import com.eliasfs06.product.acquistion.service.model.dto.ProductAcquisitionItemListDTO;
import com.eliasfs06.product.acquistion.service.model.dto.ProductAcquisitionItemListResponseDTO;
import com.eliasfs06.product.acquistion.service.model.dto.ProductAcquisitionItemResponseDTO;
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

    public Page<ProductAcquisitionItemListResponseDTO> getPageResponse(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductAcquisitionItemListResponseDTO> list;
        List<ProductAcquisition> productAcquisitions = this.findAll();
        List<ProductAcquisitionItemListResponseDTO> productAcquisitionResponseList = new ArrayList<>();

        productAcquisitions.forEach(productAcquisition -> {
            productAcquisition.setItens(productAcquisitionItemService.findByProductAcquisition(productAcquisition.getId()));

            ProductAcquisitionItemListResponseDTO productAcquisitionResponse = new ProductAcquisitionItemListResponseDTO();

            productAcquisition.getItens().forEach(item -> {
                ProductAcquisitionItemResponseDTO itemDTO = new ProductAcquisitionItemResponseDTO(item.getProduct(), item.getQuantity());
                productAcquisitionResponse.getProductAcquisitionItens().add(itemDTO);
            });

            productAcquisitionResponseList.add(productAcquisitionResponse);
        });

        if (productAcquisitionResponseList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, productAcquisitionResponseList.size());
            list = productAcquisitionResponseList.subList(startItem, toIndex);
        }

        return new PageImpl<ProductAcquisitionItemListResponseDTO>(list, PageRequest.of(currentPage, pageSize), productAcquisitionResponseList.size());
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
