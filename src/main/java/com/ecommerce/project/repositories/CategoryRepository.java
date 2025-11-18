package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// JpaRepository<[Object Type], [primary key Type]>
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // JpaRepository will parse the code below to create an implementation (proxy instance) contains that method
    // The code will need to exactly follow the convention: [Type] findBy[fieldName]([field])
    // It's just syntactic sugar
    Category findByCategoryName(String categoryName);
}
