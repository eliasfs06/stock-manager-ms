package com.eliasfs06.stock.manager.frontend.controller;

import com.eliasfs06.stock.manager.frontend.model.dto.ProductDto;
import com.eliasfs06.stock.manager.frontend.model.enums.Brand;
import com.eliasfs06.stock.manager.frontend.model.enums.ProductType;
import com.eliasfs06.stock.manager.frontend.model.exceptionsHandler.BusinessException;
import com.eliasfs06.stock.manager.frontend.service.ProductService;
import com.eliasfs06.stock.manager.frontend.service.helper.MessageCode;
import com.eliasfs06.stock.manager.frontend.service.helper.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageHelper messageHelper;

    private final String FORM_PATH = "/product/form";
    private final String LIST_PATH = "/product/list";
    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @GetMapping("/form")
    public ModelAndView getForm() {
        ModelAndView mv = new ModelAndView(FORM_PATH);

        mv.addObject("product", new ProductDto());
        mv.addObject("types", ProductType.values());
        mv.addObject("brands", Brand.values());

        return mv;
    }

    @PostMapping("/save")
    public String save(ProductDto productDto, Model model, RedirectAttributes ra) {
        try {
            productService.validateProduct(productDto);

        } catch (BusinessException e) {
            model.addAttribute("errorMessage", messageHelper.getMessage(MessageCode.DEFAULT_EMPTY_FIELD_MSG));
            model.addAttribute("product", productDto);
            model.addAttribute("types", ProductType.values());
            model.addAttribute("brands", Brand.values());
            return FORM_PATH;

        }
        productService.save(productDto);
        model.addAttribute("successMessage", messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG));
        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
    }

    @GetMapping("/list")
    public String findAll(Model model, @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ProductDto> productPage = productService.getPage(PageRequest.of(currentPage, pageSize));

        model.addAttribute("productList", productPage);
        model.addAttribute("currentPage", currentPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return LIST_PATH;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        productService.delete(id);
        model.addAttribute("successMessage", messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG));
        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
    }
}

