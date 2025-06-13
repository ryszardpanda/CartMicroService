package com.Cart.CartMicroService.mapper;

import com.Cart.CartMicroService.model.dto.cartItem.CartItemConfigurationRequestDTO;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import com.Cart.CartMicroService.model.dto.product.ProductConfigurationDTO;
import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import com.Cart.CartMicroService.model.entity.CartItemConfigurationEntity;
import com.Cart.CartMicroService.model.entity.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemEntity toEntity(CartItemRequestDTO dto);
    CartItemEntity toEntity(ProductDTO product);
    CartItemConfigurationEntity toConfigurationEntity(CartItemConfigurationRequestDTO dto);
    CartItemConfigurationEntity toConfigurationEntity(ProductConfigurationDTO dto);

}
