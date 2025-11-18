package com.ecommerce.project.controller;

import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.service.CartService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    public final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/users/carts/{cartId}/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long cartId,
                                                    @PathVariable Long productId,
                                                    @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(cartId, productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/users/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return new ResponseEntity<>(cartDTOS, HttpStatus.FOUND);
    }

    @GetMapping("/users/carts/{cartId}")
    public ResponseEntity<CartDTO> getUserCart(@PathVariable Long cartId) {
        return new ResponseEntity<>(cartService.getUserCart(cartId), HttpStatus.FOUND);
    }

    @PutMapping("/users/carts/{cartId}/products/{productId}/{operation}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @PathVariable String operation) {

        return new ResponseEntity<>(cartService.updateCartItemQuantity(cartId, productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1), HttpStatus.OK);
    }

    @DeleteMapping("/users/carts/{cartId}/products/{productId}")
    public ResponseEntity<CartDTO> deleteCartItemFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId) {
        CartDTO status = cartService.deleteCartItemFromCart(cartId, productId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
