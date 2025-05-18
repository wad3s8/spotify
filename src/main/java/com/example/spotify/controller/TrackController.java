package com.example.spotify.controller;

import com.example.spotify.entity.Track;
import com.example.spotify.entity.User;
import com.example.spotify.repository.ArtistRepository;
import com.example.spotify.repository.AlbumRepository;
import com.example.spotify.repository.UserRepository;
import com.example.spotify.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tracks")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository; // 👈 внедрили

    @GetMapping
    public String showAllTracks(Model model) {
        model.addAttribute("tracks", trackService.getAllTracks());
        return "track-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("track", new Track());
        model.addAttribute("artists", artistRepository.findAll()); // 👈 список артистов
        model.addAttribute("albums", albumRepository.findAll());   // 👈 список альбомов
        return "track-form";
    }

    @PostMapping("/add")
    public String addTrack(@ModelAttribute Track track,
                           @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        track.setCreatedBy(currentUser);
        trackService.saveTrack(track);
        return "redirect:/tracks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
        return "redirect:/tracks";
    }
}
