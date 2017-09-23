package com.example.jackson.schoolplanner.SchoolPlanner.PreviousSemesters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.SchoolClass.SchoolClass;
import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;

import java.util.ArrayList;

/**
 * Created by Jackson on 6/17/17.
 */

public class ClassChooserFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private SchoolSemester schoolSemester;
    private static final String SCHOOL_SEMESTER_KEY = "school_semester_key";

    public static ClassChooserFragment newInstance(SchoolSemester semester) {
        Bundle args = new Bundle();
        args.putParcelable(SCHOOL_SEMESTER_KEY, semester);
        ClassChooserFragment fragment = new ClassChooserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstance){
        View v = inflator.inflate(R.layout.class_chooser, container, false);
        Bundle args = getArguments();
        schoolSemester = args.getParcelable(SCHOOL_SEMESTER_KEY);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.category_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.setAdapter(new ClassAdapter(schoolSemester.getClasses()));
        ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        bar.setTitle(schoolSemester.getName());
        return v;
    }

    private class ClassAdapter extends RecyclerView.Adapter<ClassViewHolder>{

        private ArrayList<SchoolClass> classes;

        ClassAdapter(ArrayList<SchoolClass> classes){
            this.classes = classes;
        }

        @Override
        public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.school_planner_category, parent, false);
            ClassViewHolder viewHolder = new ClassViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ClassViewHolder holder, int position) {
            SchoolClass singleClass = classes.get(position);
            holder.setCategoryName(singleClass.getName());
        }

        @Override
        public int getItemCount() {
            return classes.size();
        }

    }

    private class ClassViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        ClassViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.category_name_text_view);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = TaskSelectorActivity.getIntent(getActivity());
                    //startActivity(intent);
                }
            });
        }

        void setCategoryName(String name){
            mTextView.setText(name);
        }
    }
}
