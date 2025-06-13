package com.Cart.CartMicroService.controller;

import com.Cart.CartMicroService.mapper.CartMapper;
import com.Cart.CartMicroService.model.dto.cart.CartResponseDTO;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import com.Cart.CartMicroService.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Add item to the cart", tags = "CartItem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully added to cart",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
    })
    @PostMapping("/{userId}/item")
    public CartResponseDTO addItem(@PathVariable String userId, @RequestBody CartItemRequestDTO cartItemRequestDTO) {
        log.info("New request for endpoint POST/api/cart/{userId}/item logged");
      return cartMapper.toDto(cartItemService.addItem(userId, cartItemRequestDTO));
    }

    @Operation(summary = "Remove item from cart", tags = "CartItem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully removed from cart",
                    content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @DeleteMapping("/{cartId}/item/{cartItemId}")
    public void removeItem(@PathVariable Long cartId, @PathVariable Long cartItemId){
        log.info("New request for endpoint DELETE/api/cart/{userId}/item logged");
        cartItemService.removeItem(cartId, cartItemId);
    }
}
