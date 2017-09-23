package com.example.jackson.schoolplanner.SchoolPlanner.PreviousSemesters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jackson.schoolplanner.Database.Database;
import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;

import java.util.ArrayList;

/**
 * Created by Jackson on 6/17/17.
 */

public class SemesterViewPagerFragment extends Fragment{

    private ViewPager mViewPager;
    private Database database;
    private ArrayList<SchoolSemester> semesters;

    public static SemesterViewPagerFragment newInstance() {
        Bundle args = new Bundle();

        SemesterViewPagerFragment fragment = new SemesterViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,Bundle savedInstance){
        View v = inflator.inflate(R.layout.semester_view_pager, container, false);
        database = Database.getDatabase(getContext());
        mViewPager = (ViewPager) v.findViewById(R.id.semester_view_pager);
        //ArrayList<SchoolSemester> semesterList = database.getSchoolSemesters();
        //mViewPager.setAdapter(new SemestersAdapter(semesterList, getFragmentManager()));
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.classes_view_pager_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        switch (id){
            case R.id.add_class_menu_item:

                break;
        }
        return true;
    }

    private class SemestersAdapter extends FragmentPagerAdapter{

        private ArrayList<SchoolSemester> semesterList;

        public SemestersAdapter(ArrayList<SchoolSemester> semesterList, FragmentManager fm){
            super(fm);
            this.semesterList = semesterList;
        }

        @Override
        public Fragment getItem(int position) {
            SchoolSemester plannerClass = semesterList.get(position);
            ClassChooserFragment fragment = ClassChooserFragment.newInstance(plannerClass);
            return fragment;
        }

        @Override
        public int getCount() {
            return semesterList.size();
        }
    }

}
