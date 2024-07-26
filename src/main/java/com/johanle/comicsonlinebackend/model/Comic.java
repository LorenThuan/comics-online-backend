package com.johanle.comicsonlinebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.johanle.comicsonlinebackend.dto.ComicRequest;
import com.johanle.comicsonlinebackend.dto.ComicTest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"genreList", "chapterList", "user"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SqlResultSetMapping(
        name = "ComicRequestMapping",
        classes = @ConstructorResult(
                targetClass = ComicRequest.class,
                columns = {
                        @ColumnResult(name = "comic_id", type = Integer.class),
                        @ColumnResult(name = "name_comic", type = String.class),
                        @ColumnResult(name = "author", type = String.class),
                        @ColumnResult(name = "image_src", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "views", type = Long.class),
                        @ColumnResult(name = "liked", type = Integer.class),
                        @ColumnResult(name = "followed", type = Integer.class),
                        @ColumnResult(name = "create_date", type = LocalDateTime.class),
                        @ColumnResult(name = "create_date_chapter", type = LocalDateTime.class),
                        @ColumnResult(name = "last_modified_date_chapter", type = LocalDateTime.class),
                        @ColumnResult(name = "last_modified_date", type = LocalDateTime.class),
                        @ColumnResult(name = "genre_list", type = String.class),
                        @ColumnResult(name = "chapter_list", type = String.class),
                }
        )
)
@SqlResultSetMapping(
        name = "ComicTestMapping",
        classes = @ConstructorResult(
                targetClass = ComicTest.class,
                columns = {
                        @ColumnResult(name = "comic_id", type = Integer.class),
                        @ColumnResult(name = "name_comic", type = String.class),
                        @ColumnResult(name = "author", type = String.class),
                        @ColumnResult(name = "image_src", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "views", type = Long.class),
                        @ColumnResult(name = "liked", type = Integer.class),
                        @ColumnResult(name = "followed", type = Integer.class),
                        @ColumnResult(name = "create_date", type = LocalDateTime.class),
                        @ColumnResult(name = "create_date_chapter", type = LocalDateTime.class),
                        @ColumnResult(name = "last_modified_date_chapter", type = LocalDateTime.class),
                        @ColumnResult(name = "last_modified_date", type = LocalDateTime.class),
                        @ColumnResult(name = "genre_list", type = String.class),
                        @ColumnResult(name = "chapter_list", type = String.class),
                        @ColumnResult(name= "chapter_numbers", type = Integer.class)
                }
        )
)
@NamedNativeQuery(
        name = "ComicRequest.getLimitComic",
        query = "SELECT \n" +
                "    C.comic_id, \n" +
                "    name_comic, \n" +
                "    author, \n" +
                "    image_src, \n" +
                "    state, \n" +
                "    views, \n" +
                "    liked, \n" +
                "    followed, \n" +
                "    C.create_date, \n" +
                "    MAX(CH.create_date) AS create_date_chapter, \n" +
                "    MAX(CH.last_modified_date) AS last_modified_date_chapter, \n" +
                "    C.last_modified_date,\n" +
                "    GROUP_CONCAT(DISTINCT G.genre ORDER BY G.genre SEPARATOR ', ') AS genre_list,\n" +
                "\tGROUP_CONCAT(DISTINCT CH.chapter_number ORDER BY CAST(SUBSTRING_INDEX(CH.chapter_number, ' ', -1) AS UNSIGNED) DESC SEPARATOR ', ') AS chapter_list\n" +
                "FROM \n" +
                "    genre G \n" +
                "JOIN \n" +
                "    comic C ON G.comic_id = C.comic_id \n" +
                "JOIN \n" +
                "    chapter CH ON C.comic_id = CH.comic_id\n" +
                "GROUP BY \n" +
                "    C.comic_id, name_comic, author, image_src, state, views, liked, followed, \n" +
                "    C.create_date, C.last_modified_date\n" +
                "ORDER BY \n" +
                "    CASE \n" +
                "        WHEN MAX(CH.create_date) > C.create_date THEN MAX(CH.create_date)\n" +
                "        WHEN MAX(CH.last_modified_date) > MAX(CH.create_date) THEN MAX(CH.last_modified_date)\n" +
                "        ELSE C.create_date \n" +
                "    END DESC, \n" +
                "    CASE \n" +
                "        WHEN MAX(CH.last_modified_date) > MAX(CH.create_date) THEN MAX(CH.last_modified_date)\n" +
                "        ELSE C.create_date \n" +
                "    END DESC, \n" +
                "    C.comic_id DESC, \n" +
                "    C.create_date DESC",
        resultSetMapping = "ComicRequestMapping"
)
@NamedNativeQuery(
        name = "ComicRequest.getPopularComic",
        query = "SELECT  \n" +
                "    C.comic_id, \n" +
                "    name_comic, \n" +
                "    author, \n" +
                "    image_src, \n" +
                "    state, \n" +
                "    views, \n" +
                "    liked, \n" +
                "    followed, \n" +
                "    C.create_date, \n" +
                "    MAX(CH.create_date) AS create_date_chapter, \n" +
                "    MAX(CH.last_modified_date) AS last_modified_date_chapter, \n" +
                "    C.last_modified_date,\n" +
                "    GROUP_CONCAT(DISTINCT G.genre ORDER BY G.genre SEPARATOR ', ') AS genre_list,\n" +
                "\tGROUP_CONCAT(DISTINCT CH.chapter_number ORDER BY CAST(SUBSTRING_INDEX(CH.chapter_number, ' ', -1) AS UNSIGNED) DESC SEPARATOR ', ') AS chapter_list\n" +
                "FROM \n" +
                "    genre G \n" +
                "JOIN \n" +
                "    comic C ON G.comic_id = C.comic_id \n" +
                "JOIN \n" +
                "    chapter CH ON C.comic_id = CH.comic_id\n" +
                "GROUP BY \n" +
                "    C.comic_id, \n" +
                "    name_comic, \n" +
                "    author, \n" +
                "    image_src, \n" +
                "    state, \n" +
                "    views, \n" +
                "    liked, \n" +
                "    followed, \n" +
                "    C.create_date, \n" +
                "    C.last_modified_date\n" +
                "ORDER BY \n" +
                "    views DESC\n" +
                "LIMIT 10",
        resultSetMapping = "ComicRequestMapping"
)
@NamedNativeQuery(name = "ComicRequest.findByNameOrAuthor",
        query = "SELECT \n" +
                "    C.comic_id, \n" +
                "    name_comic, \n" +
                "    author, \n" +
                "    image_src, \n" +
                "    state, \n" +
                "    views, \n" +
                "    liked, \n" +
                "    followed, \n" +
                "    C.create_date, \n" +
                "    MAX(CH.create_date) AS create_date_chapter, \n" +
                "    MAX(CH.last_modified_date) AS last_modified_date_chapter, \n" +
                "    C.last_modified_date,\n" +
                "    GROUP_CONCAT(DISTINCT G.genre ORDER BY G.genre SEPARATOR ', ') AS genre_list,\n" +
                "\tGROUP_CONCAT(DISTINCT CH.chapter_number ORDER BY CAST(SUBSTRING_INDEX(CH.chapter_number, ' ', -1) AS UNSIGNED) DESC SEPARATOR ', ') AS chapter_list\n" +
                "FROM \n" +
                "    genre G \n" +
                "JOIN \n" +
                "    comic C ON G.comic_id = C.comic_id \n" +
                "JOIN \n" +
                "    chapter CH ON C.comic_id = CH.comic_id\n" +
                "WHERE \n" +
                "    name_comic LIKE :searchQuery OR author LIKE :searchQuery\n" +
                "GROUP BY \n" +
                "    C.comic_id, \n" +
                "    name_comic, \n" +
                "    author, \n" +
                "    image_src, \n" +
                "    state, \n" +
                "    views, \n" +
                "    liked, \n" +
                "    followed, \n" +
                "    C.create_date, \n" +
                "    C.last_modified_date",
        resultSetMapping = "ComicRequestMapping")
