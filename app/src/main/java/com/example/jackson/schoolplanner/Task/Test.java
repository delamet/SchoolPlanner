package com.example.jackson.schoolplanner.Task;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Jackson on 8/21/17.
 */

public class Test extends Task implements Parcelable {

    private String location;

    private static final String LOCATION_KEY = "_key";

    public Test(String taskName, Date startDate, Date endDate, String location, String classId) {
        super(taskName, startDate, endDate, classId);
        this.location = location;
        type = TaskType.TEST;
    }

    public Test(String taskId, String classId, String taskName, Date startDate, Date endDate, boolean isCompleted, String location) {
        super(taskId, classId, taskName, startDate, endDate, isCompleted, TaskType.TEST);
        this.location = location;
        type = TaskType.TEST;
    }

    public Test(Parcel in) {
        super(in);
        Bundle bundle = in.readBundle();
        location = bundle.getString(LOCATION_KEY);
    }

    public String getLocation(){
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

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}
