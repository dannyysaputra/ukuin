package projectuas.ukm_management.controller;

import java.nio.file.Files;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import projectuas.ukm_management.data.entity.Event;
import projectuas.ukm_management.data.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import projectuas.ukm_management.dto.EventDto;
import projectuas.ukm_management.service.EventService;
import projectuas.ukm_management.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/buat-event")
    public String eventForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        EventDto eventDto = new EventDto();

        model.addAttribute("event", eventDto);
        model.addAttribute("ukm", userService.findUserByUsername(username));

        return "ukm/create_event";
    }

    @PostMapping("/create-event")
    public String create(@Valid @ModelAttribute EventDto eventDto,
            BindingResult result,
            Authentication authentication) {
        if (eventDto.getPoster().isEmpty()) {
            result.addError(new FieldError("eventDto", "poster", "The image is empty."));
        }

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> {
                System.out.println("Field: " + error.getField() + " - " + error.getDefaultMessage());
            });
            return "ukm/create_event";
        }

        MultipartFile file = eventDto.getPoster();
        String fileName = file.getOriginalFilename();

        try {
            String uploadDir = "public/posters/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Boolean isPaid = eventDto.getPrice() != 0;

        String username = authentication.getName();
        User user = userService.findUserByUsername(username);

        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setPrice(eventDto.getPrice());
        event.set_paid(isPaid);
        event.setPoster(fileName);
        event.setStart_date(eventDto.getStart_date());
        event.setEnd_date(eventDto.getEnd_date());
        event.setUser(user);
        
        eventService.saveEvent(event);
        return "redirect:/home";
    }

}
