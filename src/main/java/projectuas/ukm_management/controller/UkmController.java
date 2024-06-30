package projectuas.ukm_management.controller;

import jakarta.validation.Valid;
import projectuas.ukm_management.data.entity.Ukm;
import projectuas.ukm_management.service.UkmService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UkmController {

    private UkmService ukmService;

    public UkmController(UkmService ukmService) {
        this.ukmService = ukmService;
    }

    @GetMapping("/admin/ukm-form")
    public String showUkmForm(Model model) {
        Ukm ukm = new Ukm();
        model.addAttribute("ukms", ukm);
        return "ukm/register_ukm";
    }

    @PostMapping("/admin/movie-form/save")
    public String addUkm(@Valid @ModelAttribute("ukms") Ukm ukm,
            BindingResult result,
            Model model) {
        ukmService.pushUkm(ukm);
        return "redirect:/ukm";
    }

    @GetMapping("/ukm-details/{id}")
    public String ukmDetails(@PathVariable("id") Long ukmId, Model model) {
        Ukm ukm = ukmService.getUkmById(ukmId);

        model.addAttribute("ukm", ukm);
        model.addAttribute("ukms", ukmService.getAllUkms());

        return "detail_ukm";
    }

    @GetMapping("/admin/ukm")
    public String showUkm(Model model) {
        model.addAttribute("ukms", ukmService.getAllUkms());
        return "ukm";
    }

    // handler method to handle homepage request
    @GetMapping("/home")
    public String dashboard(Ukm movie, Model model) {
        // if (keyword != null) {
        // List<Ukm> movieSearch = ukmService.getByUkmName(keyword);
        // model.addAttribute("movies", movieSearch);
        // } else {
        // model.addAttribute("movies", ukmService.getAllUkms());
        // }
        model.addAttribute("ukms", ukmService.getAllUkms());
        return "ukm/dashboard";
    }

    @GetMapping("/admin/ukm/delete/{id}")
    public String deleteUkmById(@PathVariable("id") Long id, Model model) {
        ukmService.deleteUkm(id);
        return "redirect:/ukm";
    }

    @GetMapping("/admin/ukm-form/{id}")
    public String updateUkm(@PathVariable("id") Long id, Model model) {
        Ukm ukm = ukmService.getUkmById(id);
        model.addAttribute("ukm", ukm);
        return "/ukm/register_ukm";
    }

    @PostMapping("/admin/ukm-form/update/{id}")
    public String updateUkm(@Valid @ModelAttribute("ukm") Ukm ukm, @Valid @ModelAttribute("id") Long id,
            BindingResult result,
            Model model) {
        ukmService.updateUkm(ukm, id);
        return "redirect:/ukm";
    }

    // @GetMapping({"/movies", "/s"})
    // public String movies(Model model, @RequestParam(required = false) String
    // sort,
    // @RequestParam(required = false) String keyword) {
    // List<Ukm> movies;

    // if (keyword != null) {
    // movies = ukmService.getByUkmName(keyword);
    // } else {
    // if ("nameAsc".equals(sort)) {
    // movies = ukmService.getByUkmNameAsc();
    // } else if ("nameDesc".equals(sort)) {
    // movies = ukmService.getByUkmNameDesc();
    // } else {
    // movies = ukmService.getAllUkms();
    // }
    // }

    // model.addAttribute("movies", movies);
    // return "movies";
    // }

    // @GetMapping("/movies/sortedByNameAsc")
    // public String getUkmsSortedByNameAsc(Model model) {
    // List<Ukm> movies = ukmService.getByUkmNameAsc();
    // model.addAttribute("movies", movies);
    // return "movies";
    // }
}
