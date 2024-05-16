package com.johanle.comicsonlinebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Admin {

    @Id
    @GeneratedValue
    private int adminId;
    private String username;
    private String email;
    private String password;
    private String  roleName;
}
