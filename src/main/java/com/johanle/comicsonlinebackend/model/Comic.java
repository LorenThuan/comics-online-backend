package com.johanle.comicsonlinebackend.model;

import com.johanle.comicsonlinebackend.dto.ComicRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
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
@ToString
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
                        @ColumnResult(name = "create_date", type = LocalDateTime.class),
                        @ColumnResult(name = "create_date_chapter", type = LocalDateTime.class),
                        @ColumnResult(name = "last_modified_date_chapter", type = LocalDateTime.class),
                        @ColumnResult(name = "last_modified_date", type = LocalDateTime.class),
                        @ColumnResult(name = "genre_list", type = String.class),
                        @ColumnResult(name = "chapter_list", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "ComicRequest.getLimitComic",
        query = "SELECT distinct C.comic_id, name_comic, author, image_src, state, C.create_date, CH.create_date AS create_date_chapter, CH.last_modified_date AS last_modified_date_chapter, C.last_modified_date, " +
                "GROUP_CONCAT(distinct genre ORDER BY genre SEPARATOR ', ') AS genre_list, " +
                "GROUP_CONCAT(distinct chapter_number ORDER BY CAST(SUBSTRING(chapter_number, LOCATE(' ', chapter_number) + 1) AS UNSIGNED) DESC SEPARATOR ', ') AS chapter_list " +
                "FROM genre G JOIN comic C ON G.comic_id = C.comic_id JOIN chapter CH ON C.comic_id = CH.comic_id " +
                "GROUP BY C.create_date, CH.create_date, CH.last_modified_date, C.comic_id, C.last_modified_date " +
                "ORDER BY CH.create_date DESC, CH.last_modified_date DESC, C.comic_id DESC, C.create_date DESC, C.last_modified_date DESC " +
                "LIMIT 12",
        resultSetMapping = "ComicRequestMapping"
)
@EntityListeners(AuditingEntityListener.class)
public class Comic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comicId;

    @Column(nullable = false)
    private String nameComic;

    private String author;
    @Column(nullable = false)
    private String image_src;

//    @Column(nullable = false)
//    private String genre;

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

    @CreatedBy
    @Column(nullable = true, updatable = false)
    private String createBy;


    @LastModifiedBy
    @Column(insertable = false)
    private LocalDateTime lastModifiedBy;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comic", cascade=CascadeType.ALL)
    private List<Genre> genreList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comic", cascade=CascadeType.ALL)
    private List<Chapter> chapterList;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = true)
    private User user;

}
