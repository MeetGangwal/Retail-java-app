package com.retailpulse.repository;
import com.retailpulse.model.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long> {
}
