package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public class Manual {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private Long id;
    private String name;
    @Embedded(prefix = "pdf_")
    private ManualFileInfo pdfInfo;
    @Embedded(prefix = "html_")
    private ManualFileInfo htmlInfo;
    private Boolean downloaded;

    public Manual(Long id, String name, ManualFileInfo pdfInfo, ManualFileInfo htmlInfo) {
        this.id = id;
        this.name = name;
        this.pdfInfo = pdfInfo;
        this.htmlInfo = htmlInfo;
        this.downloaded = false;
    }

    public Boolean getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Boolean downloaded) {
        this.downloaded = downloaded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ManualFileInfo getPdfInfo() {
        return pdfInfo;
    }

    public void setPdfInfo(ManualFileInfo pdfInfo) {
        this.pdfInfo = pdfInfo;
    }

    public ManualFileInfo getHtmlInfo() {
        return htmlInfo;
    }

    public void setHtmlInfo(ManualFileInfo htmlInfo) {
        this.htmlInfo = htmlInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
