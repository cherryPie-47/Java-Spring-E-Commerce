package com.ecommerce.project.payload;

import com.ecommerce.project.model.Order;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private Long orderId;

    private String email;

    private LocalDate orderDate;

    private double totalAmount;

    private String orderStatus;

    private List<OrderItemDTO> orderItemDTOS;

    private Long addressId;

    private PaymentDTO paymentDTO;

    public OrderDTO() {
    }

    public OrderDTO(Long orderId,
                           String email,
                           LocalDate orderDate,
                           double totalAmount,
                           String orderStatus,
                           List<OrderItemDTO> orderItemDTOS,
                           Long addressId,
                           PaymentDTO paymentDTO) {
        this.orderId = orderId;
        this.email = email;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderItemDTOS = orderItemDTOS;
        this.addressId = addressId;
        this.paymentDTO = paymentDTO;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItemDTO> getOrderItemDTOS() {
        return orderItemDTOS;
    }

    public void setOrderItemDTOS(List<OrderItemDTO> orderItemDTOS) {
        this.orderItemDTOS = orderItemDTOS;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public PaymentDTO getPaymentDTO() {
            return paymentDTO;
    }

    public void setPaymentDTO(PaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
    }

    public static OrderDTO fromEntity(Order order, List<OrderItemDTO> orderItemDTOS, PaymentDTO paymentDTO) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setEmail(order.getEmail());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setOrderItemDTOS(orderItemDTOS);
        orderDTO.setAddressId(order.getAddress().getAddressId());
        orderDTO.setPaymentDTO(paymentDTO);

        return orderDTO;
    }
}
