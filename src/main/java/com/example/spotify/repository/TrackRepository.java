package com.example.spotify.repository;

import com.example.spotify.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByCreatedBy_Id(Long userId);
}
