package projectuas.ukm_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import projectuas.ukm_management.data.entity.User;
import projectuas.ukm_management.data.entity.Event;
import projectuas.ukm_management.service.EventService;
import projectuas.ukm_management.service.UserService;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/ukm")
    public String showLogosList (Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "ukm";
    }

    @GetMapping("/event")
    public String showEventList (Model model) {
        List<Event> events = eventService.getEvents();
        model.addAttribute("events", events);
        return "event";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        String username = authentication.getName();

        System.out.println(username);
        model.addAttribute("ukm", userService.findUserByUsername(username));
        model.addAttribute("events", eventService.getEventByName(username));
        return "ukm/dashboard";
    }

}
