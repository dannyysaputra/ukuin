package projectuas.streamingPlatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projectuas.streamingPlatform.dto.UserDto;

@Controller
public class MovieController {

    @GetMapping("/movie-details")
    public String movieDetails() {
        return "movie-details";
    }

    // handler method to handle homepage request
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "dashboard";
    }
}
