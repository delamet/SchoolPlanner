package com.example.jackson.schoolplanner.SchoolSemester;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Jackson on 8/20/17.
 */

public class SchoolSemester implements Parcelable{

    private String name;
    private String id;
    private Date startDate;
    private Date endDate;
    private boolean active;
    private static final String NAME_KEY = "name_key";
    private static final String ID_KEY = "id_key";
    private static final String START_DATE_KEY = "start_date_key";
    private static final String END_DATE_KEY = "end_date_key";
    private static final String ACTIVE_KEY = "active_key";

    public SchoolSemester(String name, Date startDate, Date dueDate, boolean active){
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.startDate = startDate;
        this.endDate = dueDate;
        this.active = active;
    }

    public SchoolSemester(String name, String id, Date startDate, Date dueDate){
        this.name = name;
        this.id = id;
        this.startDate = startDate;
        this.endDate = dueDate;
    }

    SchoolSemester(Parcel in) {
        Bundle data = in.readBundle();
        name = data.getString(NAME_KEY);
        id = data.getString(ID_KEY);
        startDate = new Date(data.getLong(START_DATE_KEY, 0));
        endDate = new Date(data.getLong(END_DATE_KEY, 0));
        active = data.getBoolean(ACTIVE_KEY);
    }

    public static final Creator<SchoolSemester> CREATOR = new Creator<SchoolSemester>() {
        @Override
        public SchoolSemester createFromParcel(Parcel in) {
            return new SchoolSemester(in);
        }

        @Override
        public SchoolSemester[] newArray(int size) {
            return new SchoolSemester[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getDueDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString(NAME_KEY, name);
        bundle.putString(ID_KEY, id);
        bundle.putLong(START_DATE_KEY, startDate.getTime());
        bundle.putLong(END_DATE_KEY, endDate.getTime());
        bundle.putBoolean(ACTIVE_KEY, active);
        dest.writeBundle(bundle);
    }
}
