package projectuas.ukm_management.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ukms")
public class Ukm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String vision;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mission;

    @Lob
    @Column(nullable = false)
    private byte[] logo;

    // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ukm")
    // private Set<Event> events;
}
