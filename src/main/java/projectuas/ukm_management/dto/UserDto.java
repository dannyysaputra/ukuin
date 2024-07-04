package projectuas.ukm_management.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    // private Long id;
    // @NotEmpty
    // private String name;
    // @NotEmpty(message = "Username should not be empty")
    // private String username;

    private String role;
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;
    
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotEmpty(message = "vision should not be empty")
    private String vision;

    @NotEmpty(message = "Mission should not be empty")
    private String mission;

    private MultipartFile logo;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
}