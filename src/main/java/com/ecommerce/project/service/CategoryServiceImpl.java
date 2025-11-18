package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        // Instantiate the Sort class by sortBy and sortOrder setting
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new APIException("There is no category");
        }
        // List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        // List<CategoryDTO> categoryDTOS = categories.stream().map(CategoryDTO::fromEntity).toList(); Method reference can also be usecd
        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> CategoryDTO.fromEntity(category)).toList();
        CategoryResponse categoryResponse = new CategoryResponse(categoryDTOS,
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),
                categoryPage.isLast());
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryDTO.toEntity(categoryDTO);

        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDb != null) {
            throw new APIException(String.format("Category with the name of %s already exists", categoryDTO.getCategoryName()));
        }

        Category savedCategory = categoryRepository.save(category);

        return CategoryDTO.fromEntity(savedCategory);
    }
    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO updatedCategoryDTO) {
        /*
        // We can use for-each loop here because we can modify mutable object, just not remove it or modify immutable type (like primitive types)
        List<Category> categories = categoryRepository.findAll();
        for ( Category category : categories) {
            if (category.getCategoryId().equals(categoryId)) {
                if (Boolean.parseBoolean(newCategory.getCategoryName()) || newCategory.getCategoryName().isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name must be included");
                }
                category.setCategoryName(newCategory.getCategoryName());
                categoryRepository.save(category);
                return "Category updated successfully";
            }
         */
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = CategoryDTO.toEntity(updatedCategoryDTO);
            category.setCategoryId(categoryId);
            Category updatedCategory = categoryRepository.save(category);

            return CategoryDTO.fromEntity(updatedCategory);
        } else {
            throw new ResourceNotFoundException("Category","categoryId", categoryId);
        }
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        // When delete element of a list, we don't use for-each loop, instead we use Iterator or removeIf method
        //  1: Using Iterator O(n)
        /*
        Iterator<Category> iterator = categories.iterator();
        while (iterator.hasNext()) {
            Category category = iterator.next();
            if (category.getCategoryId().equals(categoryId)) {
                iterator.remove();
                break;
            }
        }
         */

        // 2: Using removeIf O(n)
        /*
        boolean removed = categories.removeIf(category -> category.getCategoryId().equals(categoryId));
        if (removed) {
            return "Category deleted successfully";
        }
        return "Category unsuccessfully deleted";
         */

        // 3: Using stream to find the first occurrence, then remove it O(2n) - This approach is great for accessing the object before remove it
        // findFirst() returns an Optional<Category> container object, not the object itself because there is the possibility of not finding it
        // Get the Optional result
        /*
        List<Category> categories = categoryRepository.findAll();
        Optional<Category> categoryOptional = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        // Check if category is present before trying to delete it
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            categoryRepository.delete(category);
            return "Category deleted successfully";
        } else {
            // We throw this specific generic exception because it includes the status code and message that we can warp it to make a response
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category with the ID: %s does not exist", categoryId));
        }
         */

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            categoryRepository.deleteById(categoryId);
            Category deletedCategory = categoryOptional.get();
            return CategoryDTO.fromEntity(deletedCategory);
        }
        // throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category with the ID: %s does not exist", categoryId));
        throw new ResourceNotFoundException("Category","categoryId", categoryId);
    }

}
