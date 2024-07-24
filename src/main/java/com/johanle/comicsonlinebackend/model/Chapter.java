package com.johanle.comicsonlinebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ToString(exclude = {"comic", "fileDataList"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id")
    @JsonIgnore
    private Comic comic;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chapter")
    @JsonIgnore
    private List<FileData> fileDataList;
}
