package projectuas.streamingPlatform.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectuas.streamingPlatform.data.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // custom query
//    @Query(value = "select * from movie where movie.movie_name like %:keyword%", nativeQuery = true)
//    List<Movie> findByMovieName(@Param("keyword") String keyword);

    List<Movie> findByMovieNameContaining(String keyword);
    List<Movie> findAllByOrderByMovieNameAsc();
    List<Movie> findAllByOrderByRatingAsc();
    List<Movie> findAllByOrderByMovieNameDesc();
    List<Movie> findAllByOrderByRatingDesc();
}
