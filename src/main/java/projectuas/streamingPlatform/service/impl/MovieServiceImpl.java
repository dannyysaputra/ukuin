package projectuas.streamingPlatform.service.impl;

import org.springframework.stereotype.Service;
import projectuas.streamingPlatform.data.entity.Movie;
import projectuas.streamingPlatform.data.repository.MovieRepository;
import projectuas.streamingPlatform.service.MovieService;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
//    private MovieService movieService;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
//        this.movieService = movieService;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long movie_id) {
        return movieRepository.findById(movie_id).orElse(null);
    }

    @Override
    public Movie pushMovie(Movie newMovie) {
        return movieRepository.save(newMovie);
    }

    @Override
    public List<Movie> getByMovieName(String movieName) {
        return movieRepository.findByMovieNameContaining(movieName);
    }

    @Override
    public List<Movie> getByMovieNameAsc() {
        return movieRepository.findAllByOrderByMovieNameAsc();
    }

    @Override
    public List<Movie> getByMovieNameDesc() {
        return movieRepository.findAllByOrderByMovieNameDesc();
    }

    @Override
    public List<Movie> getByRatingAsc() {
        return movieRepository.findAllByOrderByRatingAsc();
    }

    @Override
    public List<Movie> getByRatingDesc() {
        return movieRepository.findAllByOrderByRatingDesc();
    }

    @Override
    public Movie updateMovie(Movie updatedMovie, Long movie_id) {
        return movieRepository.findById(movie_id).map(movie -> {
            movie.setMovieName(updatedMovie.getMovieName());
            movie.setMovieName(updatedMovie.getMovieName());
            movie.setYear(updatedMovie.getYear());
            movie.setMovieBackdropUrl(updatedMovie.getMovieBackdropUrl());
            movie.setMoviePosterUrl(updatedMovie.getMoviePosterUrl());
            movie.setGenre(updatedMovie.getGenre());
            movie.setDurationInMinute(updatedMovie.getDurationInMinute());
            movie.setRating(updatedMovie.getRating());
            movie.setTrailerLink(updatedMovie.getTrailerLink());
            movie.setDescription(updatedMovie.getDescription());
            return movieRepository.save(movie);
        }).orElse(null);
    }

    @Override
    public void deleteMovie(Long movie_id) {

    }
}
