package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Guest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guestId;
    private String lastVisitedPage;
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "guest")
    private List<Comic> comicList;
}
