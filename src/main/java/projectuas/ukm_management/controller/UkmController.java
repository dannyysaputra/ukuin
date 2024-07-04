package projectuas.ukm_management.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import projectuas.ukm_management.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import projectuas.ukm_management.data.entity.User;
import projectuas.ukm_management.dto.UserDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class UkmController {

    @Autowired
    private UserService userService;

    @GetMapping("/edit-ukm/{id}")
    public String updateUkmForm(@PathVariable("id") Long id, Model model) {
        User ukm = userService.getUserById(id);
        model.addAttribute("ukm", ukm);
        return "/ukm/edit";
    }

    @PostMapping("/edit-ukm/update/{id}")
    public String updateUkm(@Valid @ModelAttribute("ukm") UserDto userDto, @Valid @ModelAttribute("id") Long id,
            BindingResult result,
            Model model) {

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

        User ukm = new User();
        ukm.setName(userDto.getName());
        ukm.setDescription(userDto.getDescription());
        ukm.setVision(userDto.getVision());
        ukm.setMission(userDto.getMission());
        ukm.setEmail(userDto.getEmail());
        ukm.setLogo(fileName);

        userService.update(ukm, id);
        return "redirect:/home";
    }

}
