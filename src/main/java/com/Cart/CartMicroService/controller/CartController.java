package com.Cart.CartMicroService.controller;

import com.Cart.CartMicroService.mapper.CartMapper;
import com.Cart.CartMicroService.model.dto.cart.CartResponseDTO;
import com.Cart.CartMicroService.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/{id}")
    public CartResponseDTO getCartById(@PathVariable Long id){
        log.info("New request for endpoint GET/api/cart/{id} logged");
        return cartMapper.toDto(cartService.getCart(id));
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id){
        log.info("New request for endpoint DELETE/api/cart/{id} logged");
        cartService.deleteCart(id);
    }
}
