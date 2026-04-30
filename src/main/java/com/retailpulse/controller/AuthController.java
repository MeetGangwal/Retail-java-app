package com.retailpulse.controller;

import com.retailpulse.model.User;
import com.retailpulse.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String landing(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) return "redirect:/dashboard";
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        if (session.getAttribute("user") != null) return "redirect:/dashboard";
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            authService.registerUser(user);
            model.addAttribute("successMsg", "Registration successful. Please login.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Email already registered. Please login.");
            return "register";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = authService.loginUser(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }
        model.addAttribute("errorMsg", "Invalid email or password.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
