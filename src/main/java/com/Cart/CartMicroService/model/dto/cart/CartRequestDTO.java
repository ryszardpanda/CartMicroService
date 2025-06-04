package com.Cart.CartMicroService.model.dto.cart;

import com.Cart.CartMicroService.model.entity.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDTO {
    private Long id;
    private List<CartItemEntity> items = new ArrayList<>();
}
