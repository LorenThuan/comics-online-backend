package com.johanle.comicsonlinebackend.repository;

import com.johanle.comicsonlinebackend.dto.ComicRequest;
import com.johanle.comicsonlinebackend.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComicRepository extends JpaRepository<Comic, Integer> {
    /*get 12 comic update new*/
    @Query(name = "ComicRequest.getLimitComic", nativeQuery = true)
    public List<ComicRequest> getLimitComic();

    @Query(name = "ComicRequest.getPopularComic", nativeQuery = true)
    public List<ComicRequest> getPopularComic();

    @Query(value = "SELECT * FROM comic\n" +
            "WHERE name_comic LIKE %:searchQuery% OR author LIKE %:searchQuery%", nativeQuery = true)
    public List<Comic> findByNameOrAuthor(@Param("searchQuery") String searchQuery);

//    @Query(value = "select * from comic where comic_id = :comicId", nativeQuery = true)
//    public Comic findComicById(@Param("comicId") int comicId);
}
