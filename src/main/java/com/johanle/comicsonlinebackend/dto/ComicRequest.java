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
    private int comic_id;
    private String name_comic;
    private String author;
    private String image_src;
    private String state;
    private LocalDateTime createDate;
    private LocalDateTime createDateChapter;
    private LocalDateTime lastModifiedDateChapter;
    private LocalDateTime lastModifiedDate;

    private List<String> genreList;
    private List<String> chapterList;

    public ComicRequest(int comic_id, String name_comic, String author, String image_src, String state,
                        LocalDateTime createDate, LocalDateTime createDateChapter, LocalDateTime lastModifiedDateChapter,
                        LocalDateTime lastModifiedDate, String genreList, String chapterList) {
        this.comic_id = comic_id;
        this.name_comic = name_comic;
        this.author = author;
        this.image_src = image_src;
        this.state = state;
        this.createDate = createDate;
        this.createDateChapter = createDateChapter;
        this.lastModifiedDateChapter = lastModifiedDateChapter;
        this.lastModifiedDate = lastModifiedDate;
        this.genreList = convertStringToList(genreList);
        this.chapterList = convertStringToList(chapterList);
    }

    private List<String> convertStringToList(String data) {
        return data != null ? Arrays.stream(data.split(","))
                .map(String::trim)
                .collect(Collectors.toList()) : null;
    }
}
