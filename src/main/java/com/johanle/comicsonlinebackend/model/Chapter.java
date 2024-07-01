package com.johanle.comicsonlinebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"comic"})
@EntityListeners(AuditingEntityListener.class)
public class Chapter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chapterId;

    private String chapterNumber;

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
//    @LastModifiedBy
//    @Column(insertable = false)
//    private String lastModifiedBy;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    @JsonIgnore
    private Comic comic;
}
