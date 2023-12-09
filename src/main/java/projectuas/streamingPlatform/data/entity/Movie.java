package projectuas.streamingPlatform.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String movieName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer durationInMinute;

    @Column(nullable = false)
    private String trailerLink;

    @Column(nullable = false)
    private String moviePosterUrl;

    @Column(nullable = false)
    private String movieBackdropUrl;

    @Column(nullable = false)
    private String movieTags;

    @Column(nullable = false)
    private ArrayList<String> genres;
}
