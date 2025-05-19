package com.example.spotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyApplication.class, args);
    }

}
