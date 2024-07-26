package com.johanle.comicsonlinebackend.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComicRequest {
    private int comicId;
    private String nameComic;
    private String author;
    private String image_src;
    private String state;
    private Long views;
    private int liked;
    private int followed;
    private LocalDateTime createDate;
    private LocalDateTime createDateChapter;
    private LocalDateTime lastModifiedDateChapter;
    private LocalDateTime lastModifiedDate;

    private List<String> genreList;
    private List<String> chapterList;

    public ComicRequest(int comic_id, String name_comic, String author, String image_src, String state, Long views, int liked, int followed, LocalDateTime create_date, LocalDateTime create_date_chapter, LocalDateTime last_modified_date_chapter, LocalDateTime last_modified_date, String genreList, String chapterList) {
        this.comicId = comic_id;
        this.nameComic = name_comic;
        this.author = author;
        this.image_src = image_src;
        this.state = state;
        this.views = views;
        this.liked = liked;
        this.followed = followed;
        this.createDate = create_date;
        this.createDateChapter = create_date_chapter;
        this.lastModifiedDateChapter = last_modified_date_chapter;
        this.lastModifiedDate = last_modified_date;
        this.genreList = convertStringToList(genreList);
        this.chapterList = convertStringToList(chapterList);
    }

    private List<String> convertStringToList(String data) {
        return data != null ? Arrays.stream(data.split(","))
                .map(String::trim)
                .collect(Collectors.toList()) : null;
    }
}
