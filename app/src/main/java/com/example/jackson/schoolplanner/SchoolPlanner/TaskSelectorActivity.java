package com.example.jackson.schoolplanner.SchoolPlanner;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;
import com.example.jackson.schoolplanner.SingleFragmentActivity;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;

/**
 * Created by Jackson on 6/21/17.
 */

public class TaskSelectorActivity extends SingleFragmentActivity implements TaskSelectorActivityListener {

    private static final String SEMESTER_KEY = "semester_key";

    public static Intent getIntent(SchoolSemester semester, Context context){
        Intent intent = new Intent(context, TaskSelectorActivity.class);
        intent.putExtra(SEMESTER_KEY, semester);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        SchoolSemester semester = (SchoolSemester) intent.getExtras().get(SEMESTER_KEY);
        return TaskSelectorFragment.newInstance(semester);
    }

    @Override
    public void deleteTask(HomeworkAssignment hw) {
        FragmentManager manager = getSupportFragmentManager();
        TaskSelectorFragment fragment = (TaskSelectorFragment) manager.findFragmentById(R.id.fragment_container);
        fragment.deleteTask(hw);
    }

    @Override
    public void updateTask(HomeworkAssignment hw) {
        FragmentManager manager = getSupportFragmentManager();
        TaskSelectorFragment fragment = (TaskSelectorFragment) manager.findFragmentById(R.id.fragment_container);
        fragment.updateTask(hw);
    }

    @Override
    public void completeTask(HomeworkAssignment hw) {
        FragmentManager manager = getSupportFragmentManager();
        TaskSelectorFragment fragment = (TaskSelectorFragment) manager.findFragmentById(R.id.fragment_container);
        fragment.taskCompleted(hw);
    }
}
