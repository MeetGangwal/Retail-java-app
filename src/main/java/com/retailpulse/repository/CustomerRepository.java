package com.retailpulse.repository;
import com.retailpulse.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long> {}
