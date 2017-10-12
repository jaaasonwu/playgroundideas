package com.playgroundideas.playgroundideas.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**
 * Created by Ferdinand on 2/10/2017.
 */

@Entity(foreignKeys = {@ForeignKey(entity = Manual.class, parentColumns = "id", childColumns = "manualId")}, primaryKeys = {"manualId", "position"})
public class ManualChapter {

    @NonNull
    private int position;
    private String title;
    @NonNull
    private Long manualId;
    @Embedded(prefix = "pdf_")
    private FileInfo pdfChapterInfo;
    @Embedded(prefix = "html_")
    private FileInfo htmlChapterInfo;

    public ManualChapter(int position, String title, Long manualId, FileInfo pdfChapterInfo, FileInfo htmlChapterInfo) {
        this.position = position;
        this.title = title;
        this.manualId = manualId;
        this.pdfChapterInfo = pdfChapterInfo;
        this.htmlChapterInfo = htmlChapterInfo;
    }

    @NonNull
    public int getPosition() {
        return position;
    }

    public void setPosition(@NonNull int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    public Long getManualId() {
        return manualId;
    }

    public void setManualId(@NonNull Long manualId) {
        this.manualId = manualId;
    }

    public FileInfo getPdfChapterInfo() {
        return pdfChapterInfo;
    }

    public void setPdfChapterInfo(FileInfo pdfChapterInfo) {
        this.pdfChapterInfo = pdfChapterInfo;
    }

    public FileInfo getHtmlChapterInfo() {
        return htmlChapterInfo;
    }

    public void setHtmlChapterInfo(FileInfo htmlChapterInfo) {
        this.htmlChapterInfo = htmlChapterInfo;
    }
}
