package com.hackwiz.pragati.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

        @RequestMapping("/ping")
        public String ping() {
            return "I am alive!";
        }

}
