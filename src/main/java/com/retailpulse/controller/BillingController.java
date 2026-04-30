package com.retailpulse.controller;

import com.retailpulse.model.*;
import com.retailpulse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/billing")
public class BillingController {

    @Autowired private ProductRepository productRepo;
    @Autowired private CustomerRepository customerRepo;
    @Autowired private CartItemRepository cartRepo;
    @Autowired private SaleOrderRepository orderRepo;
    @Autowired private OrderItemRepository orderItemRepo;

    @GetMapping
    public String billing(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        model.addAttribute("user", user);
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("customers", customerRepo.findAll());
        
        List<CartItem> cart = cartRepo.findByUser(user);
        model.addAttribute("cart", cart);
        
        Double total = cart.stream().mapToDouble(c -> c.getProduct().getPrice() * c.getQuantity()).sum();
        model.addAttribute("cartTotal", total);
        
        return "billing";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam Integer quantity, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        Product p = productRepo.findById(productId).orElse(null);
        if (p != null && quantity > 0 && p.getStock() >= quantity) {
            CartItem item = new CartItem(user, p, quantity);
            cartRepo.save(item);
            p.setStock(p.getStock() - quantity);
            productRepo.save(p);
        }
        return "redirect:/billing";
    }

    @Transactional
    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        List<CartItem> cart = cartRepo.findByUser(user);
        for (CartItem item : cart) {
            Product p = item.getProduct();
            p.setStock(p.getStock() + item.getQuantity());
            productRepo.save(p);
        }
        cartRepo.deleteByUser(user);
        return "redirect:/billing";
    }

    @Transactional
    @PostMapping("/checkout")
    public String checkout(@RequestParam Long customerId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        Customer c = customerRepo.findById(customerId).orElse(null);
        List<CartItem> cart = cartRepo.findByUser(user);
        
        if (c != null && !cart.isEmpty()) {
            Double total = cart.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
            SaleOrder order = new SaleOrder(c, total, new Date());
            orderRepo.save(order);
            
            for (CartItem item : cart) {
                OrderItem oi = new OrderItem(order, item.getProduct(), item.getQuantity(), item.getProduct().getPrice());
                orderItemRepo.save(oi);
            }
            cartRepo.deleteByUser(user);
        }
        return "redirect:/dashboard";
    }
}
