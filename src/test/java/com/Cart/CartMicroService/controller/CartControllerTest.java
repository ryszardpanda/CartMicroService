package com.Cart.CartMicroService.controller;

import com.Cart.CartMicroService.mapper.CartMapper;
import com.Cart.CartMicroService.model.dto.cart.CartResponseDTO;
import com.Cart.CartMicroService.model.entity.CartEntity;
import com.Cart.CartMicroService.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartController.class,
        properties = {"base.url.products-microservice=http://localhost:8081"})
@AutoConfigureMockMvc(addFilters = false)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @MockBean
    private CartMapper cartMapper;

    @Test
    void getCartById() throws Exception {
        //given
        Long id = 1L;
        CartResponseDTO cartResponseDTO = new CartResponseDTO(1L, "user123", List.of());
        CartEntity cartEntity = new CartEntity(1L, LocalDateTime.of(2022, 12, 12, 12, 12), "user123", new ArrayList<>());


        Mockito.when(cartService.getCart(id)).thenReturn(cartEntity);
        Mockito.when(cartMapper.toDto(cartEntity)).thenReturn(cartResponseDTO);

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cartId").value(1L))
                .andExpect(jsonPath("$.userId").value("user123"));
    }

    @Test
    void deleteCart() throws Exception {
        //given
        Long id = 1L;

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(cartService, Mockito.times(1)).deleteCart(id);
        Mockito.verifyNoMoreInteractions(cartService);
    }
}
