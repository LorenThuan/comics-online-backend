package com.johanle.comicsonlinebackend.controller;

import com.johanle.comicsonlinebackend.dto.ComicRequest;
import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @GetMapping("/comic/last-comics")
    public ResponseEntity<List<ComicRequest>> showLastListComic() throws Exception{
        List<ComicRequest> comicRequestList = comicService.getLastListComic();
        return new ResponseEntity<>(comicRequestList, HttpStatus.OK);
    }

    /*Show 10 popular comic by views desc*/
    @GetMapping("/comic/popular-comics")
    public ResponseEntity<List<ComicRequest>> showPopularComic() throws Exception{
        List<ComicRequest> comicRequestList = comicService.getPopularComic();
        return new ResponseEntity<>(comicRequestList, HttpStatus.OK);
    }

    /*Get Comic By name or author*/
    @GetMapping("/comics/search-list/{searchQuery}")
    public ResponseEntity<List<ComicRequest>> findByNameOrAuthor(@PathVariable String searchQuery) throws Exception{
        List<ComicRequest> comicRequestList = comicService.findByNameOrAuthor(searchQuery);
        return new ResponseEntity<>(comicRequestList, HttpStatus.OK);
    }

}
