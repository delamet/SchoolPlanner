package com.example.jackson.schoolplanner.Task;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Jackson on 6/26/17.
 */

public class HomeworkAssignment extends Task implements Parcelable{

    public HomeworkAssignment(String taskName, Date startDate, Date endDate, String classId) {
        super(taskName, startDate, endDate, classId);
        type = TaskType.HOMEWORK_ASSIGNMENT;
    }

    public HomeworkAssignment(String taskId, String classId, String taskName, Date startDate, Date endDate, boolean isCompleted) {
        super(taskId, classId, taskName, startDate, endDate, isCompleted, TaskType.HOMEWORK_ASSIGNMENT);
        type = TaskType.HOMEWORK_ASSIGNMENT;
    }

    public HomeworkAssignment(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomeworkAssignment> CREATOR = new Creator<HomeworkAssignment>() {
        @Override
        public HomeworkAssignment createFromParcel(Parcel in) {
            return new HomeworkAssignment(in);
        }

        @Override
        public HomeworkAssignment[] newArray(int size) {
            return new HomeworkAssignment[size];
        }
    };

    public TaskType getType() {
        return type;
    }
}
