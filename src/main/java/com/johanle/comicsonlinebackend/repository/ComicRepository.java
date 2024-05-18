package com.johanle.comicsonlinebackend.repository;

import com.johanle.comicsonlinebackend.model.Comic;
import org.springframework.data.repository.CrudRepository;

public interface ComicRepository extends CrudRepository<Comic, Integer> {
}
