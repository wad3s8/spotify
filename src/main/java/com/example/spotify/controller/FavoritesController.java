package com.example.spotify.controller;

import com.example.spotify.entity.Playlist;
import com.example.spotify.entity.User;
import com.example.spotify.repository.PlaylistRepository;
import com.example.spotify.repository.UserRepository;
import com.example.spotify.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class FavoritesController {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/favorites")
    public String showFavorites(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Playlist favorite = playlistRepository.findByOwnerAndTitle(user, "Любимое");
        if (favorite == null) {
            favorite = new Playlist();
            favorite.setTitle("Любимое");
            favorite.setTracks(Collections.emptyList());
        }


        model.addAttribute("playlist", favorite);
        return "favorites";
    }
}
