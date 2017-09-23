package com.example.jackson.schoolplanner.SchoolPlanner.CreateSemester;

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
import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.SchoolClass.SchoolClass;
import com.example.jackson.schoolplanner.SchoolPlanner.AddClass.AddClassesActivity;
import com.example.jackson.schoolplanner.SchoolPlanner.AddClass.AddClassesFragment;
import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jackson on 8/23/17.
 */

public class CreateSemesterFragment extends Fragment{

    private EditText semesterNameField;
    private TextView semesterStartDateButton;
    private TextView semesterEndDateButton;

    private static final int START_DATE_CODE = 1;
    private static final int END_DATE_CODE = 2;
    private static final int CREATE_CLASSES_REQUEST_CODE = 3;
    private static final String DIALOG_DATE_PICKER_TAG = "dialog_date_picker_tag";
    public static final String ADDED_SEMESTER = "created_semester";
    public static final String ADDED_CLASSES = "added_class";

    public static CreateSemesterFragment newInstance() {
        Bundle args = new Bundle();

        CreateSemesterFragment fragment = new CreateSemesterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        View view = inflater.inflate(R.layout.create_semester, parent, false);
        semesterNameField = (EditText) view.findViewById(R.id.semester_name_field);
        semesterStartDateButton = (TextView) view.findViewById(R.id.semester_start_date_button);
        semesterEndDateButton = (TextView) view.findViewById(R.id.semester_end_date_button);
        Date startDate = Calendar.getInstance().getTime();
        Calendar endCal = Calendar.getInstance();
        endCal.roll(Calendar.MONTH, 4);
        Date endDate = endCal.getTime();
        semesterStartDateButton.setText(DateFormatter.formatDateToString(startDate));
        semesterEndDateButton.setText(DateFormatter.formatDateToString(endDate));
        semesterStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment dialog = DatePickerDialogFragment.newInstance(CreateSemesterFragment.this, START_DATE_CODE);
                dialog.show(getFragmentManager(), DIALOG_DATE_PICKER_TAG);
            }
        });
        semesterEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment dialog = DatePickerDialogFragment.newInstance(CreateSemesterFragment.this, END_DATE_CODE);
                dialog.show(getFragmentManager(), DIALOG_DATE_PICKER_TAG);
            }
        });
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
            case R.id.add:
                createSemester();
        }
        return true;
    }

    private void createSemester(){
        String semesterName = semesterNameField.getText().toString();
        Date startDate = DateFormatter.parseStringToDate(semesterStartDateButton.getText().toString());
        Date endDate = DateFormatter.parseStringToDate(semesterEndDateButton.getText().toString());
        SchoolSemester semester = new SchoolSemester(semesterName, startDate, endDate, true);
        Intent data = AddClassesActivity.getIntent(semester, getContext());
        data.putExtra(ADDED_SEMESTER, semester);
        startActivityForResult(data, CREATE_CLASSES_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == START_DATE_CODE){
                Date selectedDate = new Date(data.getLongExtra(DatePickerDialogFragment.SELECTED_DATE, 0));
                semesterStartDateButton.setText(DateFormatter.formatDateToString(selectedDate));
            }
            else if(requestCode == END_DATE_CODE){
                Date selectedDate = new Date(data.getLongExtra(DatePickerDialogFragment.SELECTED_DATE, 0));
                semesterEndDateButton.setText(DateFormatter.formatDateToString(selectedDate));
            }
            else if(requestCode == CREATE_CLASSES_REQUEST_CODE){
                SchoolSemester semester = (SchoolSemester) data.getExtras().get(AddClassesFragment.ADDED_SEMESTER_KEY);
                ArrayList<SchoolClass> classes = data.getParcelableArrayListExtra(AddClassesFragment.ADDED_CLASSES_KEY);
                Intent intent = new Intent();
                intent.putExtra(ADDED_CLASSES, classes);
                intent.putExtra(ADDED_SEMESTER, semester);
                Activity activity = getActivity();
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        }
    }
}
