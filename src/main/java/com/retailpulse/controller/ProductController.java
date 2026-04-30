package com.retailpulse.controller;

import com.retailpulse.model.Product;
import com.retailpulse.model.User;
import com.retailpulse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping
    public String products(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Product> products = productRepo.findAll();
        long lowStockCount = products.stream().filter(p -> p.getStock() != null && p.getStock() < 10).count();

        model.addAttribute("user", user);
        model.addAttribute("products", products);
        model.addAttribute("lowStockCount", lowStockCount);
        return "products";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name, @RequestParam Double price, @RequestParam Integer stock, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        if (name != null && !name.trim().isEmpty() && price != null && price >= 0 && stock != null && stock >= 0) {
            productRepo.save(new Product(name.trim(), price, stock));
        }
        return "redirect:/products";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @RequestParam String name, @RequestParam Double price, @RequestParam Integer stock, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        Product p = productRepo.findById(id).orElse(null);
        if (p != null && name != null && !name.trim().isEmpty() && price != null && price >= 0 && stock != null && stock >= 0) {
            p.setName(name.trim());
            p.setPrice(price);
            p.setStock(stock);
            productRepo.save(p);
        }
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        productRepo.deleteById(id);
        return "redirect:/products";
    }
}
