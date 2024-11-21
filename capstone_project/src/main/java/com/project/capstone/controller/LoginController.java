package com.project.capstone.controller;

import com.project.capstone.dto.UserLoginDTO;
import com.project.capstone.service.DefaultUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Tag(name = "Login Controller", description = "Controller for managing user login")
public class LoginController {
    @Autowired
    private DefaultUserService userService;

    @ModelAttribute("user")
    public UserLoginDTO userLoginDTO() {
        return new UserLoginDTO();
    }

    @GetMapping
    @Operation(summary = "Login Page", description = "Displays the login page")
    public String login() {
        return "login";
    }

    @PostMapping
    @Operation(summary = "Login User", description = "Logs in the user with the provided credentials")
    public void  loginUser(@ModelAttribute("user")
                           UserLoginDTO userLoginDTO) {
        userService.loadUserByUsername(userLoginDTO.getUsername());
    }
}

