package com.johanle.comicsonlinebackend.repository;

import com.johanle.comicsonlinebackend.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicRepository extends JpaRepository<Comic, Integer> {
}
