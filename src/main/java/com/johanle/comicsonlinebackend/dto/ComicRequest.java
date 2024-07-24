package com.johanle.comicsonlinebackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.johanle.comicsonlinebackend.model.ChapterInfo;
import com.johanle.comicsonlinebackend.model.GenreInfo;
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

    private List<GenreInfo> genreList;
    private List<ChapterInfo> chapterList;

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
        this.genreList = convertGenreStringToList(genreList);
        this.chapterList = convertChapterStringToList(chapterList);
    }

    private List<GenreInfo> convertGenreStringToList(String data) {
        if (data == null || data.isEmpty()) return null;
        data = data.substring(1, data.length() - 1); // Remove the surrounding brackets
        return Arrays.stream(data.split("\\}, \\{"))
                .map(genre -> {
                    String[] parts = genre.replaceAll("[{}]", "").split(", ");
                    int genreId = Integer.parseInt(parts[0].split(": ")[1].trim());
                    String genreName = parts[1].split(": ")[1].replace("\"", "").trim();
                    return new GenreInfo(genreId, genreName);
                })
                .collect(Collectors.toList());
    }

    private List<ChapterInfo> convertChapterStringToList(String data) {
        if (data == null || data.isEmpty()) return null;

        // Remove the surrounding brackets
        data = data.substring(1, data.length() - 1);

        // Split the string into individual chapter strings
        return Arrays.stream(data.split("\\}, \\{"))
                .map(chapter -> {
                    // Remove surrounding curly braces
                    chapter = chapter.replaceAll("[{}]", "");

                    // Split by comma to get key-value pairs
                    String[] parts = chapter.split(", ");

                    // Extract chapterId and chapterNumber
                    int chapterId = Integer.parseInt(parts[0].split(": ")[1].trim());
                    String chapterNumber = parts[1].split(": ")[1].replace("\"", "").trim();

                    return new ChapterInfo(chapterId, chapterNumber);
                })
                .collect(Collectors.toList());
    }

}
