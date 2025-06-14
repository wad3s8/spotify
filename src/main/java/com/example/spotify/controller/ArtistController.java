package com.example.spotify.controller;

import com.example.spotify.entity.Artist;
import com.example.spotify.repository.ArtistRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository artistRepository;

    @GetMapping
    public String listArtists(Model model) {
        model.addAttribute("artists", artistRepository.findAll());
        return "artist-list";
    }

    @GetMapping("/add")
    public String showArtistForm(Model model) {
        model.addAttribute("artist", new Artist());
        return "artist-form";
    }

    @PostMapping("/add")
    public String saveArtist(@Valid @ModelAttribute Artist artist) {
        artistRepository.save(artist);
        return "redirect:/artists"; // Можно возвращать на список
    }
}

