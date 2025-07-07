package com.Cart.CartMicroService.mapper;

import com.Cart.CartMicroService.model.dto.cart.CartResponseDTO;
import com.Cart.CartMicroService.model.entity.CartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponseDTO toDto(CartEntity entity);
}