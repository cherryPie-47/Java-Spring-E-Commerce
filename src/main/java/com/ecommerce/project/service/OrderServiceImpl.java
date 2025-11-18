package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.*;
import com.ecommerce.project.payload.*;
import com.ecommerce.project.repositories.*;
import com.ecommerce.project.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            PaymentRepository paymentRepository, CartRepository cartRepository, AddressRepository addressRepository, ProductRepository productRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    @Transactional
    @Override
    public OrderDTO placeOrder(String paymentMethod, String email, OrderRequestDTO orderRequestDTO) {
        // Getting User Cart
        Optional<Cart> cartOptional = cartRepository.findCartByEmail(email);
        if (cartOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cart", "email", email);
        }
        Cart cart = cartOptional.get();
        Long addressId = orderRequestDTO.getAddressId();
        String pgPaymentId = orderRequestDTO.getPgPaymentId();
        String pgStatus = orderRequestDTO.getPgStatus();
        String pgResponseMessage = orderRequestDTO.getPgResponseMessage();
        String pgName = orderRequestDTO.getPgName();
        Optional<Address> addressOptional = addressRepository.findByIdAndEmail(addressId, email);
        if(addressOptional.isEmpty()) {
            throw new ResourceNotFoundException("Address", "addressId", addressId);
        }
        Address address = addressOptional.get();

        // Create a new order with payment info
        Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted");
        order.setAddress(address);

        Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
            // We have a helper method to ensure the link between Order and Payment
        order.setPayment(payment);

            // Because of persist cascade, we only need to save Payment
        Payment savedPayment = paymentRepository.save(payment);

        // Get items from the cart into the order items
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new APIException("Cart is empty");
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedPrice(cartItem.getProductPrice());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }
        // This line is actually redundant, as order is now marked as Managed, and its corresponding orderItem will also be cascaded
        // (provided if there is a cascade option for orderItem, and we didn't use GenerationType.IDENTITY, as IDENTITY make the entity flush immediately upon calling .save())
        orderItemRepository.saveAll(orderItems);


        List<Long> cartItemIdsToDelete = cart.getCartItems().stream()
                .map(cartItem -> {
            // Update the stock
            int orderedQuantity = cartItem.getQuantity();
            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity() - orderedQuantity);
            productRepository.save(product);

            // Get the Ids to delete
            return product.getProductId();
        }).toList();

        // Clear the cart
        cartItemIdsToDelete.forEach(productId ->
                cartService.deleteCartItemFromCart(cart.getCartId(), productId));

        // Send back the order summary
        List<OrderItemDTO> orderItemDTOS = orderItems.stream().map(orderItem -> {
            ProductDTO productDTO = ProductDTO.fromEntity(orderItem.getProduct());
            OrderItemDTO orderItemDTO = OrderItemDTO.fromEntity(orderItem, productDTO);
            return orderItemDTO;
        }).toList();
        return OrderDTO.fromEntity(order, orderItemDTOS, PaymentDTO.fromEntity(payment));
    }
}
