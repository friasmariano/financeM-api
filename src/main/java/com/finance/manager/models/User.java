package com.finance.manager.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToOne
    @JoinColumn(name = "person_Id")
    private Person person;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> userRoles;


    public User() {}

    public User(Long id, String username, String password, Integer version) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.version = version;
    }

    public User(String username, String password, Person person, Integer version) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public Person getPerson() { return person; }
}

