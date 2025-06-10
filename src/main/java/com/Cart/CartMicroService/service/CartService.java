package com.Cart.CartMicroService.service;

import com.Cart.CartMicroService.client.ProductsMicroserviceClient;
import com.Cart.CartMicroService.exception.NoIdException;
import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import com.Cart.CartMicroService.model.entity.CartEntity;
import com.Cart.CartMicroService.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final ProductsMicroserviceClient productsMicroserviceClient;
    private final CartRepository cartRepository;

    public Page<ProductDTO> getProducts(){
        Page<ProductDTO> products = productsMicroserviceClient.getProducts();
        log.info("CartMicroservice returned: " + products.toString());
        return products;
    }

    public ProductDTO getProductById(Long id){
        ProductDTO product = productsMicroserviceClient.getProductById(id);
        log.info("CartMicroservice returned: " + product.toString());
        return product;
    }

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
        return cartRepository.findById(id)
                .orElseThrow(() -> new NoIdException("No cart with given id found " + id, HttpStatus.BAD_REQUEST));
    }

    public void deleteCart(Long id){
        cartRepository.deleteById(id);
    }
}
