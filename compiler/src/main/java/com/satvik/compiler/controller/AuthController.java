package com.satvik.compiler.controller;

import com.satvik.compiler.entity.User;
import com.satvik.compiler.security.JwtService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(
            JwtService jwtService
    ) {

        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(
            @RequestBody User user
    ) {

        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        if (
                user.getUsername().trim().equals("satvik")
                        &&
                        user.getPassword().trim().equals("1234")
        ) {

            return jwtService.generateToken(
                    user.getUsername()
            );
        }

        return "Invalid Credentials";
    }
}
