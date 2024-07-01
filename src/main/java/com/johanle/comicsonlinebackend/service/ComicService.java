package com.johanle.comicsonlinebackend.service;

import com.johanle.comicsonlinebackend.dto.ComicRequest;
import com.johanle.comicsonlinebackend.dto.ComicTest;
import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.repository.ComicRepository;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ComicService {

    @Autowired
    private final ComicRepository comicRepository;

    @PersistenceContext
    private EntityManager entityManager;


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

    public List<ComicRequest> getLastListComic() {
        List<ComicRequest> comicRequests = comicRepository.getLimitComic();
        for (ComicRequest comicRequest1: comicRequests) {
            System.out.println(comicRequest1);
        }
        return comicRequests;
    }

    public List<ComicRequest> getPopularComic() {
        List<ComicRequest> comicRequests = comicRepository.getPopularComic();
        for (ComicRequest comicRequest1: comicRequests) {
            System.out.println(comicRequest1);
        }
        return comicRequests;
    }

    public List<ComicRequest> findByNameOrAuthor(String searchQuery) {
        Query query = entityManager.createNamedQuery("ComicRequest.findByNameOrAuthor", ComicRequest.class);
        query.setParameter("searchQuery", "%" + searchQuery + "%");
        return query.getResultList();
    }

    public List<ComicTest> findComicsQuery(String stateCheckBox, String numOption, String sortByOption, String genres) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("ComicTestMapping.findComicsQuery");
        query.setParameter("stateCheckBox", stateCheckBox);
        query.setParameter("numOption", numOption);
        query.setParameter("sortByOption", sortByOption);
        query.setParameter("genres", genres);

        return query.getResultList();
    }
}
