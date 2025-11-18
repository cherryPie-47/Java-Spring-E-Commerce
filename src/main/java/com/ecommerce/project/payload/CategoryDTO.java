package com.ecommerce.project.payload;

import com.ecommerce.project.model.Category;

// This is a wrapper class to contain all categoryDTO in a list
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;

    public CategoryDTO() {
    }

    public CategoryDTO(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    // To convert DTO -> Entity, I could either have a method in DTO class or a static method on the Entity class
    // Have the method in DTO is preferable as it would separate unnecessary method from Entity class
    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        return category;
    }

    public static CategoryDTO fromEntity(Category category) {
        // Because DTO is just a temporary POJO serve as a data holder, so asking Spring to manage its creation would
        // be needless overhead
        // Manually instantiate the category DTO
        CategoryDTO categoryDTO = new CategoryDTO();

        // Set the field of that instance
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setCategoryId(category.getCategoryId());

        return categoryDTO;
    }
}
