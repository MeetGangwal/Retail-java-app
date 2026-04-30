package com.retailpulse.service;

import com.retailpulse.repository.CustomerRepository;
import com.retailpulse.repository.ProductRepository;
import com.retailpulse.repository.SaleOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private CustomerRepository customerRepo;
    
    @Autowired
    private SaleOrderRepository saleOrderRepo;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productRepo.count());
        stats.put("totalCustomers", customerRepo.count());
        stats.put("totalOrders", saleOrderRepo.count());
        
        // Calculate total sales
        Double totalSales = saleOrderRepo.findAll().stream()
            .mapToDouble(order -> order.getTotalAmount() != null ? order.getTotalAmount() : 0.0)
            .sum();
        stats.put("totalSales", totalSales);
        
        return stats;
    }
}
