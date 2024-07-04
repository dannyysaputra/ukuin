package projectuas.ukm_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import projectuas.ukm_management.service.UserService;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/ukm")
    public String viewUkm(Model model) {
        model.addAttribute("ukms", userService.findAllUsers());
        return "ukm";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        String username = authentication.getName();
        System.out.println(username);
        model.addAttribute("ukm", userService.findUserByUsername(username));
        return "ukm/dashboard";
    }
    
}
