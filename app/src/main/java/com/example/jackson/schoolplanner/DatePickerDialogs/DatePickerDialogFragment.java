package com.example.jackson.schoolplanner.DatePickerDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jackson on 7/11/17.
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public static final String SELECTED_DATE = "selected_date";

    public static DatePickerDialogFragment newInstance(Fragment target, int requestCode) {
        Bundle args = new Bundle();
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        fragment.setTargetFragment(target, requestCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        Calendar  cal = Calendar.getInstance();
        return new DatePickerDialog(getContext(), this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date date = cal.getTime();
        Intent returnData = new Intent();
        returnData.putExtra(SELECTED_DATE, date.getTime());
        Fragment targetFragment = getTargetFragment();
        targetFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, returnData);
    }
}
