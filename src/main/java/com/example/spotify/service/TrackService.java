package com.example.spotify.service;

import com.example.spotify.entity.Track;
import com.example.spotify.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    public Track getById(Long id) {
        return trackRepository.findById(id).orElse(null);
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }

    public List<Track> getTracksByUser(Long userId) {
        return trackRepository.findByCreatedBy_Id(userId);
    }
}
