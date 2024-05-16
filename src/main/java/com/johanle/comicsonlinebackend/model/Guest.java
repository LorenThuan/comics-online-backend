package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Guest {

    @Id
    @GeneratedValue
    private int guestId;
    private String lastVisitedPage;
    private String roleName;
}
