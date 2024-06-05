package com.johanle.comicsonlinebackend.repository;

import com.johanle.comicsonlinebackend.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComicRepository extends JpaRepository<Comic, Integer> {
    /*get 12 comic update new*/
    @Query(value = "SELECT * FROM comic\n" +
            "ORDER BY `comic_id` DESC\n" +
            "LIMIT 12", nativeQuery = true)
    public List<Comic> getLimitComic();
}
