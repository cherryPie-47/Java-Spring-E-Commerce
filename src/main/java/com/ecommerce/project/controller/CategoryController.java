package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {
    // Add an object of categoryService to this controller, to use the method in that object
    // The dependency field is the interface type
    private final CategoryService categoryService;


    // The dependency (service) is injected to the controller
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(summary = "Get all categories", description = "Get all available categories")
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                             @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_CATEGORIES_BY, required = false) String sortBy,
                                                             @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder){
        return new ResponseEntity<>(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK );
    }

    // @RequestBody [ClassName ObjectName] will parse data from request body and create a specified object, using setter and getter methods
    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(summary = "Create category", description = "API to create new category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO status = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(summary = "Update category", description = "API to update an existing category")
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Parameter(description = "Category that you wish to update") @PathVariable Long categoryId,
                                                      @Valid @RequestBody CategoryDTO updatedCategoryDTO) {
        CategoryDTO status = categoryService.updateCategory(categoryId, updatedCategoryDTO);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @Tag(name = "Category APIs", description = "APIs for managing categories")
    @Operation(summary = "Delete category", description = "API to delete a category")
    @DeleteMapping("/admin/categories/{categoryId}")
    // Instead of returning a generic exception, we extract the status code and message, warped it by ResponseEntity class
    // For DispatcherServlet to pick it up and generate a modified error response for us
    public ResponseEntity<CategoryDTO> deleteCategory(@Parameter(description = "Category that you wish to delete") @PathVariable Long categoryId) {
        CategoryDTO status = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
