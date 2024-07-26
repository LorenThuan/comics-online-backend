package com.johanle.comicsonlinebackend.service;

import com.johanle.comicsonlinebackend.dto.ComicRequest;
import com.johanle.comicsonlinebackend.dto.ComicTest;
import com.johanle.comicsonlinebackend.model.Chapter;
import com.johanle.comicsonlinebackend.model.Comic;
import com.johanle.comicsonlinebackend.model.Genre;
import com.johanle.comicsonlinebackend.repository.ChapterRepository;
import com.johanle.comicsonlinebackend.repository.ComicRepository;
import com.johanle.comicsonlinebackend.repository.GenreRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComicService {

    @Autowired
    private ComicRepository comicRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ChapterRepository chapterRepository;


    public Comic readComics(int comicId) {
        return comicRepository.findById(comicId).orElse(null);
    }

    /*find all comic*/
    public List<Comic> findAllComics() throws Exception {
        return comicRepository.findAll();
    }

    public void deleteComics(int comicId) throws Exception {

        try {
            genreRepository.delGenreById(comicId);
            chapterRepository.delChapterById(comicId);
            comicRepository.deleteById(comicId);
        } catch (Exception e) {
            System.out.println("comicId illegal " + comicId + e.getMessage());
        }
    }

    public Comic updateComic(Comic newComic, int comicId) {
        Comic result = null;
        try {
            Optional<Comic> comicOptional = comicRepository.findById(comicId);
            if (comicOptional.isPresent()) {
                Comic comic = comicOptional.get();
                comic.setNameComic(newComic.getNameComic());
                comic.setAuthor(newComic.getAuthor());
                comic.setImage_src(newComic.getImage_src());
                comic.setState(newComic.getState());
                comic.setGenreList(newComic.getGenreList());
                comic.setChapterList(newComic.getChapterList());
                System.out.println("Comic data update " + comic);
                for (Genre genre : comic.getGenreList()) {
                    Optional<Genre> genres = genreRepository.findById(genre.getGenreId());
                    System.out.println("Comic find " + genres);
                    if (genres.isPresent() && !genre.getGenre().isEmpty()) {
                        Genre genreNews = genres.get();
                            genreNews.setComic(comic);
                            genreNews.setGenre(genre.getGenre());
                            System.out.println("Comic Update " + genreNews);
                            genreRepository.save(genreNews);
                    } else {
                        genre.setComic(comic);
                        System.out.println("Comic add " +genre);
                        genreRepository.save(genre);
                    }
                }
                for (Chapter chapter : comic.getChapterList()) {
                    Optional<Chapter> chapters = chapterRepository.findById(chapter.getChapterId());
                    System.out.println("Chapter find " + chapters);
                    if (chapters.isPresent()) {
                        Chapter chapterNews = chapters.get();
                        chapterNews.setComic(comic);
                        chapterNews.setChapterNumber(chapter.getChapterNumber());
                        System.out.println(chapterNews);
                        chapterRepository.save(chapterNews);
                    } else {
                        chapter.setComic(comic);
                        System.out.println("Chapter add " +chapter);
                        chapterRepository.save(chapter);
                    }
                }
                result = comicRepository.save(comic);
                System.out.println(result);
                System.out.println("Comic was update successfully");
            }
        } catch (Exception e) {
            System.out.println("Error while update comic" + e.getMessage());
        }
        return result;
    }

    @Cacheable(value = "comics")
    public List<ComicRequest> getLastListComic() {
        return comicRepository.getLimitComic();
    }

    @Cacheable(value = "comicsPopular")
    public List<ComicRequest> getPopularComic() {
        return comicRepository.getPopularComic();
    }

    public List<ComicRequest> findByNameOrAuthor(String searchQuery) {
        Query query = entityManager.createNamedQuery("ComicRequest.findByNameOrAuthor", ComicRequest.class);
        query.setParameter("searchQuery", "%" + searchQuery + "%");
        return query.getResultList();
    }

    @Cacheable(value = "comicsRecentlyAdd")
    public List<ComicTest> getComicRecentlyAdd() {
       return comicRepository.getComicRecentlyAdd();
    }

    public List<ComicTest> findComicsQuery(String stateCheckBox, String numOption, String sortByOption, String genres) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("ComicTestMapping.findComicsQuery");
        query.setParameter("stateCheckBox", stateCheckBox);
        query.setParameter("numOption", numOption);
        query.setParameter("sortByOption", sortByOption);
        query.setParameter("genres", genres);

        return query.getResultList();
    }

    public Comic addComic(Comic comic) {
        Comic result = null;
       try {
           result = comicRepository.save(comic);
           for (Genre genre : comic.getGenreList()) {
               System.out.println(genre);
               genre.setComic(comic);
               genreRepository.save(genre);
           }

           for (Chapter chapter : comic.getChapterList()) {
               System.out.println(chapter);
               chapter.setComic(comic);
               chapterRepository.save(chapter);
           }
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
       return result;
    }

    /*Search all comic in database*/
    public List<Comic> findAllComic(String searchQuery) {
        List<Comic> comicList = null;
        try {
            if (!searchQuery.isEmpty()) {
                comicList = comicRepository.findByNameOrAuthor(searchQuery);
            }
        } catch (Exception e) {
            System.out.println("Can't find out comic " + e.getMessage());
        }
        return comicList;
    }
}
