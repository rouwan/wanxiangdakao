package io.renren.modules.sys.domain.small.wxdk;

public class WeekSubject {
    private int id;
    private int subjectId;
    private int titleId;
    private int sortId;

    public WeekSubject(){}

    public WeekSubject(int id, int subjectId, int titleId, int sortId) {
        this.id = id;
        this.subjectId = subjectId;
        this.titleId = titleId;
        this.sortId = sortId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }
}
