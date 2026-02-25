package com.tapsyrys.tapsyrys.Repositories;

import com.tapsyrys.tapsyrys.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByNameContainingIgnoreCase(String name);
    List<Product> findAllByCategoryContainingIgnoreCase(String category);
}
