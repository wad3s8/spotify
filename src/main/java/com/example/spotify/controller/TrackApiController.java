package com.example.spotify.controller;

import com.example.spotify.entity.Track;
import com.example.spotify.service.TrackService;
import com.example.spotify.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracks")
public class TrackApiController {

    @Autowired
    private TrackService trackService;

    @Autowired
    private StorageService storageService;

    @GetMapping("/play/{id}")
    public ResponseEntity<Resource> playTrack(@PathVariable Long id) {
        Track track = trackService.getById(id);
        Resource file = storageService.loadAsResource(track.getFilePath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .body(file);
    }
}
