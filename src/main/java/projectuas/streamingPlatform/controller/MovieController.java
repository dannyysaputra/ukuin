package projectuas.streamingPlatform.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import projectuas.streamingPlatform.data.entity.Movie;
import projectuas.streamingPlatform.service.MovieService;

import java.util.List;

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
        model.addAttribute("movies", movieService.getAllMovies());

        return "movie-details";
    }

    @GetMapping("/movie")
    public String showMovie(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movie";
    }

    // handler method to handle homepage request
    @GetMapping({"/dashboard", "/search"})
    public String dashboard(Movie movie, Model model, String keyword) {
        if (keyword != null) {
            List<Movie> movieSearch = movieService.getByMovieName(keyword);
            model.addAttribute("movies", movieSearch);
        } else {
            model.addAttribute("movies", movieService.getAllMovies());
        }
        return "dashboard";
    }

    @GetMapping("movie/delete/{id}")
    public String deleteMovieById(@PathVariable("id") Long id, Model model) {
        Movie movie = movieService.getMovieById(id);

        movieService.deleteMovie(id);
        return "redirect:/movie";
    }

    @GetMapping("movie-form/{id}")
    public String updateMovie(@PathVariable("id") Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movie-form-update";
    }

    @PostMapping("/movie-form/update/{id}")
    public String updateMovie(@Valid @ModelAttribute("movie") Movie movie, @Valid @ModelAttribute("id") Long id,
                           BindingResult result,
                           Model model) {
        movieService.updateMovie(movie, id);
        return "redirect:/movie";
    }
}
