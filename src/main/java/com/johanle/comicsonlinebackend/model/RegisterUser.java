package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class RegisterUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String email;
    private String password;
    private List<Comic> readingList;
    private String  roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "registerUser")
    private List<Comic> comicList;
}
