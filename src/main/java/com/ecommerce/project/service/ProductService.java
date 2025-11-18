package com.ecommerce.project.service;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addNewProduct(Long productId, ProductDTO productDTO);
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
    void deleteProduct(Long productId);
    ProductResponse getProductBySeller(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    Long getProductCount();
}
