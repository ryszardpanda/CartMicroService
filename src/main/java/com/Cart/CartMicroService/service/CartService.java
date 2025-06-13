package com.Cart.CartMicroService.service;

import com.Cart.CartMicroService.exception.NoIdException;
import com.Cart.CartMicroService.model.entity.CartEntity;
import com.Cart.CartMicroService.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    public CartEntity getOrCreateCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    CartEntity cart = new CartEntity();
                    cart.setUserId(userId);
                    log.info("CartMicroservice returned: " + cart);
                    return cartRepository.save(cart);
                });
    }

    public CartEntity getCart(Long id){
        CartEntity cartEntity = cartRepository.findById(id)
                .orElseThrow(() -> new NoIdException("No cart with given id found " + id, HttpStatus.BAD_REQUEST));
        log.info("CartMicroService returned cart: " + cartEntity.toString());
        return cartEntity;
    }

    @Transactional
    public void deleteCart(Long id){
        log.info("CartMicroservice deleted Cart with id: " + id);
        cartRepository.deleteById(id);
    }
}
