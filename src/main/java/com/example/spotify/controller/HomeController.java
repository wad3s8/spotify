package com.example.spotify.controller;

import com.example.spotify.entity.Playlist;
import com.example.spotify.repository.PlaylistRepository;
import com.example.spotify.repository.TrackRepository;
import com.example.spotify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @GetMapping("/")
    public String index(Model model,
                        @AuthenticationPrincipal com.example.spotify.security.CustomUserDetails userDetails) {

        model.addAttribute("tracks", trackRepository.findAll());

        // Получаем пользователя
        com.example.spotify.entity.User user = userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Получаем плейлисты пользователя
        List<Playlist> playlists = playlistRepository.findByOwner(user);
        model.addAttribute("playlists", playlists); // 💥 Обязательно!

        return "index";
    }

}
