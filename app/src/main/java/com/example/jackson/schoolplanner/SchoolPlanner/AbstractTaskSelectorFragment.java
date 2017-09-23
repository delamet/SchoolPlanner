package com.example.jackson.schoolplanner.SchoolPlanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.jackson.schoolplanner.SchoolPlanner.HwInfo.HwInfoFragment;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;


/**
 * Created by Jackson on 8/9/17.
 */

public abstract class AbstractTaskSelectorFragment extends Fragment{

    protected TaskSelectorActivityListener listener;
    protected static final int HW_INFO_CODE = 1;

    public abstract void taskDeleted(int position);
    public abstract void taskAdded(int position);
    public abstract void taskUpdated(int position);
    public abstract void taskCompleted(int position);

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (TaskSelectorActivityListener) activity;
        } catch (Exception exception){

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == HW_INFO_CODE){
                HomeworkAssignment hwDelete = data.getExtras().getParcelable(HwInfoFragment.DELETE_HW_KEY);
                HomeworkAssignment hwUpdate = data.getExtras().getParcelable(HwInfoFragment.TASK_HW_KEY);
                HomeworkAssignment hwComplete = data.getExtras().getParcelable(HwInfoFragment.TASK_COMPLETED_KEY);
                if(hwDelete != null){
                    listener.deleteTask(hwDelete);
                }
                else if(hwUpdate != null){
                    listener.updateTask(hwUpdate);
                }
                else if(hwComplete != null){
                    listener.completeTask(hwComplete);
                }
            }
        }
    }
}
