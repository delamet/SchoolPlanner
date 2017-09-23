package com.example.jackson.schoolplanner.DatePickerDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jackson on 8/15/17.
 */

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    public static final String SELECTED_TIME = "selected_time";

    public static TimePickerDialogFragment newInstance(Fragment targetFragment, int requestCode) {
        Bundle args = new Bundle();
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        fragment.setTargetFragment(targetFragment, requestCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        Calendar cal = Calendar.getInstance();
        return new TimePickerDialog(getContext(), this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.YEAR);
        cal.clear(Calendar.DAY_OF_MONTH);
        cal.clear(Calendar.MONTH);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        Date selectedDate = cal.getTime();
        Intent returnData = new Intent();
        returnData.putExtra(SELECTED_TIME, selectedDate.getTime());
        Fragment targetFragment = getTargetFragment();
        targetFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, returnData);
    }
}
