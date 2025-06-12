package com.Cart.CartMicroService.controller;

import com.Cart.CartMicroService.mapper.CartMapper;
import com.Cart.CartMicroService.model.dto.cart.CartResponseDTO;
import com.Cart.CartMicroService.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/cart")
public class CartController {

    private final CartService productsService;
    private final CartMapper cartMapper;

    @GetMapping("/{id}")
    public CartResponseDTO getCartById(@PathVariable Long id){
        log.info("New request for endpoint GET/api/products/{id} logged");
        return cartMapper.toDto(productsService.getCart(id));
    }
}
