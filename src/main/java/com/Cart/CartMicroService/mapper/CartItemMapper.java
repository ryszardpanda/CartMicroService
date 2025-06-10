package com.Cart.CartMicroService.mapper;

import com.Cart.CartMicroService.model.dto.cartItem.CartItemConfigurationRequestDTO;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import com.Cart.CartMicroService.model.entity.CartItemConfigurationEntity;
import com.Cart.CartMicroService.model.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "configurations", source = "dto.configurations")
    CartItemEntity toEntity(ProductDTO product, CartItemRequestDTO dto);

    CartItemConfigurationEntity toConfigurationEntity(CartItemConfigurationRequestDTO dto);

}
