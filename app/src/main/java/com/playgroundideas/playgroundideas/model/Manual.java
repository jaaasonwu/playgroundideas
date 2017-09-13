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
    private ManualFile pdfInfo;

    public Manual(Long id, String name, ManualFile pdfInfo) {
        this.id = id;
        this.name = name;
        this.pdfInfo = pdfInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ManualFile getPdfInfo() {
        return pdfInfo;
    }

    public void setPdfInfo(ManualFile pdfInfo) {
        this.pdfInfo = pdfInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
