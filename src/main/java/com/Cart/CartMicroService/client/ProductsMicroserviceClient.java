package com.Cart.CartMicroService.client;

import com.Cart.CartMicroService.model.dto.product.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "products-micro-service", url = "${base.url.products-micro-service}")
public interface ProductsMicroserviceClient {

    @RequestMapping(method = RequestMethod.GET)
    Page<ProductDTO> getProducts();

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);
}
