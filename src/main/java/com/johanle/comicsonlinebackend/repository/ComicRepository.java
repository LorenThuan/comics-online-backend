package com.johanle.comicsonlinebackend.repository;

import com.johanle.comicsonlinebackend.dto.ComicRequest;
import com.johanle.comicsonlinebackend.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComicRepository extends JpaRepository<Comic, Integer> {
    /*get 12 comic update new*/
    @Query(name = "ComicRequest.getLimitComic", nativeQuery = true)
    public List<ComicRequest> getLimitComic();

    @Query(name = "ComicRequest.getPopularComic", nativeQuery = true)
    public List<ComicRequest> getPopularComic();
}
