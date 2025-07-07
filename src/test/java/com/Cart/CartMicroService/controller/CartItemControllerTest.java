package com.Cart.CartMicroService.controller;

import com.Cart.CartMicroService.mapper.CartMapper;
import com.Cart.CartMicroService.model.dto.cart.CartResponseDTO;
import com.Cart.CartMicroService.model.dto.cartItem.CartItemRequestDTO;
import com.Cart.CartMicroService.model.entity.CartEntity;
import com.Cart.CartMicroService.service.CartItemService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartItemController.class,
        properties = {"base.url.products-microservice=http://localhost:8081"})
@AutoConfigureMockMvc(addFilters = false)
public class CartItemControllerTest {

    @MockBean
    private CartItemService cartItemService;

    @MockBean
    private CartMapper cartMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addItem_ItemAdded() throws Exception {

        //given
        String userId = "user123";
        CartResponseDTO cartResponseDTO = new CartResponseDTO(1L, "user123", List.of());
        CartEntity cartEntity = new CartEntity(1L, LocalDateTime.of(2022, 12, 12, 12, 12), "user123", new ArrayList<>());
        CartItemRequestDTO cartItemRequestDTO = new CartItemRequestDTO(1L, 1L, List.of(), "name1", BigDecimal.valueOf(222), 5);

        Mockito.when(cartItemService.addItem(userId, cartItemRequestDTO)).thenReturn(cartEntity);
        Mockito.when(cartMapper.toDto(cartEntity)).thenReturn(cartResponseDTO);

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/{userId}/item", userId)
                        .content(objectMapper.writeValueAsString(cartItemRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cartId").value(1L))
                .andExpect(jsonPath("$.userId").value("user123"));
    }

    @Test
    void removeItem_success() throws Exception {
        // given
        Long cartId = 1L;
        Long cartItemId = 100L;

        Mockito.doNothing().when(cartItemService).removeItem(cartId, cartItemId);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/{cartId}/item/{cartItemId}", cartId, cartItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(cartItemService, Mockito.times(1)).removeItem(cartId, cartItemId);
    }
}
