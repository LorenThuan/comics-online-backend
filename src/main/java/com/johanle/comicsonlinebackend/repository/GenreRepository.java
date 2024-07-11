package com.johanle.comicsonlinebackend.repository;

import com.johanle.comicsonlinebackend.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
        @Modifying
        @Query(value = "delete from genre where comic_id = :comicId", nativeQuery = true)
        public void delGenreById(@Param("comicId") int comicId);
}
