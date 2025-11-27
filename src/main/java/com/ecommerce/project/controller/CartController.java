package com.ecommerce.project.controller;

import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Add product to cart", description = "Add a new product to cart")
    @PostMapping("/users/carts/{cartId}/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@Parameter(description = "ID of the cart you wish to add product to") @PathVariable Long cartId,
                                                    @Parameter(description = "ID of the product you wish to add to cart") @PathVariable Long productId,
                                                    @Parameter(description = "Number of products you wish to add") @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(cartId, productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Get all carts", description = "Get a list of all existing carts")
    @GetMapping("/users/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return new ResponseEntity<>(cartDTOS, HttpStatus.FOUND);
    }

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Get current user's cart", description = "Get the cart of the current authenticated user")
    @GetMapping("/users/carts/{cartId}")
    public ResponseEntity<CartDTO> getUserCart(@Parameter(description = "ID of the cart you wish to get")  @PathVariable Long cartId) {
        return new ResponseEntity<>(cartService.getUserCart(cartId), HttpStatus.FOUND);
    }

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Update cart item quantity", description = "Update a specified cart item in the cart")
    @PutMapping("/users/carts/{cartId}/products/{productId}/{operation}")
    public ResponseEntity<?> updateCartItemQuantity(@Parameter(description = "ID of the cart you want to update") @PathVariable Long cartId,
                                                    @Parameter(description = "ID of the product you wish to update") @PathVariable Long productId,
                                                    @Parameter(description = "The operation you wish to perform (add/delete)") @PathVariable String operation) {
        return new ResponseEntity<>(cartService.updateCartItemQuantity(cartId, productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1), HttpStatus.OK);
    }

    @Tag(name = "Cart APIs", description = "APIs for managing carts")
    @Operation(summary = "Delete cart item from cart", description = "Delete a specified cart item in the cart")
    @DeleteMapping("/users/carts/{cartId}/products/{productId}")
    public ResponseEntity<CartDTO> deleteCartItemFromCart(@Parameter(description = "ID of the cart you want to delete") @PathVariable Long cartId,
                                                          @Parameter(description = "ID of the product you want to delete") @PathVariable Long productId) {
        CartDTO status = cartService.deleteCartItemFromCart(cartId, productId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
