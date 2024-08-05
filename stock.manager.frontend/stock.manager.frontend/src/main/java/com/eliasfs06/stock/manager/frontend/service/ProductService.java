package com.eliasfs06.stock.manager.frontend.service;

import com.eliasfs06.stock.manager.frontend.model.RestPageImpl;
import com.eliasfs06.stock.manager.frontend.model.dto.ProductDto;
import com.eliasfs06.stock.manager.frontend.model.exceptionsHandler.BusinessException;
import com.eliasfs06.stock.manager.frontend.service.helper.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Value("${gateway.host}")
    private String gateway;

    @Value("${product.service.path}")
    private String servicePath;

    @Autowired
    private RestTemplate restTemplate;

    public void save(ProductDto productDto){
        String path = gateway + servicePath + "/save";
        restTemplate.postForObject(path, productDto, ProductDto.class);
    }

    public void validateProduct(ProductDto productDto) throws BusinessException {
        if(productDto.getName() == null || productDto.getName().isEmpty() ||
                productDto.getProductType() == null ||  productDto.getBrand() == null){
            throw new BusinessException(MessageCode.DEFAULT_EMPTY_FIELD_MSG);
        }
        save(productDto);
    }

    public Page<ProductDto> getPage(PageRequest pageRequest) {
        String path = gateway + servicePath + "/page";

        int page = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();

        StringBuilder urlBuilder = new StringBuilder(path)
                .append("?page=").append(page)
                .append("&size=").append(size);

        String url = urlBuilder.toString();

        ParameterizedTypeReference<RestPageImpl<ProductDto>> responseType =
                new ParameterizedTypeReference<RestPageImpl<ProductDto>>() {};

        ResponseEntity<RestPageImpl<ProductDto>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                responseType
        );

        return responseEntity.getBody();
    }

    public void delete(Long id) {
        String path = gateway + servicePath + "/delete/" + id;
        restTemplate.delete(path);
    }

    public List<ProductDto> findAll() {
        String path = gateway + servicePath + "/list";
        ProductDto[] productArray = restTemplate.getForObject(path, ProductDto[].class);
        return Arrays.asList(productArray);
    }

    public ProductDto getProduct(Long id) {
        String path = gateway + servicePath + "/get/" + id;
        return restTemplate.getForObject(path, ProductDto.class);
    }
}
