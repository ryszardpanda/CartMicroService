package com.Cart.CartMicroService.model.dto.product;

import com.Cart.CartMicroService.common.ProductsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String name;
    private BigDecimal price;
    private ProductsType type;
    private int quantity;
    private List<ProductConfigurationDTO> configurations;
}
