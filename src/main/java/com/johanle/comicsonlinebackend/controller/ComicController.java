package com.johanle.comicsonlinebackend.controller;

import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173/")
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

    @GetMapping("/comics")
    public List<Comic> showListComic() throws Exception{
        List<Comic> comicList = comicService.findAllComics();
        System.out.println(Arrays.toString(comicList.toArray()));
        return comicList;
    }

    @GetMapping("/comics/{comicId}")
    public Comic getComicById(@PathVariable("comicId") int comicId) {
        return comicService.readComics(comicId);
    }

    @PutMapping("/comics/{comicId}")
    public Comic editComic(@RequestBody Comic newComic, @PathVariable("comicId") int comicId) {
        return comicService.updateComic(newComic, comicId);
    }

    @DeleteMapping("/comics/{comicId}")
    public void deleteComicById(@PathVariable("comicId") int comicId) throws Exception{
        comicService.deleteComics(comicId);
    }

    /*Show 12 last update comic*/
    @GetMapping("/last-comics")
    public List<Comic> showLastListComic() throws Exception{
        List<Comic> comicList = comicService.getLastListComic();
        System.out.println(Arrays.toString(comicList.toArray()));
        return comicList;
    }

}
