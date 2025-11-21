package com.ecommerce.project.controller;

import com.ecommerce.project.payload.OrderDTO;
import com.ecommerce.project.payload.OrderRequestDTO;
import com.ecommerce.project.service.OrderService;
import com.ecommerce.project.utils.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Order APIs", description = "APIs for managing Order")
    @Operation(summary = "Order product", description = "Order products currently on the cart")
    @PostMapping("/users/order/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(@Parameter(description = "Payment method you wish to use (PAYPAL/E-WALLET/CARD/COD)") @PathVariable String paymentMethod,
                                                  @RequestBody OrderRequestDTO orderRequestDTO) {
        String email = authUtils.loggedInEmail();
        return new ResponseEntity<>(orderService.placeOrder(paymentMethod, email, orderRequestDTO), HttpStatus.CREATED);
    }
}
