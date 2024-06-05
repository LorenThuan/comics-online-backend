package com.johanle.comicsonlinebackend.service;

import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.repository.ComicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ComicService {

    @Autowired
    private final ComicRepository comicRepository;

    private List<Comic> comicList = new LinkedList<>();

    private List<Comic> comicListUpdate;

    public ComicService(ComicRepository comicRepository) {;
        this.comicRepository = comicRepository;
    }

    public Comic uploadComics(Comic comic) {
        return comicRepository.save(comic);
    }

    public Comic readComics(int comicId) {
        return comicRepository.findById(comicId).orElse(null);
    }

    /*find all comic*/
    public List<Comic> findAllComics() throws Exception {

        return comicRepository.findAll();
    }

    public void deleteComics(int comicId) throws Exception {
        comicRepository.deleteById(comicId);
    }

    public Comic updateComic(Comic newComic, int comicId) {
        return comicRepository.findById(comicId)
                .map(comic -> {
                    comic.setNameComic(newComic.getNameComic());
                    comic.setAuthor(newComic.getAuthor());
                    return comicRepository.save(comic);
                }).orElse(null);
    }

    public List<Comic> getLastListComic() {
        comicListUpdate = new LinkedList<>();
        for (Comic comic : comicRepository.getLimitComic()) {
            comicListUpdate.add(comic);
        }
        return comicListUpdate;
    }
}
