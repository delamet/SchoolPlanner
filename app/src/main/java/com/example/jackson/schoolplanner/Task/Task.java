package com.example.jackson.schoolplanner.Task;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.BatchUpdateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Jackson on 6/24/17.
 */

public abstract class Task implements Parcelable{

    private String taskName;
    private Date startDate;
    private Date endDate;
    private boolean isCompleted = false;
    private String taskId;
    private String classId;
    TaskType type;

    private static final String TASK_NAME_KEY = "task_name_key";
    private static final String START_DATE_KEY = "start_date_key";
    private static final String END_DATE = "end_date_key";
    private static final String IS_COMPLETED_KEY = "is_completed_key";
    private static final String TASK_ID_KEY = "task_id";
    private static final String CLASS_ID = "class_id";
    private static final String TASK_TYPE_KEY = "task_type_key";

    @Override
    public boolean equals(Object o){
        if(o instanceof Task){
            Task task = (Task) o;
            return task.getTaskId().equals(this.taskId);
        }
        return false;
    }

    public Task(String taskName, Date startDate, Date endDate, String classId){
        taskId = UUID.randomUUID().toString();
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.classId = classId;
    }

    public Task(String taskId, String classId, String taskName, Date startDate, Date endDate, boolean isCompleted, TaskType taskType){
        this.taskId = taskId;
        this.classId = classId;
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCompleted = isCompleted;
        this.type = taskType;
    }

    public Task(Parcel in){
        Bundle bundle = in.readBundle();
        taskId = bundle.getString(TASK_ID_KEY);
        classId = bundle.getString(CLASS_ID);
        taskName = bundle.getString(TASK_NAME_KEY);
        startDate = new Date(bundle.getLong(START_DATE_KEY, 0));
        endDate = new Date(bundle.getLong(END_DATE, 0));
        isCompleted = bundle.getBoolean(IS_COMPLETED_KEY);
        type = TaskType.valueOf(bundle.getString(TASK_TYPE_KEY));
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public TaskType getType() {
        return type;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getClassId() {
        return classId;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString(TASK_ID_KEY, taskId);
        bundle.putString(CLASS_ID, classId);
        bundle.putString(TASK_NAME_KEY, taskName);
        bundle.putLong(START_DATE_KEY, startDate.getTime());
        bundle.putLong(END_DATE, endDate.getTime());
        bundle.putBoolean(IS_COMPLETED_KEY, isCompleted);
        bundle.putString(TASK_TYPE_KEY, type.toString());
        dest.writeBundle(bundle);
    }
}
