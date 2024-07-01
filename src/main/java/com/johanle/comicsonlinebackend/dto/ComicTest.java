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
public class ComicTest {
    private int comic_id;
    private String name_comic;
    private String author;
    private String image_src;
    private String state;
    private long views;
    private int liked;
    private int followed;
    private LocalDateTime create_date;
    private LocalDateTime create_date_chapter;
    private LocalDateTime last_modified_date_chapter;
    private LocalDateTime last_modified_date;

    private List<String> genreList;
    private List<String> chapterList;
    private int chapter_numbers;

    public ComicTest(int comic_id, String name_comic, String author, String image_src, String state, Long views, int liked, int followed, LocalDateTime create_date, LocalDateTime create_date_chapter, LocalDateTime last_modified_date_chapter, LocalDateTime last_modified_date, String genreList, String chapterList, int chapter_numbers) {
        this.comic_id = comic_id;
        this.name_comic = name_comic;
        this.author = author;
        this.image_src = image_src;
        this.state = state;
        this.views = views;
        this.liked = liked;
        this.followed = followed;
        this.create_date = create_date;
        this.create_date_chapter = create_date_chapter;
        this.last_modified_date_chapter = last_modified_date_chapter;
        this.last_modified_date = last_modified_date;
        this.genreList = convertStringToList(genreList);
        this.chapterList = convertStringToList(chapterList);
        this.chapter_numbers = chapter_numbers;
    }

    private List<String> convertStringToList(String data) {
        return data != null ? Arrays.stream(data.split(","))
                .map(String::trim)
                .collect(Collectors.toList()) : null;
    }
}
