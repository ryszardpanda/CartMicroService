package com.Cart.CartMicroService.model.dto.product;

import com.Cart.CartMicroService.common.ProductsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductConfigurationDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProductsType type;
}
