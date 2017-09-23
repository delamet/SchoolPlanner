package com.example.jackson.schoolplanner.SchoolPlanner.AddClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.SchoolClass.SchoolClass;
import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;

import java.util.ArrayList;

/**
 * Created by Jackson on 8/25/17.
 */

public class AddClassesFragment extends Fragment {

    private RecyclerView classesView;
    private SchoolSemester semester;
    private ClassesViewAdapter adapter;

    private static final String SCHOOL_SEMESTER_KEY = "school_semester_key";
    private static final String ADD_CLASS_DIALOG_TAG = "add_class_dialog_tag";
    public static final String ADDED_SEMESTER_KEY = "added_semester_key";
    public static final String ADDED_CLASSES_KEY = "added_class_key";
    private static final int ADD_CLASS_REQUEST_CODE = 1;

    public static AddClassesFragment newInstance(SchoolSemester semester) {
        Bundle args = new Bundle();
        args.putParcelable(SCHOOL_SEMESTER_KEY, semester);
        AddClassesFragment fragment = new AddClassesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Bundle args = getArguments();
        semester = (SchoolSemester) args.get(SCHOOL_SEMESTER_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        View view = inflater.inflate(R.layout.create_classes, parent, false);
        classesView = (RecyclerView) view.findViewById(R.id.classes_view);
        classesView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ClassesViewAdapter();
        classesView.setAdapter(adapter);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.create_class_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.add_class_menu_item:
                DialogFragment dialog = AddClassDialogFragment.newInstance(semester, this, ADD_CLASS_REQUEST_CODE);
                dialog.show(getFragmentManager(), ADD_CLASS_DIALOG_TAG);
                break;
            case R.id.add_classes_menu_item:
                Intent intent = new Intent();
                intent.putExtra(ADDED_CLASSES_KEY, adapter.getData());
                intent.putExtra(ADDED_SEMESTER_KEY, semester);
                Activity activity = getActivity();
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == ADD_CLASS_REQUEST_CODE){
                SchoolClass schoolClass = (SchoolClass) data.getExtras().get(AddClassDialogFragment.CREATED_CLASS_KEY);
                adapter.addData(schoolClass);
            }
        }
    }

    private class ClassesViewAdapter extends RecyclerView.Adapter {

        private ArrayList<SchoolClass> classes;

        ClassesViewAdapter(){
            classes = new ArrayList<>();
        }

        void addData(SchoolClass schoolClass){
            classes.add(schoolClass);
            //notifyDataSetChanged();
            notifyItemInserted(classes.size()-1);
        }

        ArrayList<SchoolClass>getData(){
            return classes;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            return new ClassesViewHolder(inflater.inflate(
                    R.layout.create_classes_view_holder, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ClassesViewHolder classHolder = (ClassesViewHolder) holder;
            SchoolClass singleClass = classes.get(position);
            classHolder.setClassName(singleClass.getName());
        }

        @Override
        public int getItemCount() {
            return classes.size();
        }

        private class ClassesViewHolder extends RecyclerView.ViewHolder{

            private TextView classNameField;

            ClassesViewHolder(View itemView) {
                super(itemView);
                classNameField = (TextView) itemView.findViewById(R.id.class_name);
            }

            void setClassName(String name){
                classNameField.setText(name);
            }
        }
    }
}
