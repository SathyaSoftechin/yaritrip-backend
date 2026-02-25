package com.example.demo.controller;
import com.example.demo.service.UserService;

import com.example.demo.dto.UserProfileResponse;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

  @GetMapping("/me")
public UserProfileResponse getCurrentUser(Authentication authentication) {

    System.out.println(authentication.getPrincipal());

    String email = authentication.getName();
    // String name = authentication.getMobile();

    return userService.getUserProfile(email);
}

}
