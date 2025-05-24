package com.example.spotify.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "album.title.required")
    private String title;

    @PastOrPresent(message = "album.releaseDate.past")
    private LocalDate releaseDate;

    @NotNull(message = "album.artist.required")
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "album")
    @ToString.Exclude
    private List<Track> tracks;
}
