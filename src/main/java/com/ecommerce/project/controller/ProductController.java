package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Add new product
    @Tag(name = "Product APIs", description = "APIs for managing products")
    @Operation(summary = "Add new product", description = "Add a new product to the current authenticated user")
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addNewProduct(@Parameter(description = "ID of the category you wish to add product to") @PathVariable Long categoryId,
                                                    @Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.addNewProduct(categoryId, productDTO), HttpStatus.CREATED);
    }

    // Get all products
    @Tag(name = "Product APIs", description = "APIs for managing products")
    @Operation(summary = "Get all products", description = "Get all available products")
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                          @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                          @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                          @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder) {
        return new ResponseEntity<>(productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    // Get Products by Category
    @Tag(name = "Product APIs", description = "APIs for managing products")
    @Operation(summary = "Get products by category", description = "Get all products with a specified categoryId")
    @GetMapping("/public/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> getProductsByCategory(@Parameter(description = "ID of the category you use to filter products") @PathVariable Long categoryId,
                                                                 @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                 @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder) {
        return new ResponseEntity<>(productService.getProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    // Get Products by keyword
    @Tag(name = "Product APIs", description = "APIs for managing products")
    @Operation(summary = "Get products by keyword", description = "Get all products associated with keyword")
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@Parameter(description = "Keyword you use to filter products") @PathVariable String keyword,
                                                                @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder) {
        return new ResponseEntity<>(productService.getProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    // Update Product
    @Tag(name = "Product APIs", description = "APIs for managing products")
    @Operation(summary = "Update product", description = "Update product")
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Parameter(description = "ID of the product you want to update") @PathVariable Long productId,
                                                    @Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(productId, productDTO), HttpStatus.OK);
    }

    // Delete Product
    @Tag(name = "Product APIs", description = "APIs for managing products")
    @Operation(summary = "Delete product", description = "Delete product")
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<String> deleteProduct(@Parameter(description = "ID of the product you want to delete") @PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

    // Update Product image
    @Tag(name = "Product APIs", description = "APIs for managing products")
    @Operation(summary = "Update product image", description = "Update product image")
    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@Parameter(description = "ID of the product you want to update its image") @PathVariable Long productId,
                                                     @RequestParam("image")MultipartFile image) throws IOException {
        return new ResponseEntity<>(productService.updateProductImage(productId, image), HttpStatus.OK);
    }
}
