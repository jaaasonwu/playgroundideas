package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public class User {

    @PrimaryKey(autoGenerate = false)
    private Long id;
    private String firstName;
    private String surname;
    private String email;
    private String phoneNumber;
    @Ignore
    private List<Project> createdProjects;
    @Ignore
    private List<Design> createdDesigns;
    @Ignore
    private List<Design> favouritedDesigns;


    public User(Long id, String firstName, String surname, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdProjects = new LinkedList<>();
        this.createdDesigns = new LinkedList<>();
        this.favouritedDesigns = new LinkedList<>();
    }

    public List<Project> getCreatedProjects() {
        return createdProjects;
    }

    public void setCreatedProjects(List<Project> createdProjects) {
        this.createdProjects = createdProjects;
    }

    public List<Design> getCreatedDesigns() {
        return createdDesigns;
    }

    public void setCreatedDesigns(List<Design> createdDesigns) {
        this.createdDesigns = createdDesigns;
    }

    public List<Design> getFavouritedDesigns() {
        return favouritedDesigns;
    }

    public void setFavouritedDesigns(List<Design> favouritedDesigns) {
        this.favouritedDesigns = favouritedDesigns;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
