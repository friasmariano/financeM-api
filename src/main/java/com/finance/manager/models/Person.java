package com.finance.manager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private String email;

    @Version
    private Integer version;

    public Person() {}

    public Person(Long id, String name, String lastname, String email, Integer version) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.version = version;
    }

    public Person(String name, String lastname, String email, Integer version) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
