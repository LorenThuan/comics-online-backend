package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    @Column(nullable = false)
    private String gerne;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    private Comic comic;

    public Genre() {
    }

    public Genre(String gerne) {
        this.gerne = gerne;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGerne() {
        return gerne;
    }

    public void setGerne(String gerne) {
        this.gerne = gerne;
    }


}
