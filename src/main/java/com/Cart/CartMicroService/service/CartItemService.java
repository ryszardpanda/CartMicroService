package com.Cart.CartMicroService.service;

import com.Cart.CartMicroService.client.ProductsMicroserviceClient;
import com.Cart.CartMicroService.exception.NoIdException;
import com.Cart.CartMicroService.mapper.CartItemMapper;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemConfigurationRequestDTO;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import com.Cart.CartMicroService.model.dto.product.ProductConfigurationDTO;
import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import com.Cart.CartMicroService.model.entity.CartEntity;
import com.Cart.CartMicroService.model.entity.CartItemConfigurationEntity;
import com.Cart.CartMicroService.model.entity.CartItemEntity;
import com.Cart.CartMicroService.repository.CartItemRepository;
import com.Cart.CartMicroService.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductsMicroserviceClient productsClient;
    private final CartItemMapper cartItemMapper;
    private final CartService cartService;

    @Transactional
    public CartEntity addItem(String userId, CartItemRequestDTO cartItemRequestDTO){
        CartEntity cart = cartService.getOrCreateCart(userId);
        addNewItem(cart, cartItemRequestDTO);
        return cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(Long cartId, Long cartItemId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoIdException("Cart not found: " + cartId, HttpStatus.BAD_REQUEST));
        cart.getItems().removeIf(i -> i.getCartItemId().equals(cartItemId));
        cartRepository.save(cart);
    }

    private void addNewItem(CartEntity cart, CartItemRequestDTO dto) {
        ProductDTO product = productsClient.getProductById(dto.getProductId());

        Set<Long> selectedIds = dto.getConfigurations().stream()
                .map(CartItemConfigurationRequestDTO::getId)
                .collect(Collectors.toSet());

        List<ProductConfigurationDTO> chosenCfgs = product.getConfigurations().stream()
                .filter(c -> selectedIds.contains(c.getProductConfigurationId()))
                .toList();

        CartItemEntity cartItem = cartItemMapper.toEntity(product);
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setProductId(product.getProductId());

        List<CartItemConfigurationEntity> cfgEntities = chosenCfgs.stream()
                .map(cartItemMapper::toConfigurationEntity)
                .peek(cfg -> cfg.setCartItem(cartItem))   // relacja dwukierunkowa
                .toList();
        cartItem.setConfigurations(cfgEntities);

        cartItem.setCart(cart);
        cart.getItems().add(cartItem);
    }
}