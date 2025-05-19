package com.example.spotify.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @ToString.Exclude
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    private int durationInSeconds;
    private LocalDate releaseDate;
    private String genre;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
