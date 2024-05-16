package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class RegisterUser {

    @Id
    @GeneratedValue
    private int userId;
    private String username;
    private String email;
    private String password;
    private List<Comic> readingList;
    private String  roleName;
}
