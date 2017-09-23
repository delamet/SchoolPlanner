package com.example.jackson.schoolplanner.SchoolPlanner.HwInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.jackson.schoolplanner.SingleFragmentActivity;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;


/**
 * Created by Jackson on 7/22/17.
 */

public class HwInfoActivity extends SingleFragmentActivity {

    private static final String TASK_HW_KEY = "task_hw_key";
    private static final String TASK_HW_POSITION_KEY = "task_hw_position_key";

    public static Intent getActivityIntent(Context context, HomeworkAssignment hw){
        Intent intent = new Intent(context, HwInfoActivity.class);
        intent.putExtra(TASK_HW_KEY, hw);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        HomeworkAssignment hw = intent.getExtras().getParcelable(TASK_HW_KEY);
        return HwInfoFragment.newInstance(hw);
    }
}
