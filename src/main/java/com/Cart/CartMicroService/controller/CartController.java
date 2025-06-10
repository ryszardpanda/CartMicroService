package com.Cart.CartMicroService.controller;

import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import com.Cart.CartMicroService.service.CartMicroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/products")
public class CartController {

    private final CartMicroService productsService;

    @GetMapping
    public Page<ProductDTO> getProducts(){
        log.info("New request for endpoint GET/api/products logged");
        return productsService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id){
        log.info("New request for endpoint GET/api/products/{id} logged");
        return productsService.getProductById(id);
    }
}
