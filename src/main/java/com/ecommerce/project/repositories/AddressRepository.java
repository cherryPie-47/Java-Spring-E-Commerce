package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.user.email = ?1")
    List<Address> findAllByEmail(String email);

    @Query("SELECT a FROM Address a WHERE a.id = ?1 AND a.user.email = ?2")
    Optional<Address> findByIdAndEmail(Long addressId, String email);
}
