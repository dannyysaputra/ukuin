package projectuas.streamingPlatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {


    @GetMapping("/movie-details")
    public String movieDetails() {
        return "movie-details";
    }

    @GetMapping("/movie")
    public String showMovie() {
        return "movie";
    }

    // handler method to handle homepage request
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
