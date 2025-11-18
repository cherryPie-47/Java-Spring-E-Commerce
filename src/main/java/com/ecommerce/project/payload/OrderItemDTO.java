package com.ecommerce.project.payload;

import com.ecommerce.project.model.OrderItem;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public class OrderItemDTO {
    private Long orderItemId;

    private ProductDTO productDTO;

    private int quantity;

    private double discount;

    private double orderedPrice;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long orderItemId, ProductDTO productDTO, int quantity, double discount, double orderedPrice) {
        this.orderItemId = orderItemId;
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.discount = discount;
        this.orderedPrice = orderedPrice;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
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

    public static OrderItemDTO fromEntity(OrderItem orderItem, ProductDTO productDTO) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setDiscount(orderItem.getDiscount());
        orderItemDTO.setOrderedPrice(orderItem.getOrderedPrice());
        orderItemDTO.setProductDTO(productDTO);

        return orderItemDTO;
    }
}
