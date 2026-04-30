package com.retailpulse.repository;
import com.retailpulse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Long> {}
