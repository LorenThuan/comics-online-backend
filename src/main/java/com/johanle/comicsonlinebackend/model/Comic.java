package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comicId;
    private String nameComic;
    private String author;
    private List<String> img = new ArrayList<>();
    private int year;
    private  String genre;
    private int chapNumber;
    private String rating;

    @ManyToOne
    @JoinColumn(name = "admin_id", insertable = false, updatable = false)
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "registerUser_id", insertable = false, updatable = false)
    private RegisterUser registerUser;

    @ManyToOne
    @JoinColumn(name = "guest_id", insertable = false, updatable = false)
    private Guest guest;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapNumber() {
        return chapNumber;
    }

    public void setChapNumber(int chapNumber) {
        this.chapNumber = chapNumber;
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

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
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
                ", img=" + img +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", chapNumber=" + chapNumber +
                '}';
    }
}
