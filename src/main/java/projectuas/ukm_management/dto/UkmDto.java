package projectuas.ukm_management.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UkmDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotEmpty(message = "vision should not be empty")
    private String vision;

    @NotEmpty(message = "Mission should not be empty")
    private String mission;

    private MultipartFile logo;
}
