package com.example.jackson.schoolplanner.SchoolPlanner.HwAddEdit;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.jackson.schoolplanner.SingleFragmentActivity;


/**
 * Created by Jackson on 7/4/17.
 */

public class SchoolPlannerHWAddActivity extends SingleFragmentActivity {

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SchoolPlannerHWAddActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return SchoolPlannerHWAddEditFragment.newInstance();
    }
}
