package org.ai.authmodule.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public String profile(Authentication authentication) {

        return "Logged In User : "
                + authentication.getName();
    }
}
