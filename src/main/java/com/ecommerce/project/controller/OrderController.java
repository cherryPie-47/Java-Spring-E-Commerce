package com.ecommerce.project.controller;

import com.ecommerce.project.payload.OrderDTO;
import com.ecommerce.project.payload.OrderRequestDTO;
import com.ecommerce.project.service.OrderService;
import com.ecommerce.project.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;
    private final AuthUtils authUtils;

    public OrderController(OrderService orderService, AuthUtils authUtils) {
        this.orderService = orderService;
        this.authUtils = authUtils;
    }

    @PostMapping("/users/order/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod,
                                                  @RequestBody OrderRequestDTO orderRequestDTO) {
        String email = authUtils.loggedInEmail();
        return new ResponseEntity<>(orderService.placeOrder(paymentMethod, email, orderRequestDTO), HttpStatus.CREATED);
    }
}
