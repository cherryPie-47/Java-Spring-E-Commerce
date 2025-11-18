package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Size(min = 1, message = "Product name cannot be blank")
    private String productName;

    @NotBlank
    @Size(min = 1, message = "Description cannot be blank")
    private String description;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Product(){
    }

    public Product(String productName, String description, String image, Integer quantity, double price, double specialPrice, Category category, User user, List<CartItem> cartItems){
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.specialPrice = specialPrice;
        this.category = category;
        this.user = user;
        this.cartItems = cartItems;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName(){
        return productName;
    }

    public String getDescription(){
        return description;
    }

    public String getImage() {
        return image;
    }

    public Integer getQuantity(){
        return quantity;
    }

    public double getPrice(){
        return price;
    }

    public double getDiscount(){
        return discount;
    }

    public double getSpecialPrice(){
        return specialPrice;
    }

    public Category getCategory(){
        return category;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setDiscount(double discount){
        this.discount = discount;
    }

    public void setSpecialPrice(double specialPrice){
        this.specialPrice = specialPrice;
    }

    public void setCategory(Category category){
        if (category == null) {
            return;
        }
        this.category = category;
        if (!category.getProducts().contains(this)) {
            category.setProduct(this);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null) {
            return;
        }
        this.user = user;
        if (!user.getProducts().contains(this)) {
            user.setProduct(this);
        }
    }

    public List<CartItem> getProducts() {
        return cartItems;
    }

    public void setProducts(List<CartItem> products) {
        this.cartItems = products;
    }

    public void setProduct(CartItem cartItem) {
        if (cartItem == null) {
            return;
        }
        this.cartItems.add(cartItem);
        if (!this.equals(cartItem.getProduct())) {
            cartItem.setProduct(this);
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
        if (this.equals(cartItem.getProduct())) {
            cartItem.setProduct(this);
        }
    }

    public void removeCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
        cartItem.setProduct(null);
    }
}
