package com.example.jackson.schoolplanner.SchoolPlanner.AddClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.SchoolClass.SchoolClass;
import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;


/**
 * Created by Jackson on 8/26/17.
 */

public class AddClassDialogFragment extends DialogFragment{

    private EditText classNameField;
    private SchoolSemester semester;

    private static final String SCHOOL_SEMESTER_KEY = "school_semester_key";
    public static final String CREATED_CLASS_KEY = "created_class_key";

    public static AddClassDialogFragment newInstance(SchoolSemester semester, Fragment target,
                                                     int requestCode) {
        Bundle args = new Bundle();
        args.putParcelable(SCHOOL_SEMESTER_KEY, semester);
        AddClassDialogFragment fragment = new AddClassDialogFragment();
        fragment.setArguments(args);
        fragment.setTargetFragment(target, requestCode);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Bundle args = getArguments();
        semester = (SchoolSemester) args.get(SCHOOL_SEMESTER_KEY);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialog = inflater.inflate(R.layout.create_class_dialog, null, false);
        classNameField = (EditText) dialog.findViewById(R.id.class_name_field);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add class").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = classNameField.getText().toString();
                SchoolClass singleClass = new SchoolClass(name, semester.getId());
                Fragment target = getTargetFragment();
                Intent data = new Intent();
                data.putExtra(CREATED_CLASS_KEY, singleClass);
                target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setView(dialog);
        return builder.create();
    }
}
