package com.Cart.CartMicroService.service;

import com.Cart.CartMicroService.client.ProductsMicroserviceClient;
import com.Cart.CartMicroService.exception.NoIdException;
import com.Cart.CartMicroService.mapper.CartItemMapper;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import com.Cart.CartMicroService.model.entity.CartEntity;
import com.Cart.CartMicroService.model.entity.CartItemEntity;
import com.Cart.CartMicroService.repository.CartItemRepository;
import com.Cart.CartMicroService.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductsMicroserviceClient productsClient;
    private final CartItemMapper cartItemMapper;
    private final CartService cartService;

//    @Transactional
//    public CartEntity addOrUpdateItem(String userId, CartItemRequestDTO dto) {
//        CartEntity cart = getOrCreateCart(userId);
//        Optional<CartItemEntity> existingItem = findExistingItem(cart, dto.getProductId());
//
//        if (existingItem.isPresent()) {
//            updateExistingItem(existingItem.get(), dto);
//        } else {
//            addNewItem(cart, dto);
//        }
//
//        return cartRepository.save(cart);
//    }

    @Transactional
    public CartEntity addItem(String userId, CartItemRequestDTO cartItemRequestDTO){
        CartEntity cart = cartService.getOrCreateCart(userId);
        addNewItem(cart, cartItemRequestDTO);
        ProductDTO product = productsClient.getProductById(cartItemRequestDTO.getProductId());
        log.info("DTO z Feigna: {}", product);
        return cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(Long cartId, Long productId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoIdException("Cart not found: " + cartId, HttpStatus.BAD_REQUEST));
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        cartRepository.save(cart);
    }


//    private CartEntity getOrCreateCart(String userId) {
//        return cartRepository.findByUserId(userId)
//                .orElseGet(() -> {
//                    CartEntity newCart = new CartEntity();
//                    newCart.setUserId(userId);
//                    return cartRepository.save(newCart);
//                });
//    }

    private Optional<CartItemEntity> findExistingItem(CartEntity cart, Long productId) {
        return cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst();
    }

    private void updateExistingItem(CartItemEntity item, CartItemRequestDTO dto) {
        int newQuantity = item.getQuantity() + dto.getQuantity();
        item.setQuantity(newQuantity);
        cartItemRepository.save(item);
    }

    private void addNewItem(CartEntity cart, CartItemRequestDTO dto) {
        ProductDTO product = productsClient.getProductById(dto.getProductId());

        CartItemEntity cartItem = cartItemMapper.toEntity(product);
        cartItem.setCart(cart);
        cart.getItems().add(cartItem);
    }
}