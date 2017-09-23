package com.example.jackson.schoolplanner.SchoolPlanner.HwAddEdit;

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
import android.widget.EditText;
import android.widget.TextView;

import com.example.jackson.schoolplanner.DateFormatter.DateFormatter;
import com.example.jackson.schoolplanner.DatePickerDialogs.DatePickerDialogFragment;
import com.example.jackson.schoolplanner.DatePickerDialogs.TimePickerDialogFragment;
import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;

import java.util.Calendar;
import java.util.Date;

import static com.example.jackson.schoolplanner.DateFormatter.DateFormatter.formatDateToString;
import static com.example.jackson.schoolplanner.DateFormatter.DateFormatter.formatTimeToString;
import static com.example.jackson.schoolplanner.DateFormatter.DateFormatter.parseStringToDate;
import static com.example.jackson.schoolplanner.DateFormatter.DateFormatter.parseStringToTime;


/**
 * Created by Jackson on 7/4/17.
 */

public class SchoolPlannerHWAddEditFragment extends Fragment{

    private static final String ADD_EDIT_TASK_HW_FRAGMENT_TAG = "add_edit_task_hw_fragment_tag";
    private static final String ADD_TASK_HW_FRAGMENT_TAG = "add_task_hw_tag";
    private static final String DATE_PICKER_FRAGMENT_TAG = "date_picker_tag";
    private static final String TIME_PICKER_FRAGMENT_TAG = "time_picker_tag";
    private static final int DATE_REQUEST_CODE = 0;
    private static final int TIME_REQUEST_CODE = 1;
    public static final String HW_KEY = "hw_key";

    private HomeworkAssignment selectedHw;
    private EditText name;
    private EditText subjectName;
    private EditText className;
    private TextView currentAssignedDate;
    private TextView currentDueDate;
    private TextView currentAssignedTime;
    private TextView currentDueTime;
    private boolean assignedDialogSet = false;
    private boolean dueDialogSet = false;

    public static SchoolPlannerHWAddEditFragment newInstance() {
        Bundle args = new Bundle();
        SchoolPlannerHWAddEditFragment fragment = new SchoolPlannerHWAddEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SchoolPlannerHWAddEditFragment newInstance(HomeworkAssignment hw) {
        Bundle args = new Bundle();
        args.putParcelable(HW_KEY, hw);
        SchoolPlannerHWAddEditFragment fragment = new SchoolPlannerHWAddEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        View view = inflater.inflate(R.layout.add_task_hw, container, false);
        name = (EditText) view.findViewById(R.id.name);
        subjectName = (EditText) view.findViewById(R.id.subject_name);
        className = (EditText) view.findViewById(R.id.class_name);
        currentAssignedDate = (TextView) view.findViewById(R.id.current_assigned_date);
        currentDueDate = (TextView) view.findViewById(R.id.current_due_date);
        currentAssignedTime = (TextView) view.findViewById(R.id.current_assigned_time);
        currentDueTime = (TextView) view.findViewById(R.id.current_due_time);
        currentAssignedDate.setOnClickListener(new DateClickListener());
        currentDueDate.setOnClickListener(new DateClickListener());
        currentAssignedTime.setOnClickListener(new TimeClickListener());
        currentDueTime.setOnClickListener(new TimeClickListener());
        Bundle args = getArguments();
        if(!args.isEmpty()){
            // A homework assignment was passed in, Edit mode
            selectedHw = args.getParcelable(HW_KEY);
            addHomeworkAssignmentInfo(selectedHw);
        }
        else{
            // Add mode
            addDefaultDates();
        }
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.add_hw_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.add_task:
                HomeworkAssignment hw = createHomeworkAssignment();
                if(selectedHw != null){
                    // You are updating a selected task
                    selectedHw.setTaskName(hw.getTaskName());
                    selectedHw.setStartDate(hw.getStartDate());
                    selectedHw.setEndDate(hw.getEndDate());
                    addHomeworkAssignment(selectedHw);
                }
                else{
                    // You are adding a new task
                    addHomeworkAssignment(hw);
                }
        }
        return true;
    }

