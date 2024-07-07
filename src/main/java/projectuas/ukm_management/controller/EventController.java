package projectuas.ukm_management.controller;

import java.nio.file.Files;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import projectuas.ukm_management.data.entity.Event;
import projectuas.ukm_management.data.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import projectuas.ukm_management.dto.EventDto;
import projectuas.ukm_management.service.EventService;
import projectuas.ukm_management.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

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

    @GetMapping("/edit-event/{id}")
    public String updateEventForm(@PathVariable("id") Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "/ukm/edit-event";
    }

    @GetMapping("/event-saya")
    public String getEventsByUkm(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        List<Event> events = eventService.getEventByUkm(user);

        model.addAttribute("ukm", user);
        model.addAttribute("events", events);
        return "ukm/event-saya";
    }

    @GetMapping("/event-delete/{eventId}")
    public String deleteEvent(@PathVariable Long eventId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);

        logger.info("Attempting to delete event with ID: {}", eventId);
        logger.info("Authenticated user: {}", username);

        Event event = eventService.getEventById(eventId);

        if (event != null) {
            logger.info("Event found with ID: {} and User: {}", eventId, event.getUser().getName());

            if (event.getUser().equals(user)) {
                eventService.deleteEventById(eventId);
                logger.info("Event deleted successfully with ID: {}", eventId);
                return "redirect:/home";
            } else {
                logger.warn("User {} is not authorized to delete event with ID: {}", username, eventId);
            }
        } else {
            logger.warn("Event not found with ID: {}", eventId);
        }

        return "redirect:/home";
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

    @PostMapping("/edit-event/update/{id}")
    public String updateEvent(@Valid @ModelAttribute("event") EventDto eventDto,
            @Valid @ModelAttribute("id") Long id,
            BindingResult result,
            Model model,
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

        eventService.update(event, id);
        return "redirect:/home";
    }

}
