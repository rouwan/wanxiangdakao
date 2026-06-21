package com.example.vientianetest.timer;

import java.util.Date;

public class TaskItem {
    private String name;
    private int interval;
    private Date previousTime;
    private boolean running;
    private Task task;

    public TaskItem() {

    }

    public TaskItem(String name, int interval, Task task) {
        this.name = name;
        this.interval = interval;
        this.running = false;
        this.previousTime = new Date(new Date().getTime() - 3600 * 1000);
        this.task = task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Date getPreviousTime() {
        return previousTime;
    }

    public void setPreviousTime(Date previousTime) {
        this.previousTime = previousTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "TaskItem{" +
                "name='" + name + '\'' +
                ", interval=" + interval +
                ", previousTime=" + previousTime +
                ", running=" + running +
                '}';
    }
}

