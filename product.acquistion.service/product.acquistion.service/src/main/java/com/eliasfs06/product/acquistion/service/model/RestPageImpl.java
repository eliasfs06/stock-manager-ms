package com.eliasfs06.product.acquistion.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class RestPageImpl<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestPageImpl(@JsonProperty("content") List<T> content,
                        @JsonProperty("number") int number,
                        @JsonProperty("size") int size,
                        @JsonProperty("totalElements") long totalElements,
                        @JsonProperty("pageable") Pageable pageable) {
        super(content, pageable, totalElements);
    }

    // Default constructor needed for Jackson deserialization
    public RestPageImpl(List<T> content) {
        super(content);
    }
}
