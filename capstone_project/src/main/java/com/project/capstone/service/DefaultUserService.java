package com.project.capstone.service;

import com.project.capstone.dto.UserRegisteredDTO;
import com.project.capstone.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DefaultUserService extends UserDetailsService {

    User save(UserRegisteredDTO userRegisteredDTO);

}
