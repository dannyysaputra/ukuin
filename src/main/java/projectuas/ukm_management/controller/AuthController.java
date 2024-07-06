package projectuas.ukm_management.controller;

import jakarta.validation.Valid;
import projectuas.ukm_management.data.entity.User;
import projectuas.ukm_management.data.repository.UserRepository;
import projectuas.ukm_management.dto.UserDto;
import projectuas.ukm_management.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "ukm/login";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/detail-ukm/{id}")
    public String detailukm(@PathVariable("id") Long id , Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "detail_ukm";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "ukm/register_ukm";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute UserDto userDto, BindingResult result) {
        if (userDto.getLogo().isEmpty()) {
            result.addError(new FieldError("userDto", "logo", "The image is empty"));
        }

        if (result.hasErrors()) {
            return "ukm/register_ukm";
        }

        MultipartFile file = userDto.getLogo();
        String fileName = file.getOriginalFilename();

        try {
            String uploadDir = "public/logos/";
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

        User user = new User();
        user.setName(userDto.getName());
        user.setDescription(userDto.getDescription());
        user.setVision(userDto.getVision());
        user.setMission(userDto.getMission());
        user.setEmail(userDto.getEmail());
        user.setLogo(fileName);
        user.setPassword(userDto.getPassword());
        // System.out.println(user);

        userService.saveUser(user);

        return "redirect:/home";
    }
}
