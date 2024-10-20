package com.brytvich.helpdesk.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sigma")
public class UserController {
    @GetMapping()
    public String sigma(){
        return "sigma";
    }
}
