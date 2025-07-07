package com.Cart.CartMicroService.service;

import com.Cart.CartMicroService.client.ProductsMicroserviceClient;
import com.Cart.CartMicroService.common.ProductsType;
import com.Cart.CartMicroService.exception.NoIdException;
import com.Cart.CartMicroService.mapper.CartItemMapper;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import com.Cart.CartMicroService.model.entity.CartEntity;
import com.Cart.CartMicroService.model.entity.CartItemEntity;
import com.Cart.CartMicroService.repository.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartItemServiceTest {

    private CartRepository cartRepository;
    private ProductsMicroserviceClient productsClient;
    private CartItemMapper cartItemMapper;
    private CartService cartService;
    private CartItemService cartItemService;

    @BeforeEach
    void setUp(){
        this.cartRepository = Mockito.mock(CartRepository.class);
        this.productsClient = Mockito.mock(ProductsMicroserviceClient.class);
        this.cartItemMapper = Mappers.getMapper(CartItemMapper.class);
        this.cartService = Mockito.mock(CartService.class);
        this.cartItemService = new CartItemService(cartRepository, productsClient, cartItemMapper, cartService);
    }

    @Test
    void addItem_ItemAdded(){
        //given
        CartEntity cartEntity = new CartEntity(1L, LocalDateTime.of(2022, 12, 12, 12, 12), "user123", new ArrayList<>());

        CartItemRequestDTO cartItemRequestDTO = new CartItemRequestDTO(1L, 1L, List.of(), "Item1", BigDecimal.valueOf(222), 5);

        ProductDTO productDTO = new ProductDTO(1L, "prod1", BigDecimal.valueOf(222), ProductsType.COMPUTER, 5, List.of());

        Mockito.when(cartService.getOrCreateCart(cartEntity.getUserId())).thenReturn(cartEntity);
        Mockito.when(productsClient.getProductById(Mockito.any())).thenReturn(productDTO);
        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(cartEntity);
        //when
        CartEntity result = cartItemService.addItem(cartEntity.getUserId(), cartItemRequestDTO);
        //then
        Assertions.assertEquals(1L, result.getCartId());
        Assertions.assertEquals(LocalDateTime.of(2022, 12, 12, 12, 12), result.getCreatedAt());
        Assertions.assertEquals("user123", result.getUserId());
        Assertions.assertNotNull(result.getItems());
        Assertions.assertEquals(1, result.getItems().size());
        CartItemEntity item = result.getItems().get(0);
        Assertions.assertEquals(productDTO.getProductId(), item.getProductId());
        Assertions.assertEquals(productDTO.getQuantity(), item.getQuantity());
    }

    @Test
    void removeItem_CartExist_ItemRemoved(){
        // given
        Long cartId = 1L;
        Long cartItemIdToRemove = 100L;

        CartItemEntity item1 = new CartItemEntity();
        item1.setCartItemId(100L);

        CartItemEntity item2 = new CartItemEntity();
        item2.setCartItemId(200L);

        CartEntity cart = new CartEntity();
        cart.setCartId(cartId);
        cart.setItems(new ArrayList<>(List.of(item1, item2)));

        Mockito.when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when
        cartItemService.removeItem(cartId, cartItemIdToRemove);

        // then
        Mockito.verify(cartRepository).save(cart);
        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertFalse(cart.getItems().stream()
                .anyMatch(item -> item.getCartItemId().equals(cartItemIdToRemove)));
    }

    @Test
    void removeItem_cartNotFound_throwsException() {
        // given
        Long cartId = 1L;
        Long cartItemId = 100L;

        Mockito.when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // when + then
        NoIdException exception = Assertions.assertThrows(NoIdException.class, () ->
                cartItemService.removeItem(cartId, cartItemId));

        Assertions.assertEquals("Cart not found: 1", exception.getMessage());
        Mockito.verify(cartRepository, Mockito.never()).save(Mockito.any());
    }
}
