package com.johanle.comicsonlinebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"comic"})
public class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    @Column(nullable = false)
    private String genre;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    @JsonIgnore
    private Comic comic;
}
