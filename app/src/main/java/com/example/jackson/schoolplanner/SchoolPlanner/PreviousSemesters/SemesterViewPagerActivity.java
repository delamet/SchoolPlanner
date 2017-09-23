package com.example.jackson.schoolplanner.SchoolPlanner.PreviousSemesters;

import android.support.v4.app.Fragment;

import com.example.jackson.schoolplanner.SingleFragmentActivity;

public class SemesterViewPagerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SemesterViewPagerFragment.newInstance();
    }

}
