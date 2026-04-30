package com.retailpulse.controller;

import com.retailpulse.model.User;
import com.retailpulse.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        model.addAttribute("user", user);
        model.addAttribute("stats", dashboardService.getDashboardStats());
        return "dashboard";
    }
}
