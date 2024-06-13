package com.johanle.comicsonlinebackend.service;

import com.johanle.comicsonlinebackend.dto.ComicRequest;
import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.repository.ComicRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
