package com.example.jackson.schoolplanner.SchoolClass;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jackson on 8/17/17.
 */

public class SchoolClass implements Parcelable{

    private String name;
    private String classId;
    private String semesterId;
    private static final String NAME = "name";
    private static final String CLASS_ID = "class_id";

    public SchoolClass(String name, String semesterId){
        this.classId = UUID.randomUUID().toString();
        this.name = name;
        this.semesterId = semesterId;
    }

    public SchoolClass(String name, String classId, String semesterId){
        this.name = name;
        this.classId = classId;
        this.semesterId = semesterId;
    }

    private SchoolClass(Parcel in){
        Bundle data = in.readBundle();
        this.classId = data.getString(CLASS_ID);
        this.name = data.getString(NAME);
    }

    public String getName() {
        return name;
    }

    public String getClassId() {
        return classId;
    }

    public String getSemesterId(){
        return semesterId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle data = new Bundle();
        data.putString(CLASS_ID, classId);
        data.putString(NAME, name);
        dest.writeBundle(data);
    }

    public static Parcelable.Creator CREATOR = new Parcelable.Creator<SchoolClass>(){

        @Override
        public SchoolClass createFromParcel(Parcel source) {
            return new SchoolClass(source);
        }

        @Override
        public SchoolClass[] newArray(int size) {
            return new SchoolClass[size];
        }
    };
}
