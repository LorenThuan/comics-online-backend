package com.johanle.comicsonlinebackend.repository;

import com.johanle.comicsonlinebackend.model.Chapter;
import com.johanle.comicsonlinebackend.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
    Optional<FileData> findByName(String fileName);
    List<FileData> findByChapter(Chapter chapter);
}
