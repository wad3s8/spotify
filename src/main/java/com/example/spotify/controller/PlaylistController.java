package com.example.spotify.controller;

import com.example.spotify.entity.Playlist;
import com.example.spotify.entity.Track;
import com.example.spotify.repository.PlaylistRepository;
import com.example.spotify.repository.TrackRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private TrackRepository trackRepository;

    @GetMapping
    public String listPlaylists(Model model) {
        model.addAttribute("playlists", playlistRepository.findAll());
        return "playlist-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("playlist", new Playlist());
        model.addAttribute("tracks", trackRepository.findAll());
        return "playlist-form";
    }

    @PostMapping("/{playlistId}/add-track/{trackId}")
    public String addTrackToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long trackId
    ) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Плейлист не найден"));

        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new IllegalArgumentException("Трек не найден"));

        if (!playlist.getTracks().contains(track)) {
            playlist.getTracks().add(track);
            playlistRepository.save(playlist);
        }

        return "redirect:/playlists/" + playlistId; // или куда нужно
    }


    @PostMapping("/add")
    public String addPlaylist(@Valid @ModelAttribute Playlist playlist,
                              @RequestParam List<Long> trackIds) {
        List<Track> tracks = trackRepository.findAllById(trackIds);
        playlist.setTracks(tracks);
        playlistRepository.save(playlist);
        return "redirect:/playlists";
    }

    @GetMapping("/delete/{id}")
    public String deletePlaylist(@PathVariable Long id) {
        playlistRepository.deleteById(id);
        return "redirect:/playlists";
    }
}
