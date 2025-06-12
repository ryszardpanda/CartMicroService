package com.Cart.CartMicroService.mapper;

import com.Cart.CartMicroService.model.dto.cart.CartRequestDTO;
import com.Cart.CartMicroService.model.dto.cart.CartResponseDTO;
import com.Cart.CartMicroService.model.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        uses = CartItemMapper.class
)
public interface CartMapper {
   // CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

   // @Mapping(target = "items", source = "items")
    CartResponseDTO toDto(CartEntity entity);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "items", ignore = true)
    //CartEntity toEntity(CartRequestDTO dto);
}