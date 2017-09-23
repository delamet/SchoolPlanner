package com.example.jackson.schoolplanner.Task;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Jackson on 8/21/17.
 */

public class StudyTime extends Task implements Parcelable{

    private String location;

    private static final String LOCATION_KEY = "location_key";

    public StudyTime(String taskName, Date startDate, Date endDate, String location, String classId) {
        super(taskName, startDate, endDate, classId);
        this.location = location;
        type = TaskType.STUDY_TIME;
    }

    public StudyTime(String taskId, String classId, String taskName, Date startDate, Date endDate, boolean isCompleted, String location) {
        super(taskId, classId, taskName, startDate, endDate, isCompleted, TaskType.STUDY_TIME);
        this.location = location;
        type = TaskType.STUDY_TIME;
    }

    StudyTime(Parcel in) {
        super(in);
        Bundle bundle = in.readBundle();
        this.location = bundle.getString(LOCATION_KEY);
    }

    public String getLocation() {
        return location;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        Bundle bundle = new Bundle();
        bundle.putString(LOCATION_KEY, location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StudyTime> CREATOR = new Creator<StudyTime>() {
        @Override
        public StudyTime createFromParcel(Parcel in) {
            return new StudyTime(in);
        }

        @Override
        public StudyTime[] newArray(int size) {
            return new StudyTime[size];
        }
    };
}
