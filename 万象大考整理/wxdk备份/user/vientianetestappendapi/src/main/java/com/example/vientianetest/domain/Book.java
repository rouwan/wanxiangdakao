package com.example.vientianetest.domain;

public class Book {
    private int type;//2:商,3:文,4:理,5:艺,6:体,7:综
    private int level;
    private double addition;//加成百分多少
    public Book(){}

    public Book(int type, int level,float addition) {
        this.type = type;
        this.level = level;
        this.addition = addition;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getAddition() {
        return addition;
    }

    public void setAddition(double addition) {
        this.addition = addition;
    }
}
