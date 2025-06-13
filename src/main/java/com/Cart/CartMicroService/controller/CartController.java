package com.Cart.CartMicroService.controller;

import com.Cart.CartMicroService.mapper.CartMapper;
import com.Cart.CartMicroService.model.dto.cart.CartResponseDTO;
import com.Cart.CartMicroService.service.CartService;
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
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @Operation(summary = "Get cart By id", tags = "Cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart successfully returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
    })
    @GetMapping("/{id}")
    public CartResponseDTO getCartById(@PathVariable Long id){
        log.info("New request for endpoint GET/api/cart/{id} logged");
        return cartMapper.toDto(cartService.getCart(id));
    }

    @Operation(summary = "Delete cart", tags = "Cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart successfully deleted",
                    content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id){
        log.info("New request for endpoint DELETE/api/cart/{id} logged");
        cartService.deleteCart(id);
    }
}
