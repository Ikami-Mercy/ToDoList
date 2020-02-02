package com.myToDoList.model;

public class Task {
    private String taskTittle;
    private int taskType;
    private String taskContent;
    private String taskID;

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

    @Override
    public String toString() {
        return "Task{" +
                "taskTittle='" + taskTittle + '\'' +
                ", taskType=" + taskType +
                ", taskContent='" + taskContent + '\'' +
                ", taskID='" + taskID + '\'' +
                '}';
    }
}
