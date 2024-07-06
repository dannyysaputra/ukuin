package projectuas.ukm_management.dto;

import java.util.Date;

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
public class EventDto
{
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;
    
    @NotEmpty(message = "Description should not be empty")
    private String description;

    private int price;

    private MultipartFile poster;

    private Date start_date;

    private Date end_date;
}
