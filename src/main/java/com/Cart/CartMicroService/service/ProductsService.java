package com.Cart.CartMicroService.service;

import com.Cart.CartMicroService.client.ProductsMicroserviceClient;
import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsService {

    private final ProductsMicroserviceClient productsMicroserviceClient;

    public Page<ProductDTO> getProducts(){
        Page<ProductDTO> products = productsMicroserviceClient.getProducts();
        log.info("ProductMicroService returned: " + products.toString());
        return products;
    }

    public ProductDTO getProductById(Long id){
        ProductDTO product = productsMicroserviceClient.getProductById(id);
        log.info("ProductMicroService returned: " + product.toString());
        return product;
    }
}
