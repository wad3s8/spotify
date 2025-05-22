package com.example.spotify.controller;

import com.example.spotify.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private TrackRepository trackRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tracks", trackRepository.findAll());
        return "index";
    }
}
