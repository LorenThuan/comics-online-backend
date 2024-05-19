package com.johanle.comicsonlinebackend.controller;

import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComicController {

    @Autowired
    private final ComicService comicService;

    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }

    @PostMapping("/comic")
    public Comic addComic(@RequestBody Comic comic) {
        System.out.println("upload comic: " + comic);
        return comicService.uploadComics(comic);
    }
}
