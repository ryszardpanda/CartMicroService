package com.Cart.CartMicroService.model.dto.cart;

import com.Cart.CartMicroService.model.dto.cartItem.CartItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
    private Long cartId;
    private String userId;
    private List<CartItemResponseDTO> items = new ArrayList<>();
}
