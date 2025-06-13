package com.Cart.CartMicroService.client;

import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "products-micro-service", url = "${base.url.products-micro-service}")
public interface ProductsMicroserviceClient {

    @GetMapping()
    Page<ProductDTO> getProducts(@RequestParam("page") int page, @RequestParam("size") int size);

    default Page<ProductDTO> getProducts() {
        return getProducts(0, 20);
    }

    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable Long id);
}
