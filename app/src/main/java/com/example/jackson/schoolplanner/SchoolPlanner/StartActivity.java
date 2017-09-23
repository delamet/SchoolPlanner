package com.example.jackson.schoolplanner.SchoolPlanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.jackson.schoolplanner.Database.Database;
import com.example.jackson.schoolplanner.SchoolClass.SchoolClass;
import com.example.jackson.schoolplanner.SchoolPlanner.AddClass.AddClassesActivity;
import com.example.jackson.schoolplanner.SchoolPlanner.CreateSemester.CreateSemesterActivity;
import com.example.jackson.schoolplanner.SchoolPlanner.CreateSemester.CreateSemesterFragment;
import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;


import java.io.Console;
import java.util.ArrayList;

/**
 * Created by Jackson on 8/22/17.
 */

public class StartActivity extends AppCompatActivity{

    private static final int CREATE_SEMESTER_RESULT_CODE = 0;
    private static final int CREATE_CLASSES_RESULT_CODE = 1;
    private static final String START_ACTIVITY_TAG = "start_activity_tag";
    Database database;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        database = Database.getDatabase(this);
        SchoolSemester semester = database.getActiveSchoolSemester();
        /*if(semester != null){
            // There is data
            Intent intent = TaskSelectorActivity.getIntent(semester, this);
            startActivity(intent);
        }
        else{
            Intent intent = CreateSemesterActivity.getIntent(this);
            startActivityForResult(intent, CREATE_SEMESTER_RESULT_CODE);
        }*/
        // commit
        Log.d(START_ACTIVITY_TAG, "Started");
        //Intent intent = CreateSemesterActivity.getIntent(this);
        //startActivityForResult(intent, CREATE_SEMESTER_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CREATE_SEMESTER_RESULT_CODE){
                SchoolSemester semester = (SchoolSemester) data.getExtras().get(CreateSemesterFragment.ADDED_SEMESTER);
                ArrayList<SchoolClass> schoolClass = data.getParcelableArrayListExtra(CreateSemesterFragment.ADDED_CLASSES);
                database.saveSemester(semester);
                for (SchoolClass singleClass: schoolClass) {
                    database.saveClass(singleClass);
                }
            }
        }
    }
}
