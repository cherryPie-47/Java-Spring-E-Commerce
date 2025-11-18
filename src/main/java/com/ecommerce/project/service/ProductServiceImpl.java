package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final CartRepository cartRepository;
    private final CartService cartService;

    @Value("${project.image}")
    private String path;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              FileService fileService,
                              CartRepository cartRepository, CartService cartService){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    // Add New Product
    @Override
    public ProductDTO addNewProduct(Long categoryId, ProductDTO productDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
        }
        Optional<Product> productOptional = productRepository.findByProductName(productDTO.getProductName());
        if (productOptional.isPresent()) {
            throw new APIException(String.format("Product with productName: %s is already existed", productDTO.getProductName()));
        }
        // Sequential Scanning method (Collection Load) is inferior to DB Query
//        List<Product> products = categoryOptional.get().getProducts();
//        for (Product product : products) {
//            if (product.getProductName().equals(productDTO.getProductName())) {
//                throw new APIException(String.format("Product with productName: %s is already existed", productDTO.getProductName()));
//            }
//        }
        Product product = ProductDTO.toEntity(productDTO);
        product.setCategory(categoryOptional.get());
        double specialPrice = productDTO.getPrice() * ((100 - productDTO.getDiscount()) / 100);
        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);
        return ProductDTO.fromEntity(savedProduct);
    }

    // Get All Product
    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageDetails);

        List<Product> products = productPage.getContent();
        if (products.isEmpty()) {
            throw new APIException("There is no product");
        }
        List<ProductDTO> productDTOS = products.stream().map(product -> ProductDTO.fromEntity(product)).toList();
        return new ProductResponse(productDTOS,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast());

//        List<Product> products = productRepository.findAll();
//        if (products.isEmpty()) {
//            throw new APIException("There is no product");
//        }
//        List<ProductDTO> productDTOS = products.stream().map(product -> ProductDTO.fromEntity(product)).toList();
//        return new ProductResponse(productDTOS);
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new ResourceNotFoundException("Category", "categoryId", categoryId);
        }

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productsPage = productRepository.findAllByCategory(categoryOptional.get(), pageDetails);

        List<Product> products = productsPage.getContent();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product", "categoryId", categoryId);
        }
        List<ProductDTO> productDTOS = products.stream().map(product -> ProductDTO.fromEntity(product)).toList();
        return new ProductResponse(productDTOS,
                productsPage.getNumber(),
                productsPage.getSize(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                productsPage.isLast());
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> productsPage = productRepository.findAllByProductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);

        List<Product> products = productsPage.getContent();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product", "keyword", keyword);
        }
        List<ProductDTO> productDTOS = products.stream().map(product -> ProductDTO.fromEntity(product)).toList();
        return new ProductResponse(productDTOS,
                productsPage.getNumber(),
                productsPage.getSize(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                productsPage.isLast());
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        Product updatedProduct = ProductDTO.toEntity(productDTO);
        Product productFromDb = productOptional.get();

        if (updatedProduct.getProductName() != null) {
            productFromDb.setProductName(updatedProduct.getProductName());
        }
        if (updatedProduct.getDescription() != null) {
            productFromDb.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getQuantity() != null) {
            productFromDb.setQuantity(updatedProduct.getQuantity());
        }
        if (updatedProduct.getPrice() != 0.0) {
            productFromDb.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getDiscount() != 0.0) {
            productFromDb.setDiscount(updatedProduct.getDiscount());
        }
        productFromDb.setSpecialPrice(productFromDb.getPrice() * ((100 - productFromDb.getDiscount()) / 100));
        Product savedProduct = productRepository.save(productFromDb);

        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        List<CartDTO> cartDTOS = carts.stream().map(cart -> {
            List<ProductDTO> productDTOS = cart.getCartItems().stream().map(
                    cartItem -> ProductDTO.fromEntity(cartItem.getProduct()))
                    .toList();
            return CartDTO.fromEntity(cart, productDTOS);
        }).toList();

        cartDTOS.forEach(cartDTO -> cartService.updateProductInCarts(cartDTO.getCartId(), productId));

        return ProductDTO.fromEntity(savedProduct);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        String path = "images/";
        String fileName = fileService.uploadImage(path, image);

        Product productFromDb = productOptional.get();
        productFromDb.setImage(fileName);

        return ProductDTO.fromEntity(productRepository.save(productFromDb));
    }



    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }
        Product product = productOptional.get();

        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        carts.forEach(cart -> cartService.deleteCartItemFromCart(cart.getCartId(), productId));

        productRepository.delete(productOptional.get());
    }

    @Override
    public ProductResponse getProductBySeller(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public Long getProductCount() {
        return 0L;
    }
}
