package projectuas.streamingPlatform.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projectuas.streamingPlatform.data.entity.Movie;
import projectuas.streamingPlatform.service.MovieService;

@Controller
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movie-form")
    public String showMovieForm(Model model){
        Movie movie = new Movie();
        model.addAttribute("movies", movie);
        return "movie-form";
    }

    @PostMapping("/movie-form/save")
    public String addMovie(@Valid @ModelAttribute("movies") Movie movie,
                           BindingResult result,
                           Model model) {
        movieService.pushMovie(movie);
        return "redirect:/movie";
    }

    @GetMapping("/movie-details/{id}")
    public String movieDetails(@PathVariable("id") Long movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        System.out.println(movie);
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
