package com.playgroundideas.playgroundideas.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;
import java.util.Map;

/**
 * Created by Ferdinand on 9/09/2017.
 */

@Entity
public class Manual {

    @PrimaryKey
    private Long id;
    private String pdfFileName;
    private String htmlFileName;
    private List<String> htmlSectionsFileNames;
    private Map<Integer, List<String>> htmlPagesFileNamesPerSection;

    public Manual(Long id, String pdfFileName, String htmlFileName, List<String> htmlSectionsFileNames, Map<Integer, List<String>> htmlPagesFileNamesPerSection) {
        this.id = id;
        this.pdfFileName = pdfFileName;
        this.htmlFileName = htmlFileName;
        this.htmlSectionsFileNames = htmlSectionsFileNames;
        this.htmlPagesFileNamesPerSection = htmlPagesFileNamesPerSection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public String getHtmlFileName() {
        return htmlFileName;
    }

    public void setHtmlFileName(String htmlFileName) {
        this.htmlFileName = htmlFileName;
    }

    public List<String> getHtmlSectionsFileNames() {
        return htmlSectionsFileNames;
    }

    public void setHtmlSectionsFileNames(List<String> htmlSectionsFileNames) {
        this.htmlSectionsFileNames = htmlSectionsFileNames;
    }

    public Map<Integer, List<String>> getHtmlPagesFileNamesPerSection() {
        return htmlPagesFileNamesPerSection;
    }

    public void setHtmlPagesFileNamesPerSection(Map<Integer, List<String>> htmlPagesFileNamesPerSection) {
        this.htmlPagesFileNamesPerSection = htmlPagesFileNamesPerSection;
    }
}
