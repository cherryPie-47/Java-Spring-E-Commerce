package com.ecommerce.project.service;

import com.ecommerce.project.payload.CartDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long cartId, Long productId, Integer quantity);
    List<CartDTO> getAllCarts();
    CartDTO getUserCart(Long cartId);
    CartDTO deleteCartItemFromCart(Long cartId, Long productId);

    @Transactional
    CartDTO updateCartItemQuantity(Long cartId, Long productId, int quantity);

    void updateProductInCarts(Long cartId, Long productId);
}
