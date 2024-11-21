package com.project.capstone.controller;

import com.project.capstone.dto.FindDto;
import com.project.capstone.entity.Bus;
import com.project.capstone.entity.User;
import com.project.capstone.repository.UserRepository;
import com.project.capstone.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    BusService busService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/home/admin")
    @Secured("ROLE_ADMIN")
    public String adminHome(Model model) {
        model.addAttribute("userDetail", returnUser());
        return "adminHome"; // Returns the admin home view
    }

    @GetMapping("/home/user")
    @Secured("ROLE_USER")
    public String userHome(Model model) {
        model.addAttribute("userDetail", returnUser());
        return "userHome"; // Returns the user home view
    }

    @GetMapping("/searchBuses")
    public String find(Model model){
        model.addAttribute("findDto",new FindDto());
        return "searchBuses";
    }

    @PostMapping("/find")
    public String findBus(@ModelAttribute("findDto")FindDto findDto, Model model){
        List<Bus> option=busService.find(findDto.getSource(),findDto.getDestination(),findDto.getDate());
        model.addAttribute("findBuses",option);
        return "searchBuses";
    }



    private User returnUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername()).get();
        return users;
    }
}
