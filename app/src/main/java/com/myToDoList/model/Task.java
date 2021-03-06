package com.myToDoList.model;

public class Task {
    private String taskTittle;
    private int taskType;
    private int taskDone;
    private String taskContent;
    private String taskID;
    private Long timestamp;
    private Long reminder;

    public Task(){

    }

    public String getTaskTittle() {
        return taskTittle;
    }

    public void setTaskTittle(String taskTittle) {
        this.taskTittle = taskTittle;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getReminder() {
        return reminder;
    }

    public void setReminder(Long reminder) {
        this.reminder = reminder;
    }

    public int getTaskDone() {
        return taskDone;
    }

    public void setTaskDone(int taskDone) {
        this.taskDone = taskDone;
    }
}
