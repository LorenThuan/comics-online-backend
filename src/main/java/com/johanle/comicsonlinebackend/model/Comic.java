package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comicId;

    @Column(nullable = false)
    private String nameComic;

    private String author;
    @Column(nullable = false)
    private String image_src;

//    @Column(nullable = false)
//    private String genre;

//  private int chapNumber;
//    private String rating;

    @Column(nullable = false)
    private String state;

    private int liked;

    private int followed;

    private Long views;

    @CreatedDate
    @Column(nullable = true, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(nullable = true, updatable = false)
    private String createBy;


    @LastModifiedBy
    @Column(insertable = false)
    private LocalDateTime lastModifiedBy;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comic", cascade=CascadeType.ALL)
    private List<Genre> genreList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comic", cascade=CascadeType.ALL)
    private List<Chapter> chapterList;


    @ManyToOne
    @JoinColumn(name = "user_id", insertable = true, updatable = true, nullable = true)
    private User user;


    public Comic() {
    }

    public Comic(String author, String image_src, String nameComic, String state) {
        this.author = author;
        this.image_src = image_src;
        this.nameComic = nameComic;
        this.state = state;
    }


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

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }


    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getNameComic() {
        return nameComic;
    }

    public void setNameComic(String nameComic) {
        this.nameComic = nameComic;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Comic{" +
                ", comicId=" + comicId +
                ", nameComic='" + nameComic + '\'' +
                ", author='" + author + '\'' +
                ", image_src='" + image_src + '\'' +
                ", state='" + state + '\'' +
                ", liked=" + liked +
                ", followed=" + followed +
                ", views=" + views +
                '}';
    }
}