@NamedNativeQuery(name = "ComicTest.getComicRecently",
        query = "SELECT \n" +
                "    C.comic_id, \n" +
                "    name_comic, \n" +
                "    author, \n" +
                "    image_src, \n" +
                "    state, \n" +
                "    views, \n" +
                "    liked, \n" +
                "    followed, \n" +
                "    C.create_date, \n" +
                "    MAX(CH.create_date) AS create_date_chapter, \n" +
                "    MAX(CH.last_modified_date) AS last_modified_date_chapter, \n" +
                "    C.last_modified_date,\n" +
                "    GROUP_CONCAT(DISTINCT G.genre ORDER BY G.genre SEPARATOR ', ') AS genre_list,\n" +
                "\tGROUP_CONCAT(DISTINCT CH.chapter_number ORDER BY CAST(SUBSTRING_INDEX(CH.chapter_number, ' ', -1) AS UNSIGNED) DESC SEPARATOR ', ') AS chapter_list,\n" +
                "    COUNT(DISTINCT CH.chapter_number) AS chapter_numbers\n" +
                "FROM \n" +
                "    genre G \n" +
                "JOIN \n" +
                "    comic C ON G.comic_id = C.comic_id \n" +
                "JOIN \n" +
                "    chapter CH ON C.comic_id = CH.comic_id\n" +
                "GROUP BY \n" +
                "    C.comic_id, \n" +
                "    name_comic, \n" +
                "    author, \n" +
                "    image_src, \n" +
                "    state, \n" +
                "    views, \n" +
                "    liked, \n" +
                "    followed, \n" +
                "    C.create_date, \n" +
                "    C.last_modified_date\n" +
                "HAVING chapter_numbers >= 2\n" +
                "ORDER BY MAX(CH.create_date) DESC",
        resultSetMapping = "ComicTestMapping")
@EntityListeners(AuditingEntityListener.class)
@NamedStoredProcedureQuery(
        name = "ComicTestMapping.findComicsQuery",
        procedureName = "findComicsQuery",
        resultSetMappings = "ComicTestMapping",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "stateCheckBox", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "numOption", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "sortByOption", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "genres", type = String.class)
        }
)
public class Comic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comicId;

    @Column(nullable = false)
    private String nameComic;

    private String author;
    @Column(nullable = false)
    private String image_src;

//  private int chapNumber;
//    private String rating;

    @Column(nullable = false)
    private String state;

    private int liked;

    private int followed;

    private Long views;

    @CreatedDate
    @Column(nullable = true, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

//    @CreatedBy
//    @Column(nullable = true, updatable = false)
//    private String createBy;
//
//
//    @LastModifiedBy
//    @Column(insertable = false)
//    private String lastModifiedBy;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comic")
    private List<Genre> genreList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comic")
    private List<Chapter> chapterList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    @JsonIgnore
    private User user;
}
