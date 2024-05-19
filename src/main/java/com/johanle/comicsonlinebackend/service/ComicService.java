package com.johanle.comicsonlinebackend.service;

import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.repository.ComicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ComicService {

    @Autowired
    private final ComicRepository comicRepository;

    public ComicService(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    public Comic uploadComics(Comic comic) {
        return comicRepository.save(comic);
    }

    public Comic readComics(int comicId) {
        return comicRepository.findById(comicId).orElse(null);
    }

    public List<Comic> findAllComics() throws Exception{
        List<Comic> comicList = new LinkedList<>();
        for (Comic comic : comicRepository.findAll()) {
            comicList.add(comic);
        }
        return comicList;
    }

    public String deleteComics(int comicId) throws Exception{
        comicRepository.deleteById(comicId);
        return "delete success";
    }

}

