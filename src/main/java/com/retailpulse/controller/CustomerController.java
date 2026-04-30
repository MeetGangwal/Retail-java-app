package com.retailpulse.controller;

import com.retailpulse.model.Customer;
import com.retailpulse.model.User;
import com.retailpulse.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping
    public String customers(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("customers", customerRepo.findAll());
        return "customers";
    }

    @PostMapping("/add")
    public String addCustomer(@RequestParam String name, @RequestParam String email, @RequestParam String phone, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        if (name != null && !name.trim().isEmpty() && email != null && !email.trim().isEmpty() && phone != null && !phone.trim().isEmpty()) {
            Customer c = new Customer();
            c.setName(name.trim());
            c.setEmail(email.trim());
            c.setPhone(phone.trim());
            customerRepo.save(c);
        }
        return "redirect:/customers";
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, @RequestParam String name, @RequestParam String email, @RequestParam String phone, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        Customer c = customerRepo.findById(id).orElse(null);
        if (c != null && name != null && !name.trim().isEmpty() && email != null && !email.trim().isEmpty() && phone != null && !phone.trim().isEmpty()) {
            c.setName(name.trim());
            c.setEmail(email.trim());
            c.setPhone(phone.trim());
            customerRepo.save(c);
        }
        return "redirect:/customers";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        customerRepo.deleteById(id);
        return "redirect:/customers";
    }
}
