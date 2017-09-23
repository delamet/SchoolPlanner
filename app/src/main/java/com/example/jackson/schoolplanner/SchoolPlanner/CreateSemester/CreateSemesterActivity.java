package com.example.jackson.schoolplanner.SchoolPlanner.CreateSemester;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.jackson.schoolplanner.SingleFragmentActivity;


/**
 * Created by Jackson on 8/23/17.
 */

public class CreateSemesterActivity extends SingleFragmentActivity {

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, CreateSemesterActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return CreateSemesterFragment.newInstance();
    }
}
