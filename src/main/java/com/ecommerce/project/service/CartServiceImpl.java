package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.CartItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImpl implements CartService{

    public final CartRepository cartRepository;

    public final CartItemRepository cartItemRepository;

    public final ProductRepository productRepository;

    public final UserRepository userRepository;

    public final AuthUtils authUtils;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           ProductRepository productRepository,
                           UserRepository userRepository,
                           AuthUtils authUtils) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.authUtils = authUtils;
    }

    @Override
    public CartDTO addProductToCart(Long cartId, Long productId, Integer quantity) {
        // Find existing cart or create one
        Cart cart = createCart(cartId);

        // Retrieve Product details
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }
        Product product = productOptional.get();

        // Perform validations
        /*
        Here we check if the cart item is already existed first, then check for the product quantity later, doesn't make
        sense in terms of code optimization, but it's for a better user experience
        */
        Optional<CartItem> cartItemOptional = cartItemRepository.findCartItemByCartIdAndProductId(
                cartId,
                productId
        );
        if (cartItemOptional.isPresent()) {
            throw new APIException(String.format("Product %s already exists in the cart", product.getProductName()));
        }
        if (product.getQuantity() == 0) {
            throw new APIException(String.format("Product %s is not available", product.getProductName()));
        }
        if (product.getQuantity() < quantity) {
            throw new APIException(String.format("Product %s only has %d left in stock", product.getProductName(), product.getQuantity()));
        }

        // Create Cart Item
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        cartRepository.save(cart);

        // Return updated CartDTO
        List<CartItem> cartItems = cart.getCartItems();

        List<ProductDTO> productDTOS = cartItems.stream().map(item -> {
            ProductDTO productDTO = ProductDTO.fromEntity(item.getProduct());
            productDTO.setQuantity(item.getQuantity());
            return productDTO;
        }).toList();

        return CartDTO.fromEntity(cart, productDTOS);

    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if (carts.isEmpty()) {
            throw new APIException("No cart exists");
        }
        List<CartDTO> cartDTOS = carts.stream()
                .map(cart -> {
                    List<ProductDTO> productDTOS = cart.getCartItems().stream()
                            .map(cartItem -> {
                                ProductDTO productDTO = ProductDTO.fromEntity(cartItem.getProduct());
                                productDTO.setQuantity(cartItem.getQuantity());
                                return productDTO;
                            }).toList();
                    return CartDTO.fromEntity(cart, productDTOS);
                }).toList();
        return cartDTOS;
    }

    @Override
    public CartDTO getUserCart(Long cartId) {
        Optional<Cart> cartOptional = cartRepository.findCartByEmailAndCartId(authUtils.loggedInEmail(), cartId);
        if (cartOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }
        Cart cart = cartOptional.get();
        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(cartItem -> {
                    ProductDTO productDTO = ProductDTO.fromEntity(cartItem.getProduct());
                    productDTO.setQuantity(cartItem.getQuantity());
                    return productDTO;
                }).toList();
        return CartDTO.fromEntity(cart, productDTOS);
    }

    @Override
    public CartDTO deleteCartItemFromCart(Long cartId, Long productId) {
        Optional<Cart> cartOptional = cartRepository.findCartByEmailAndCartId(
                authUtils.loggedInEmail(),
                cartId
        );
        if (cartOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }
        Cart cart = cartOptional.get();

        Optional<CartItem> cartItemOptional = cartItemRepository.findCartItemByCartIdAndProductId(
                cartId,
                productId
        );
        if (cartItemOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }
        CartItem cartItem = cartItemOptional.get();

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }
        Product product = productOptional.get();
        product.removeCartItem(cartItem);
        productRepository.save(product);

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getQuantity() * cartItem.getProductPrice()));
        cart.removeCartItem(cartItem);
        cartRepository.save(cart);

        // Here we directly remove the CartItem (child entity) from the database, it won't face N+1 SQL problem
        // But we have to manually manage the relationship in in-memory state (for in-memory consistency)
        // cart.getCartItems().remove(cartItem);
        cartItemRepository.deleteCartItemByCartIdAndProductId(cartId, productId);

        // Or we could un-link the in-memory relationship first, then save (persist/merge) the cart to the database
        // JPA will automatically query a DELETE the child entity, provided orphanRemoval = true in parent entity
        /*
        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
         */
        String.format("Product %s removed from the cart", cartItem.getProduct().getProductName());

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(ci -> {
                    ProductDTO productDTO = ProductDTO.fromEntity(ci.getProduct());
                    productDTO.setQuantity(cartItem.getQuantity());
                    return productDTO;
                }).toList();
        return CartDTO.fromEntity(cart, productDTOS);
    }

    @Transactional
    @Override
    public CartDTO updateCartItemQuantity(Long cartId, Long productId, int quantity) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }
        Cart cart = cartOptional.get();

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }
        Product product = productOptional.get();
        if (product.getQuantity() == 0) {
            throw new APIException(String.format("Product %s is not available", product.getProductName()));
        }
        if (product.getQuantity() < quantity) {
            throw new APIException(String.format("Product %s only has %d left in stock", product.getProductName(), product.getQuantity()));
        }

        Optional<CartItem> cartItemOptional = cartItemRepository.findCartItemByCartIdAndProductId(
                cartId,
                productId
        );
        if (cartItemOptional.isEmpty()) {
            throw new APIException(String.format("Product %s is not available in the cart", product.getProductName()));
        }
        CartItem cartItem = cartItemOptional.get();


        int newQuantity = cartItem.getQuantity() + quantity;
        if (newQuantity > product.getQuantity()) {
            throw new APIException(String.format("Product %s only has %d left in stock", product.getProductName(), product.getQuantity()));
        } else if (newQuantity < 0) {
            throw new APIException(String.format("Quantity for product %s cannot be lower than 0", product.getProductName()));
        } else if (newQuantity == 0) {
            // If we reduce quantity to 0, then delete the CartItem
            return deleteCartItemFromCart(cartId, productId);
        }

        // This is for when product price was updated beforehand
        double cartPriceWithoutCartItem = cart.getTotalPrice() -
                (cartItem.getQuantity() * cartItem.getProductPrice());

        cartItem.setProductPrice(product.getSpecialPrice());
        cartItem.setDiscount(product.getDiscount());

        cartItem.setQuantity(newQuantity);

        cart.setTotalPrice(cartPriceWithoutCartItem + (cartItem.getProductPrice() * newQuantity));

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                        .map(ci -> {
                            ProductDTO productDTO = ProductDTO.fromEntity(ci.getProduct());
                            productDTO.setQuantity(cartItem.getQuantity());
                            return productDTO;
                        }).toList();
        return CartDTO.fromEntity(cart, productDTOS);
    }

    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }
        Cart cart = cartOptional.get();

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }
        Product product = productOptional.get();

        Optional<CartItem> cartItemOptional = cartItemRepository.findCartItemByCartIdAndProductId(
                cartId,
                productId
        );
        if (cartItemOptional.isEmpty()) {
            throw new APIException(String.format("Product %s is not available in the cart", product.getProductName()));
        }
        CartItem cartItem = cartItemOptional.get();

        double cartPriceWithoutCartItem = cart.getTotalPrice() -
                (cartItem.getProductPrice() * cartItem.getQuantity());
        cartItem.setProductPrice(product.getSpecialPrice());
        cartItem.setDiscount(product.getDiscount());
        cartItemRepository.save(cartItem);

        cart.setTotalPrice(cartPriceWithoutCartItem + (cartItem.getProductPrice() * cartItem.getQuantity()));
        cartRepository.save(cart);
    }

    private Cart createCart(Long cartId) {
        Optional<Cart> userCartOptional = cartRepository.findCartByEmailAndCartId(authUtils.loggedInEmail(), cartId);
        if (userCartOptional.isPresent()) {
            return userCartOptional.get();
        }
        Cart newCart = new Cart();
        newCart.setTotalPrice(0.00);
        newCart.setUser(authUtils.loggedInUser());
        return cartRepository.save(newCart);
    }

}
