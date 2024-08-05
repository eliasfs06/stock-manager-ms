package com.eliasfs06.stock.manager.frontend.service;

import com.eliasfs06.stock.manager.frontend.model.RestPageImpl;
import com.eliasfs06.stock.manager.frontend.model.dto.ProductAcquisitionDto;
import com.eliasfs06.stock.manager.frontend.model.dto.ProductAcquisitionItemDTO;
import com.eliasfs06.stock.manager.frontend.model.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductAcquisitionService {
    @Value("${gateway.host}")
    private String gateway;

    @Value("${acquisition.service.path}")
    private String servicePath;

    @Autowired
    private RestTemplate restTemplate;

    public void deleteAcquisition(Long id) {
        String path = gateway + servicePath + "/delete/" + id;
        restTemplate.delete(path);
    }

    public Page<ProductAcquisitionDto> getPage(PageRequest pageRequest) {
        String path = gateway + servicePath + "/page";
        ParameterizedTypeReference<RestPageImpl<ProductAcquisitionDto>> responseType =
                new ParameterizedTypeReference<RestPageImpl<ProductAcquisitionDto>>() {};

        ResponseEntity<RestPageImpl<ProductAcquisitionDto>> responseEntity = restTemplate.exchange(
                path,
                HttpMethod.GET,
                null,
                responseType
        );

        return responseEntity.getBody();
    }

    public void save(List<ProductAcquisitionItemDTO> productAcquisitionItens) {
        String path = gateway + servicePath + "/save";
        restTemplate.postForObject(path, productAcquisitionItens, ProductDto.class);
    }
}
