package com.retailpulse.controller;

import com.retailpulse.model.SaleOrder;
import com.retailpulse.model.User;
import com.retailpulse.repository.CustomerRepository;
import com.retailpulse.repository.OrderItemRepository;
import com.retailpulse.repository.ProductRepository;
import com.retailpulse.repository.SaleOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReportsController {

    @Autowired private SaleOrderRepository saleOrderRepo;
    @Autowired private OrderItemRepository orderItemRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private CustomerRepository customerRepo;

    @GetMapping("/reports")
    public String reports(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<SaleOrder> orders = saleOrderRepo.findAll();

        Double totalSales = orders.stream()
                .mapToDouble(o -> o.getTotalAmount() != null ? o.getTotalAmount() : 0.0)
                .sum();

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", orders.size());
        model.addAttribute("totalProducts", productRepo.count());
        model.addAttribute("totalCustomers", customerRepo.count());
        
        model.addAttribute("customersList", customerRepo.findAll());
        model.addAttribute("productsList", productRepo.findAll());

        return "reports";
    }
}
