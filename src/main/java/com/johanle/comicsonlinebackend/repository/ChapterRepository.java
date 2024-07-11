package com.johanle.comicsonlinebackend.repository;

import com.johanle.comicsonlinebackend.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    @Modifying
    @Query(value = "delete from chapter where comic_id = :comicId", nativeQuery = true)
    public void delChapterById(@Param("comicId") int comicId);
}
