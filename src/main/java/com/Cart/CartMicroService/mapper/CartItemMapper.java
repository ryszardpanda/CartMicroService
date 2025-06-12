package com.Cart.CartMicroService.mapper;

import com.Cart.CartMicroService.model.dto.cartItem.CartItemConfigurationRequestDTO;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import com.Cart.CartMicroService.model.entity.CartItemConfigurationEntity;
import com.Cart.CartMicroService.model.entity.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {


    // MapStruct wygeneruje tę metodę
    CartItemEntity toEntity(CartItemRequestDTO dto);

    // Domyślna metoda, która ustawi relacje dwukierunkowe
    default CartItemEntity toEntityWithRelations(CartItemRequestDTO dto) {
        CartItemEntity entity = toEntity(dto);
        if (entity.getConfigurations() != null) {
            for (CartItemConfigurationEntity config : entity.getConfigurations()) {
                entity.addConfiguration(config); // ustawia relację dwukierunkową
            }
        }
        return entity;
    }

    CartItemEntity toEntity(ProductDTO product);

    CartItemConfigurationEntity toConfigurationEntity(CartItemConfigurationRequestDTO dto);

}
