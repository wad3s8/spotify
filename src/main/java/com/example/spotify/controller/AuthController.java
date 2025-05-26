package com.example.spotify.controller;

import com.example.spotify.entity.Playlist;
import com.example.spotify.entity.User;
import com.example.spotify.repository.PlaylistRepository;
import com.example.spotify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Collections;

@Controller
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private PlaylistRepository playlistRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        // Создание плейлиста "Любимое"
        Playlist favorite = Playlist.builder()
                .title("Любимое")
                .owner(savedUser)
                .tracks(Collections.emptyList())
                .build();

        playlistRepository.save(favorite);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
