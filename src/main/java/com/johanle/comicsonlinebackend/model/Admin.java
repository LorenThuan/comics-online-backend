package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;
    private String username;
    private String email;
    private String password;
    private String  roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admin")
    private List<Comic> comicList;
}
