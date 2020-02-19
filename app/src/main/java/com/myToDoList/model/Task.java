package com.myToDoList.model;

public class Task {
    private String taskTittle;
    private int taskType;
    private String taskContent;
    private String taskID;
    private String timestamp;
    private String reminder;

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskTittle='" + taskTittle + '\'' +
                ", taskType=" + taskType +
                ", taskContent='" + taskContent + '\'' +
                ", taskID='" + taskID + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", reminder='" + reminder + '\'' +
                '}';
    }
}
