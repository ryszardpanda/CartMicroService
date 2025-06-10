package com.Cart.CartMicroService.model.dto.cartItem;

import com.Cart.CartMicroService.common.ProductsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemConfigurationResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProductsType type;
}
