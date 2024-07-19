package com.johanle.comicsonlinebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChapterInfo {
    private int chapterIdConcat;
    private String chapterNumberConcat;

    public ChapterInfo(int chapterIdConcat, String chapterNumberConcat) {
        this.chapterIdConcat = chapterIdConcat;
        this.chapterNumberConcat = chapterNumberConcat;
    }
}