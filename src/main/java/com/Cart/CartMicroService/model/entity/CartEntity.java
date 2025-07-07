package com.Cart.CartMicroService.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(nullable = false, unique = true)
    private String userId;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> items = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CartEntity))
            return false;

        CartEntity other = (CartEntity) o;

        return cartId != null &&
                cartId.equals(other.getCartId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
