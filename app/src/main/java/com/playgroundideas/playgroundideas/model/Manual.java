package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public class Manual extends VersionedEntity{

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private Long id;
    private String name;
    @Embedded(prefix = "pdf_")
    private FileInfo pdfInfo;
    @Embedded(prefix = "html_")
    private FileInfo htmlInfo;

    public Manual(long version, Long id, String name, FileInfo pdfInfo, FileInfo htmlInfo) {
        super(version);
        this.id = id;
        this.name = name;
        this.pdfInfo = pdfInfo;
        this.htmlInfo = htmlInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileInfo getPdfInfo() {
        return pdfInfo;
    }

    public void setPdfInfo(FileInfo pdfInfo) {
        this.pdfInfo = pdfInfo;
    }

    public FileInfo getHtmlInfo() {
        return htmlInfo;
    }

    public void setHtmlInfo(FileInfo htmlInfo) {
        this.htmlInfo = htmlInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