    private void addDefaultDates(){
        Calendar assignedCal = Calendar.getInstance();
        Date assignedDate = assignedCal.getTime();
        currentAssignedDate.setText(formatDateToString(assignedDate));
        currentAssignedTime.setText(formatTimeToString(assignedDate));
        Calendar dueCalendar = Calendar.getInstance();
        dueCalendar.roll(Calendar.DAY_OF_MONTH, true);
        Date dueDate = dueCalendar.getTime();
        currentDueDate.setText(formatDateToString(dueDate));
        currentDueTime.setText(formatTimeToString(dueDate));
    }

    private void addHomeworkAssignment(HomeworkAssignment hw){
        Activity activity = getActivity();
        Intent data = new Intent();
        data.putExtra(HW_KEY, hw);
        activity.setResult(Activity.RESULT_OK, data);
        activity.finish();
    }

    private void addHomeworkAssignmentInfo(HomeworkAssignment hw){
        name.setText(hw.getTaskName());
        Date assignedDate = hw.getStartDate();
        Date dueDate = hw.getEndDate();
        currentAssignedDate.setText(formatDateToString(assignedDate));
        currentAssignedTime.setText(formatTimeToString(assignedDate));
        currentDueDate.setText(formatDateToString(dueDate));
        currentDueTime.setText(formatTimeToString(dueDate));
    }



    private HomeworkAssignment createHomeworkAssignment(){
        String hwName = name.getText().toString();
        Date assignedDate = parseStringToDate(currentAssignedDate.getText().toString());
        Date assignedTime = parseStringToTime(currentAssignedTime.getText().toString());
        Date dueDate = parseStringToDate(currentDueDate.getText().toString());
        Date dueTime = parseStringToTime(currentDueTime.getText().toString());
        Date assignedDateTime = getDateTime(assignedDate, assignedTime);
        Date dueDateTime = getDateTime(dueDate, dueTime);
        HomeworkAssignment hw = new HomeworkAssignment(hwName, assignedDateTime, dueDateTime, "");
        return hw;
    }

    private Date getDateTime(Date date, Date time){
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);
        Calendar dateTimeCalendar = Calendar.getInstance();
        dateTimeCalendar.set(dateCalendar.get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH), dateCalendar.get(Calendar.DAY_OF_MONTH),
                timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE));
        return dateTimeCalendar.getTime();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result){
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == DATE_REQUEST_CODE){
                Date selectedDate = new Date(result.getLongExtra(DatePickerDialogFragment.SELECTED_DATE, 0));
                changeDate(selectedDate);
            }
            else if(requestCode == TIME_REQUEST_CODE){
                Date selectedTime = new Date(result.getLongExtra(TimePickerDialogFragment.SELECTED_TIME, 0));
                changeTime(selectedTime);
            }
        }
    }


    private void changeDate(Date date){
        if(assignedDialogSet){
            // The start date was pressed
            currentAssignedDate.setText(formatDateToString(date));
            assignedDialogSet = false;
        }
        else{
            // The due date was pressed
            currentDueDate.setText(formatDateToString(date));
            dueDialogSet = false;
        }
    }

    private void changeTime(Date date){
        if(assignedDialogSet){
            // The start date was pressed
            currentAssignedTime.setText(formatTimeToString(date));
            assignedDialogSet = false;
        }
        else{
            // The due date was pressed
            currentDueTime.setText(formatTimeToString(date));
            dueDialogSet = false;
        }
    }

    private class DateClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.current_assigned_date){
                // If the start date was pressed
                assignedDialogSet = true;
            }
            else{
                // If the due date was pressed
                dueDialogSet = true;
            }
            DatePickerDialogFragment dialog = DatePickerDialogFragment.newInstance(SchoolPlannerHWAddEditFragment.this, DATE_REQUEST_CODE);
            dialog.show(getFragmentManager(), DATE_PICKER_FRAGMENT_TAG);
        }
    }

    private class TimeClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.current_assigned_time){
                assignedDialogSet = true;
            }
            else{
                dueDialogSet = true;
            }
            TimePickerDialogFragment dialog = TimePickerDialogFragment.newInstance(SchoolPlannerHWAddEditFragment.this, TIME_REQUEST_CODE);
            dialog.show(getFragmentManager(), TIME_PICKER_FRAGMENT_TAG);
        }
    }
}
