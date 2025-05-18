package com.Cart.CartMicroService.model.dto.cartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDTO {
    private Long id;

    private Long productId;
    private String name;
    private double price;
    private int quantity;
}
