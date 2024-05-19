package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comic implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comicId;

    @Column(nullable = false)
    private String nameComic;

    private String author;
//    private List<String> img = new ArrayList<>();


    private int year;

    @Column(nullable = false)
    private  String genre;
//  private int chapNumber;
    private String rating;

    public Comic(String author, String genre, String nameComic, String rating, int year) {
        this.author = author;
        this.genre = genre;
        this.nameComic = nameComic;
        this.rating = rating;
        this.year = year;
    }

    @ManyToOne
    @JoinColumn(name = "admin_id", insertable = false, updatable = false, nullable = true)
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "registerUser_id", insertable = false, updatable = false, nullable = true)
    private RegisterUser registerUser;

    @ManyToOne
    @JoinColumn(name = "guest_id", insertable = false, updatable = false, nullable = true)
    private Guest guest;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNameComic() {
        return nameComic;
    }

    public void setNameComic(String nameComic) {
        this.nameComic = nameComic;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "author='" + author + '\'' +
                ", comicId=" + comicId +
                ", nameComic='" + nameComic + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                '}';
    }
}
