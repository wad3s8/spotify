package com.example.spotify.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "artist.name.required")
    private String name;

    @OneToMany(mappedBy = "artist")
    @ToString.Exclude
    private List<Track> tracks;
}
