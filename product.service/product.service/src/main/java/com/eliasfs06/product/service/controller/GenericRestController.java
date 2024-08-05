package com.eliasfs06.product.service.controller;

import com.eliasfs06.product.service.model.BaseEntity;
import com.eliasfs06.product.service.model.dto.ResponseWrapper;
import com.eliasfs06.product.service.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class GenericRestController<T extends BaseEntity> {

    @Autowired
    private GenericService<T> service;

    public GenericRestController() {}

    @GetMapping("")
    public ResponseEntity<Page<T>> getPage(Pageable pageable){
        return ResponseEntity.ok(service.getPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getOne(@PathVariable Long id){
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("")
    public ResponseEntity<T> update(@RequestBody T updated){
        return ResponseEntity.ok(service.update(updated));
    }

    @PostMapping("")
    public ResponseEntity<T> create(@RequestBody T created){
        return ResponseEntity.ok(service.create(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Long>> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok(new ResponseWrapper<>("Ok", "success", id));
    }
}