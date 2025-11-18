package com.ecommerce.project.payload;

public class CartItemDTO {

    private Long cartItemId;

    private CartDTO cartDTO;

    private ProductDTO productDTO;

    private Integer quantity;

    private double discount;

    private double productPrice;

    public CartItemDTO() {
    }

    public CartItemDTO(Long cartItemId, CartDTO cartDTO, ProductDTO productDTO, Integer quantity, double discount, double productPrice) {
        this.cartItemId = cartItemId;
        this.cartDTO = cartDTO;
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.discount = discount;
        this.productPrice = productPrice;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public CartDTO getCartDTO() {
        return cartDTO;
    }

    public void setCartDTO(CartDTO cartDTO) {
        this.cartDTO = cartDTO;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
