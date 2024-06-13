package com.hackwiz.pragati.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
public class LoginController {

    @RequestMapping("/user")
    public ResponseEntity loginUser() {

        return new ResponseEntity("User logged in successfully", HttpStatus.OK);
    }
}
