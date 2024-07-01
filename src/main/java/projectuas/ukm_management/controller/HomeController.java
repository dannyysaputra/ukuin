package projectuas.ukm_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import projectuas.ukm_management.service.UkmService;

@Controller
public class HomeController {

    @Autowired
    private UkmService ukmService;

    @GetMapping("/ukm")
    public String viewUkm(Model model) {
        model.addAttribute("ukms", ukmService.getAllUkms());
        return "ukm";
    }
}
