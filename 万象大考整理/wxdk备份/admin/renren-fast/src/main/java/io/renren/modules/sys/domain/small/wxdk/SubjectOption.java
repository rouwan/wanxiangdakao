package io.renren.modules.sys.domain.small.wxdk;

public class SubjectOption {
    private int id;
    private int subjectId;//题目ID
    private String option;
    private int sortId;
    public SubjectOption(){}
    public SubjectOption(int id, int subjectId, String option, int sortId) {
        this.id = id;
        this.subjectId = subjectId;
        this.option = option;
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

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }
}
