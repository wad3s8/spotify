package com.example.spotify.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "track.title.required")
    private String title;

    @NotNull(message = "track.artist.required")
    @ManyToOne
    @JoinColumn(name = "artist_id")
    @ToString.Exclude
    private Artist artist;

    @NotNull(message = "track.album.required")
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Min(value = 1, message = "track.duration.min")
    private int durationInSeconds;

    @PastOrPresent(message = "track.releaseDate.past")
    private LocalDate releaseDate;

    private String genre;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
