package com.example.spotify.controller;

import com.example.spotify.entity.Album;
import com.example.spotify.repository.AlbumRepository;
import com.example.spotify.repository.ArtistRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    @GetMapping
    public String showAllAlbums(Model model) {
        model.addAttribute("albums", albumRepository.findAll());
        return "album-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("album", new Album());
        model.addAttribute("artists", artistRepository.findAll());
        return "album-form";
    }

    @GetMapping("/{id}")
    public String showAlbumDetails(@PathVariable Long id, Model model) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID: " + id));
        model.addAttribute("album", album);
        model.addAttribute("tracks", album.getTracks());
        return "album-details"; // Создадим этот шаблон
    }

    @PostMapping("/add")
    public String addAlbum(@Valid @ModelAttribute Album album) {
        albumRepository.save(album);
        return "redirect:/albums";
    }
}
