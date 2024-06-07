package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    @Column(nullable = false)
    private String genre;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    private Comic comic;

}
