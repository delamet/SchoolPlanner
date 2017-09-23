package com.example.jackson.schoolplanner.SchoolPlanner.AddClass;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;
import com.example.jackson.schoolplanner.SingleFragmentActivity;


/**
 * Created by Jackson on 8/25/17.
 */

public class AddClassesActivity extends SingleFragmentActivity {

    private static final String SCHOOL_SEMESTER_KEY = "school_semester_key";

    public static Intent getIntent(SchoolSemester semester, Context context){
        Intent intent = new Intent(context, AddClassesActivity.class);
        intent.putExtra(SCHOOL_SEMESTER_KEY, semester);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        SchoolSemester semester = (SchoolSemester) intent.getExtras().get(SCHOOL_SEMESTER_KEY);
        return AddClassesFragment.newInstance(semester);
    }
}
