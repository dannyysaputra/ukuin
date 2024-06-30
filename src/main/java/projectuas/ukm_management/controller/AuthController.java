package projectuas.ukm_management.controller;

import jakarta.validation.Valid;
import projectuas.ukm_management.data.entity.User;
import projectuas.ukm_management.dto.UserDto;
import projectuas.ukm_management.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "ukm/register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        // User existingEmail = userService.findUserByEmail(userDto.getEmail());
        // User existingUsername = userService.findUserByUsername(userDto.getUsername());
        // Optional<User> existingEmail = Optional.ofNullable(userService.findUserByEmail(userDto.getEmail()));
        // Optional<User> existingUsername = Optional.ofNullable(userService.findUserByUsername(userDto.getUsername()));

        // if (existingEmail.isPresent() && existingEmail.get().getEmail() != null
        //         && !existingEmail.get().getEmail().isEmpty()) {
        //     result.rejectValue("email", null,
        //             "There is already an account registered with the same email");

        // }

        // if (existingUsername.isPresent() && existingUsername.get().getUsername() != null
        //         && !existingUsername.get().getUsername().isEmpty()) {
        //     result.rejectValue("username", null,
        //             "There is already an account registered with the same username");
        // }

        // if (existingEmail != null && existingEmail.getEmail() != null && !existingEmail.getEmail().isEmpty()) {
        //     result.rejectValue("email", null,
        //             "There is already an account registered with the same email");
        // }

        // if (existingUsername != null && existingUsername.getUsername() != null
        //         && !existingUsername.getUsername().isEmpty()) {
        //     result.rejectValue("username", null,
        //             "There is already an account registered with the same username");
        // }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            model.addAttribute("errorMessage", "Error");
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/login";
    }

    // handler method to handle list of users
    // @GetMapping("/users")
    // public String users(Model model) {
    //     List<UserDto> users = userService.findAllUsers();
    //     model.addAttribute("users", users);
    //     return "templates/users";
    // }
}
