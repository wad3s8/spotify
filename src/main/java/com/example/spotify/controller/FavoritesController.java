package com.example.spotify.controller;

import com.example.spotify.entity.Playlist;
import com.example.spotify.entity.User;
import com.example.spotify.repository.PlaylistRepository;
import com.example.spotify.repository.UserRepository;
import com.example.spotify.security.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FavoritesController {

    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/favorites")
    public String showFavorites(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) throws JsonProcessingException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Playlist favorite = playlistRepository.findByOwnerAndTitle(user, "Любимое");

        if (favorite == null) {
            favorite = new Playlist();
            favorite.setTitle("Любимое");
            favorite.setTracks(Collections.emptyList());
        }

        model.addAttribute("playlist", favorite);

        List<Map<String, String>> trackList = favorite.getTracks().stream().map(track -> {
            Map<String, String> m = new HashMap<>();
            m.put("title", track.getTitle());
            m.put("artist", track.getArtist().getName());
            m.put("url", "/api/tracks/play/" + track.getId());
            return m;
        }).toList();

        String playlistJSON = new ObjectMapper().writeValueAsString(trackList);
        model.addAttribute("playlistJSON", playlistJSON);

        return "favorites";
    }

}
