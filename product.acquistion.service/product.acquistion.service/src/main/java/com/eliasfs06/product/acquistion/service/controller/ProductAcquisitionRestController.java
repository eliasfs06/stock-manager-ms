package com.eliasfs06.product.acquistion.service.controller;

import com.eliasfs06.product.acquistion.service.model.Product;
import com.eliasfs06.product.acquistion.service.model.ProductAcquisition;
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

        Page<ProductAcquisitionItemListResponseDTO> productAcquisitionPage = service.getPageResponse(PageRequest.of(currentPage - 1, pageSize));

        if (productAcquisitionPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        int totalPages = productAcquisitionPage.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageResponse<>(productAcquisitionPage, currentPage, pageNumbers));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody List<ProductAcquisitionItemDTO> productAcquisitionItens) {
        try {
            ProductAcquisition productAcquisition = service.save(productAcquisitionItens);
            return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG), "success", productAcquisition), HttpStatus.CREATED);

        } catch (BusinessException e){
            String errorMessage = messageHelper.getMessage(e.getMessage());
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error", null), HttpStatus.BAD_REQUEST);

        }
    }
}