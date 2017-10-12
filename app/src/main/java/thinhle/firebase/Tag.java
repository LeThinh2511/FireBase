package thinhle.firebase;

/**
 * Created by thinhle on 10/12/17.
 */

public class Tag {
    private String tagId;
    private String tagName;
    private int journalCount;

    public Tag() {
    }

    public Tag(String tagId, String tagName, int journalCount) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.journalCount = journalCount;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getJournalCount() {
        return journalCount;
    }

    public void setJournalCount(int journalCount) {
        this.journalCount = journalCount;
    }
}