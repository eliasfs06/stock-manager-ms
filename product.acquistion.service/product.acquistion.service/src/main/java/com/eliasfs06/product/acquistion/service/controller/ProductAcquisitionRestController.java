package com.eliasfs06.product.acquistion.service.controller;

import com.eliasfs06.product.acquistion.service.model.Product;
import com.eliasfs06.product.acquistion.service.model.ProductAcquisition;
import com.eliasfs06.product.acquistion.service.model.ProductAcquisitionItem;
import com.eliasfs06.product.acquistion.service.model.dto.*;
import com.eliasfs06.product.acquistion.service.model.exceptionsHandler.BusinessException;
import com.eliasfs06.product.acquistion.service.service.ProductAcquisitionService;
import com.eliasfs06.product.acquistion.service.service.helper.MessageCode;
import com.eliasfs06.product.acquistion.service.service.helper.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class ProductAcquisitionRestController extends GenericRestController<ProductAcquisition> {

    @Autowired
    private ProductAcquisitionService service;

    @Autowired
    private MessageHelper messageHelper;

    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<Long>> delete(@PathVariable Long id) {
        try {
            service.deleteAcquisition(id);
            return new ResponseEntity<>(new ResponseWrapper<>(MessageCode.DEFAULT_SUCCESS_MSG, "success", id), HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = messageHelper.getMessage(MessageCode.DEFAULT_ERROR_MSG);
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<?> findAll(@RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ProductAcquisitionDto> productAcquisitionPage = service.getPageResponse(PageRequest.of(currentPage - 1, pageSize));

        if (productAcquisitionPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productAcquisitionPage);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductAcquisitionDto productAcquisitionDto) {
        ProductAcquisition productAcquisition = new ProductAcquisition();
        ProductAcquisitionDto productAcquisitionSavedDto = new ProductAcquisitionDto();
        try {
            productAcquisition = service.save(productAcquisitionDto.getItens());
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        productAcquisitionSavedDto.setId(productAcquisition.getId());
        List<ProductAcquisitionItemDTO> savedItens = new ArrayList<>();
        for(ProductAcquisitionItem item : productAcquisition.getItens()){
            ProductAcquisitionItemDTO dto = new ProductAcquisitionItemDTO();
            dto.setProduct(item.getProduct());
            dto.setProductId(Math.toIntExact(item.getProduct().getId()));
            dto.setQuantity(item.getQuantity());
            savedItens.add(dto);
        }
        productAcquisitionSavedDto.setItens(savedItens);
        productAcquisitionSavedDto.setAquisitionDate(productAcquisition.getAquisitionDate());
        return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG), "success", productAcquisitionSavedDto), HttpStatus.CREATED);
    }
}