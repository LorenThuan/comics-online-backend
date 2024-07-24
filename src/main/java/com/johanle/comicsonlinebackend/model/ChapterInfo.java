package com.johanle.comicsonlinebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChapterInfo {
    private int chapterId;
    private String chapterNumber;

    public ChapterInfo(int chapterId, String chapterNumber) {
        this.chapterId = chapterId;
        this.chapterNumber = chapterNumber;
    }
}