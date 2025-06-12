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

    @PostMapping("/{userId}/items")
    public CartResponseDTO addItem(@PathVariable String userId, @RequestBody CartItemRequestDTO cartItemRequestDTO) {
      return cartMapper.toDto(cartItemService.addItem(userId, cartItemRequestDTO));
    }

    @DeleteMapping()
    public void removeItem(@PathVariable Long cartId, @PathVariable Long productId){
        cartItemService.removeItem(cartId, productId);
    }
}
