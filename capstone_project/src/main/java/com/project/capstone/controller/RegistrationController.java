package com.project.capstone.controller;

import com.project.capstone.dto.UserRegisteredDTO;
import com.project.capstone.service.DefaultUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/registration")
@Tag(name = "Registration Controller", description = "Controller for managing user registration")
public class RegistrationController {

    private DefaultUserService userService;

    public RegistrationController(DefaultUserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegisteredDTO userRegistrationDto() {
        return new UserRegisteredDTO();
    }

    @GetMapping
    @Operation(summary = "Show Registration Form", description = "Displays the registration form")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping
    @Operation(summary = "Register User Account", description = "Registers a new user account and redirects to the login page")
    public String registerUserAccount(@ModelAttribute("user")
                                      UserRegisteredDTO registrationDto) {
        userService.save(registrationDto);
        return "redirect:/login";
    }
}