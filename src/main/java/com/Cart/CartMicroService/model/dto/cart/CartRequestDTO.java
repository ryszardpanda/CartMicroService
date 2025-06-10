package com.Cart.CartMicroService.model.dto.cart;

import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDTO {
    private String userId;
    private List<CartItemRequestDTO> items = new ArrayList<>();
}
