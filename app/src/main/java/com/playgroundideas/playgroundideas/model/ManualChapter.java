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
    private ManualFileInfo pdfChapterInfo;
    @Embedded(prefix = "html_")
    private ManualFileInfo htmlChapterInfo;

    public ManualChapter(int position, String title, Long manualId, ManualFileInfo pdfChapterInfo, ManualFileInfo htmlChapterInfo) {
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

    public ManualFileInfo getPdfChapterInfo() {
        return pdfChapterInfo;
    }

    public void setPdfChapterInfo(ManualFileInfo pdfChapterInfo) {
        this.pdfChapterInfo = pdfChapterInfo;
    }

    public ManualFileInfo getHtmlChapterInfo() {
        return htmlChapterInfo;
    }

    public void setHtmlChapterInfo(ManualFileInfo htmlChapterInfo) {
        this.htmlChapterInfo = htmlChapterInfo;
    }
}
