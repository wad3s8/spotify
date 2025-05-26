package com.example.spotify.repository;

import com.example.spotify.entity.Playlist;
import com.example.spotify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByOwner_Id(Long userId);

    Playlist findByOwnerAndTitle(User owner, String title);
    List<Playlist> findByOwner(User owner);
}
