package com.shop.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<html><body style='display:flex;justify-content:center;align-items:center;height:100vh;'>" +
               "<img src='https://picsum.photos/800/600' alt='Welcome'/></body></html>";
    }
}
