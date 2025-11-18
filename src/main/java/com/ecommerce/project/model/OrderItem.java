package com.ecommerce.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private int quantity;

    private double discount;

    private double orderedPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem() {
    }

    public OrderItem(Long orderItemId, int quantity, double discount, double orderedPrice, Order order, Product product) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.discount = discount;
        this.orderedPrice = orderedPrice;
        this.order = order;
        this.product = product;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getOrderedPrice() {
        return orderedPrice;
    }

    public void setOrderedPrice(double orderedPrice) {
        this.orderedPrice = orderedPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if (order == null) {
            return;
        }
        this.order = order;
        if (!order.getOrderItems().contains(this)) {
            order.setOrderItem(this);
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
