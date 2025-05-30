package com.example.spotify.controller;

import com.example.spotify.entity.Album;
import com.example.spotify.repository.AlbumRepository;
import com.example.spotify.repository.ArtistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final ObjectMapper objectMapper;

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
    public String showAlbumDetails(@PathVariable Long id, Model model) throws Exception {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID: " + id));
        model.addAttribute("album", album);
        model.addAttribute("tracks", album.getTracks());

        List<Map<String, String>> trackList = album.getTracks().stream()
                .map(track -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("title", track.getTitle());
                    map.put("artist", track.getArtist().getName());
                    map.put("url", "/api/tracks/play/" + track.getId());
                    return map;
                })
                .collect(Collectors.toList());

        String playlistJSON = objectMapper.writeValueAsString(trackList);
        model.addAttribute("playlistJSON", playlistJSON);

        return "album-details";
    }




    @PostMapping("/add")
    public String addAlbum(@Valid @ModelAttribute Album album) {
        albumRepository.save(album);
        return "redirect:/albums";
    }
}
