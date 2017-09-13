package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public class Manual {

    @PrimaryKey(autoGenerate = false)
    private Long id;
    private String name;
    @Embedded(prefix = "pdf_")
    private ManualFile pdf;

    public Manual(Long id, String name, ManualFile pdf) {
        this.id = id;
        this.name = name;
        this.pdf = pdf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ManualFile getPdf() {
        return pdf;
    }

    public void setPdf(ManualFile pdf) {
        this.pdf = pdf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
