package com.example.vientianetest.domain;

public class SubjectOption {
    private int id;
    private int subjectId;
    private String name;
    private int sortId;
    public SubjectOption(){}
    public SubjectOption(int id, int subjectId, String name, int sortId) {
        this.id = id;
        this.subjectId = subjectId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }
}
