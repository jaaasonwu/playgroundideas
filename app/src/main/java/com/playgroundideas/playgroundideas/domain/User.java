package com.playgroundideas.playgroundideas.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.ContactsContract;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public class User {

    @PrimaryKey
    private Long id;
    private String firstName;
    private String surname;
    private String pictureFileName;
    private ContactsContract.CommonDataKinds.Email email;
    private ContactsContract.CommonDataKinds.Phone phoneNumber;

    public User(Long id, String firstName, String surname, String pictureFileName, ContactsContract.CommonDataKinds.Email email, ContactsContract.CommonDataKinds.Phone phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.pictureFileName = pictureFileName;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getPictureFileName() {
        return pictureFileName;
    }

    public void setPictureFileName(String pictureFileName) {
        this.pictureFileName = pictureFileName;
    }

    public ContactsContract.CommonDataKinds.Email getEmail() {
        return email;
    }

    public void setEmail(ContactsContract.CommonDataKinds.Email email) {
        this.email = email;
    }

    public ContactsContract.CommonDataKinds.Phone getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(ContactsContract.CommonDataKinds.Phone phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
