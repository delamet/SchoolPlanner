package com.example.jackson.schoolplanner.SchoolPlanner.HwInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jackson.schoolplanner.DateFormatter.DateFormatter;
import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.SchoolPlanner.HwAddEdit.SchoolPlannerHWAddEditFragment;
import com.example.jackson.schoolplanner.SchoolPlanner.HwAddEdit.SchoolPlannerHWEditActivity;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;

import java.util.Date;

/**
 * Created by Jackson on 7/22/17.
 */

public class HwInfoFragment extends Fragment{

    public static final String TASK_HW_KEY = "task_hw_key";
    public static final String DELETE_HW_KEY = "delete_hw_key";
    public static final int TASK_INFO_KEY = 0;
    public static final String TASK_COMPLETED_KEY = "task_completed_key";

    private HomeworkAssignment selectedHw;
    private TextView nameValue;
    private TextView subjectValue;
    private TextView classValue;
    private TextView assignedDateValue;
    private TextView assignedTimeValue;
    private TextView dueDateValue;
    private TextView dueTimeValue;

    public static HwInfoFragment newInstance(HomeworkAssignment hw) {
        Bundle args = new Bundle();
        args.putParcelable(TASK_HW_KEY, hw);
        HwInfoFragment fragment = new HwInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstance){
        View v = inflater.inflate(R.layout.task_info_hw_view, viewGroup, false);
        Bundle args = getArguments();
        selectedHw = args.getParcelable(TASK_HW_KEY);
        nameValue = (TextView) v.findViewById(R.id.name_value_view);
        subjectValue = (TextView) v.findViewById(R.id.subject_value_view);
        classValue = (TextView) v.findViewById(R.id.class_value_view);
        assignedDateValue = (TextView) v.findViewById(R.id.assigned_date_value_view);
        dueDateValue = (TextView) v.findViewById(R.id.due_date_value_view);
        assignedTimeValue = (TextView) v.findViewById(R.id.assigned_time_value_view);
        dueTimeValue = (TextView) v.findViewById(R.id.due_time_value_view);
        setHomeworkInfo(selectedHw);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.task_info_hw_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Activity activity = getActivity();
        Intent data = new Intent();
        int id = item.getItemId();
        switch (id){
            case R.id.edit_hw:
                data = SchoolPlannerHWEditActivity.getIntent(getContext(), selectedHw);
                startActivityForResult(data, TASK_INFO_KEY);
                break;
            case R.id.complete_hw:
                selectedHw.setCompleted(true);
                data.putExtra(TASK_COMPLETED_KEY, selectedHw);
                activity.setResult(Activity.RESULT_OK, data);
                activity.finish();
                break;
            case R.id.delete_hw:
                data.putExtra(DELETE_HW_KEY, selectedHw);
                activity.setResult(Activity.RESULT_OK, data);
                activity.finish();
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == TASK_INFO_KEY){
                HomeworkAssignment hw = intent.getExtras().getParcelable(SchoolPlannerHWAddEditFragment.HW_KEY);
                setHomeworkInfo(hw);
                Intent returnData = new Intent();
                returnData.putExtra(TASK_HW_KEY, hw);
                Activity activity = getActivity();
                activity.setResult(Activity.RESULT_OK, returnData);
            }
        }
    }

    public void setHomeworkInfo(HomeworkAssignment hw){
        Date assignedDate = hw.getStartDate();
        Date dueDate = hw.getEndDate();
        nameValue.setText(hw.getTaskName());
        assignedDateValue.setText(DateFormatter.formatDateToString(assignedDate));
        assignedTimeValue.setText(DateFormatter.formatTimeToString(assignedDate));
        dueDateValue.setText(DateFormatter.formatDateToString(dueDate));
        dueTimeValue.setText(DateFormatter.formatTimeToString(dueDate));
    }

}
