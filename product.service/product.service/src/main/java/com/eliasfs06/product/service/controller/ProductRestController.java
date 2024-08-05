package com.eliasfs06.product.service.controller;

import com.eliasfs06.product.service.exceptionsHandler.BusinessException;
import com.eliasfs06.product.service.model.Product;
import com.eliasfs06.product.service.model.dto.ResponseWrapper;
import com.eliasfs06.product.service.service.ProductService;
import com.eliasfs06.product.service.service.helper.MessageCode;
import com.eliasfs06.product.service.service.helper.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductRestController extends GenericRestController<Product> {

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageHelper messageHelper;

    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<?> findAllPage(@RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<Product> productPage = productService.getPage(PageRequest.of(currentPage - 1, pageSize));

        return ResponseEntity.ok(productPage);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Product product) {
        try {
            productService.validateProduct(product);
            Product productSaved = productService.create(product);
            return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG), "success", productSaved), HttpStatus.CREATED);
        } catch (BusinessException e) {
            String errorMessage = messageHelper.getMessage(MessageCode.DEFAULT_EMPTY_FIELD_MSG);
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<Long>> delete(@PathVariable Long id) {
        try {
            productService.delete(id);
            return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG), "success", id), HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = messageHelper.getMessage(MessageCode.DEFAULT_ERROR_MSG);
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{id}")
    private Product getById(@PathVariable Long id){
        return productService.get(id);
    }
}