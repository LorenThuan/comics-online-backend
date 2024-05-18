package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guestId;
    private String lastVisitedPage;
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "guest")
    private List<Comic> comicList;
}
