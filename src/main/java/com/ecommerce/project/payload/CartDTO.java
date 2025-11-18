package com.ecommerce.project.payload;

import com.ecommerce.project.model.Cart;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
    private Long cartId;

    private double totalPrice = 0;

    private List<ProductDTO> products = new ArrayList<>();

    public CartDTO() {
    }


    public CartDTO(Long cartId, double totalPrice, List<ProductDTO> productDTOS) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.products = productDTOS;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public static CartDTO fromEntity(Cart cart, List<ProductDTO> productDTOS) {
        CartDTO cartDTO = new CartDTO();

        cartDTO.setCartId(cart.getCartId());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        cartDTO.setProducts(productDTOS);

        return cartDTO;
    }
}
