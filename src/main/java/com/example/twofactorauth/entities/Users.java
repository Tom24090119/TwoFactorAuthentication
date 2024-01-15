package com.example.twofactorauth.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;


    private int otp;

    private boolean isEnabled;

    public Users() {
    }

    public Users(String username, String password, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
    }
}
