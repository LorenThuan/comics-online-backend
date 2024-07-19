package com.johanle.comicsonlinebackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.johanle.comicsonlinebackend.model.ChapterInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComicTest {
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
    private int chapterNumber;

    public ComicTest(int comic_id, String name_comic, String author, String image_src, String state, Long views, int liked, int followed, LocalDateTime create_date, LocalDateTime create_date_chapter, LocalDateTime last_modified_date_chapter, LocalDateTime last_modified_date, String genreList, String chapterList, int chapter_numbers) {
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
        this.chapterNumber = chapter_numbers;
    }

    private List<String> convertStringToList(String data) {
        return data != null ? Arrays.stream(data.split(","))
                .map(String::trim)
                .collect(Collectors.toList()) : null;
    }

    private List<ChapterInfo> convertChapterStringToList(String data) {
        return data != null ? Arrays.stream(data.split(","))
                .map(String::trim)
                .map(ch -> {
                    String[] parts = ch.split(":");
                    if (parts.length < 2) {
                        throw new IllegalArgumentException("Invalid chapter string format: " + ch);
                    }
                    return new ChapterInfo(Integer.parseInt(parts[0]), parts[1]);
                })
                .collect(Collectors.toList()) : null;
    }
}
