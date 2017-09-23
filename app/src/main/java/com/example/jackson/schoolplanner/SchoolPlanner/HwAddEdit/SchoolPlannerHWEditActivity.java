package com.example.jackson.schoolplanner.SchoolPlanner.HwAddEdit;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.jackson.schoolplanner.SingleFragmentActivity;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;


/**
 * Created by Jackson on 7/30/17.
 */

public class SchoolPlannerHWEditActivity extends SingleFragmentActivity {
    private static final String HW_KEY = "hw_key";

    public static Intent getIntent(Context context, HomeworkAssignment hw){
        Intent intent = new Intent(context, SchoolPlannerHWEditActivity.class);
        intent.putExtra(HW_KEY, hw);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        HomeworkAssignment hw = intent.getExtras().getParcelable(HW_KEY);
        return SchoolPlannerHWAddEditFragment.newInstance(hw);
    }
}
