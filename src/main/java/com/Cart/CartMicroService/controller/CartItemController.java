package com.Cart.CartMicroService.controller;

import com.Cart.CartMicroService.mapper.CartMapper;
import com.Cart.CartMicroService.model.dto.cart.CartResponseDTO;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import com.Cart.CartMicroService.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartMapper cartMapper;

    @PostMapping("/{userId}/item")
    public CartResponseDTO addItem(@PathVariable String userId, @RequestBody CartItemRequestDTO cartItemRequestDTO) {
        log.info("New request for endpoint POST/api/cart/{userId}/item logged");
      return cartMapper.toDto(cartItemService.addItem(userId, cartItemRequestDTO));
    }

    @DeleteMapping("/{cartId}/item/{cartItemId}")
    public void removeItem(@PathVariable Long cartId, @PathVariable Long cartItemId){
        log.info("New request for endpoint DELETE/api/cart/{userId}/item logged");
        cartItemService.removeItem(cartId, cartItemId);
    }
}
