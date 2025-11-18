package com.ecommerce.project.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    private double totalPrice = 0.0;

    public Cart() {
    }

    public Cart(double totalPrice, List<CartItem> cartItems, User user, Long cartId) {
        this.totalPrice = totalPrice;
        this.cartItems = cartItems;
        this.user = user;
        this.cartId = cartId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null) {
            return;
        }
        this.user = user;
        if (!this.equals(user.getCart())) {
            user.setCart(this);
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setCartItem(CartItem cartItem) {
        if (cartItem == null) {
            return;
        }
        this.cartItems.add(cartItem);
        if (!this.equals(cartItem.getCart())) {
            cartItem.setCart(this);
        }
    }

    public void removeCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
        cartItem.setCart(null);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
