package projectuas.streamingPlatform.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectuas.streamingPlatform.data.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
