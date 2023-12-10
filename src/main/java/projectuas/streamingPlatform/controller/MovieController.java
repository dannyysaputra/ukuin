package projectuas.streamingPlatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projectuas.streamingPlatform.service.MovieService;

@Controller
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movie-details")
    public String movieDetails() {
        return "movie-details";
    }

    @GetMapping("/movie")
    public String showMovie(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movie";
    }

    // handler method to handle homepage request
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "dashboard";
    }
}
