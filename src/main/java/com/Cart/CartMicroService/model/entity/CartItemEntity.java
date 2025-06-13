package com.Cart.CartMicroService.model.entity;

import com.Cart.CartMicroService.common.ProductsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartItemId;

    private Long productId;
    private String name;
    private BigDecimal price;
    private ProductsType type;
    private int quantity;

    @OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemConfigurationEntity> configurations = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof CartItemEntity))
            return false;

        CartItemEntity other = (CartItemEntity) o;

        return cartItemId != null &&
                cartItemId.equals(other.getCartItemId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
