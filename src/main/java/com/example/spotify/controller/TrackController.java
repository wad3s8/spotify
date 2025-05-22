package com.example.spotify.controller;
import org.springframework.core.io.Resource;
import com.example.spotify.entity.Track;
import com.example.spotify.entity.User;
import com.example.spotify.repository.ArtistRepository;
import com.example.spotify.repository.AlbumRepository;
import com.example.spotify.repository.UserRepository;
import com.example.spotify.storage.StorageService;
import com.example.spotify.service.TrackService;
import com.example.spotify.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private AlbumRepository albumRepository;

    @Autowired
    private StorageService storageService;

    @GetMapping
    public String showAllTracks(Model model) {
        model.addAttribute("tracks", trackService.getAllTracks());
        return "track-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("track", new Track());
        model.addAttribute("artists", artistRepository.findAll());
        model.addAttribute("albums", albumRepository.findAll());
        return "track-form";
    }

    @GetMapping("/play/{id}")
    @ResponseBody
    public ResponseEntity<Resource> playTrack(@PathVariable Long id) {
        Track track = trackService.getById(id); // добавь метод в TrackService, если его нет
        Resource file = storageService.loadAsResource(track.getFilePath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/add")
    public String addTrack(@ModelAttribute Track track,
                           @RequestParam("file") MultipartFile file,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {

        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        track.setCreatedBy(currentUser);

        try {
            String filename = storageService.store(file);
            track.setFilePath(filename);
        } catch (StorageException e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка загрузки файла: " + e.getMessage());
            model.addAttribute("artists", artistRepository.findAll());
            model.addAttribute("albums", albumRepository.findAll());
            return "track-form";
        }

        if (track.getArtist() != null) {
            track.setArtist(artistRepository.findById(track.getArtist().getId()).orElse(null));
        }

        if (track.getAlbum() != null) {
            track.setAlbum(albumRepository.findById(track.getAlbum().getId()).orElse(null));
        }
        trackService.saveTrack(track);

        return "redirect:/tracks";
    }


    @GetMapping("/delete/{id}")
    public String deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
        return "redirect:/tracks";
    }
}
