package com.Cart.CartMicroService.service;

import com.Cart.CartMicroService.exception.NoIdException;
import com.Cart.CartMicroService.model.entity.CartEntity;
import com.Cart.CartMicroService.repository.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartServiceTest {

	private CartService cartService;
	private CartRepository cartRepository;

	@BeforeEach
	void setUp(){
		this.cartRepository = Mockito.mock(CartRepository.class);
		this.cartService = new CartService(cartRepository);
	}

	@Test
	void getOrCreateCart_CartReturned(){
		//given
		CartEntity cartEntity = new CartEntity(1L, LocalDateTime.of(2022, 12, 12, 12, 12), "user123", new ArrayList<>());

		when(cartRepository.findByUserId(any())).thenReturn(Optional.of(cartEntity));
		when(cartRepository.save(any())).thenReturn(cartEntity);
		//when
		CartEntity result = cartService.getOrCreateCart(cartEntity.getUserId());
		//then
		assertEquals(1L, result.getCartId());
		assertEquals(LocalDateTime.of(2022, 12, 12, 12, 12), result.getCreatedAt());
		assertEquals("user123", result.getUserId());
	}

	@Test
	void getCart_CartExist_CartReturned(){
		//given
		CartEntity cartEntity = new CartEntity(1L, LocalDateTime.of(2022, 12, 12, 12, 12), "user123", new ArrayList<>());

		when(cartRepository.findById(any())).thenReturn(Optional.of(cartEntity));
		//when
		CartEntity result = cartService.getCart(1L);
		//then
		assertEquals(1L, result.getCartId());
		assertEquals(LocalDateTime.of(2022, 12, 12, 12, 12), result.getCreatedAt());
		assertEquals("user123", result.getUserId());
	}

	@Test
	void getCart_CartDoesNotExist_NoIdException(){
		//given
		Long id = 1L;
		when(cartRepository.findById(any())).thenReturn(Optional.empty());
		//when
		NoIdException result = Assertions.assertThrows(NoIdException.class, () -> cartService.getCart(id));
		//then
		Assertions.assertEquals("No cart with given id found " + id, result.getMessage());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
	}

	@Test
	void deleteCart_CartDeleted(){
		//given
		Long cartId = 1L;
		//when/then
		cartService.deleteCart(cartId);
		verify(cartRepository, times(1)).deleteById(cartId);
		verifyNoMoreInteractions(cartRepository);
	}
}
