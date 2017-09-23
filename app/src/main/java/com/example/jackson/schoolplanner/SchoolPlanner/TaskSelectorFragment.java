package com.example.jackson.schoolplanner.SchoolPlanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import com.example.jackson.schoolplanner.SchoolClass.SchoolClass;
import com.example.jackson.schoolplanner.SchoolPlanner.HwAddEdit.SchoolPlannerHWAddActivity;
import com.example.jackson.schoolplanner.SchoolPlanner.HwAddEdit.SchoolPlannerHWAddEditFragment;
import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;
import com.example.jackson.schoolplanner.Task.Task;

import java.util.ArrayList;

/**
 * Created by Jackson on 6/21/17.
 */

public class TaskSelectorFragment extends Fragment{

    private ViewPager listCalViewPager;
    private TaskViewPagerAdapter adapter;
    private TabLayout taskTabLayout;
    private SchoolSemester selectedSemester;
    private Database database;
    private ArrayList<SchoolClass> classes;
    private ArrayList<Task> tasks;

    private static final int ADD_TASK_HW_REQUEST_CODE = 0;
    private static final String SELECTED_SEMESTER_KEY = "selected_semester_key";

    public static TaskSelectorFragment newInstance(SchoolSemester semester) {
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_SEMESTER_KEY, semester);
        TaskSelectorFragment fragment = new TaskSelectorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Bundle args = getArguments();
        selectedSemester = (SchoolSemester) args.get(SELECTED_SEMESTER_KEY);
        setHasOptionsMenu(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle onSavedInstance){
        View v = inflator.inflate(R.layout.planner_selector_view, container, false);
        database = Database.getDatabase(getContext());
        String semesterId = selectedSemester.getId();
        classes = database.getSchoolClasses(semesterId);
        tasks = getNonCompleteTasks();
        listCalViewPager = (ViewPager) v.findViewById(R.id.task_view_pager);
        taskTabLayout = (TabLayout) v.findViewById(R.id.task_tab_layout);
        adapter = new TaskViewPagerAdapter(tasks, getFragmentManager());
        listCalViewPager.setAdapter(adapter);
        taskTabLayout.setupWithViewPager(listCalViewPager);
        return v;
    }

    private ArrayList<Task> getNonCompleteTasks(){
        String semesterId = selectedSemester.getId();
        ArrayList<Task> tasks = new ArrayList<>();
        for (SchoolClass singleClass:classes) {
            String classId = singleClass.getClassId();
            ArrayList<Task> classTasks = database.getNonCompletedTasks(semesterId, classId);
            for(int i=0;i<classTasks.size();i++){
                Task task = classTasks.get(i);
                tasks.add(task);
            }
        }
        return tasks;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.planner_selector_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.add_task:
                Intent intent = SchoolPlannerHWAddActivity.getIntent(getActivity());
                startActivityForResult(intent, ADD_TASK_HW_REQUEST_CODE);
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == ADD_TASK_HW_REQUEST_CODE){
                HomeworkAssignment hw = data.getExtras().getParcelable(SchoolPlannerHWAddEditFragment.HW_KEY);
                addTask(hw);
            }
        }
    }

    void addTask(Task task){
        int position = 0;
        long dateNum = task.getEndDate().getTime();
        for (int i=0;i<tasks.size();i++) {
            Task comparingTask = tasks.get(i);
            long taskDateNum = task.getEndDate().getTime();
            if(dateNum <= taskDateNum){
                tasks.add(i, comparingTask);
                position = i;
                break;
            }
        }
        database.saveTask(task);
        ArrayList<AbstractTaskSelectorFragment> fragments = adapter.getFragmentList();
        for (AbstractTaskSelectorFragment fragment:fragments) {
            fragment.taskAdded(position);
        }
    }

    void updateTask(Task task){
        int position = tasks.indexOf(task);
        tasks.set(position, task);
        database.updateTask(task);
        ArrayList<AbstractTaskSelectorFragment> fragments = adapter.getFragmentList();
        for (AbstractTaskSelectorFragment fragment:fragments) {
            fragment.taskUpdated(position);
        }
    }

    void deleteTask(Task task){
        int position = tasks.indexOf(task);
        tasks.remove(task);
        database.deleteTask(task);
        ArrayList<AbstractTaskSelectorFragment> fragments = adapter.getFragmentList();
        for (AbstractTaskSelectorFragment fragment:fragments) {
            fragment.taskDeleted(position);
        }
    }

    void taskCompleted(HomeworkAssignment hw){
        int position = tasks.indexOf(hw);
        tasks.remove(hw);
        database.updateTask(hw);
        ArrayList<AbstractTaskSelectorFragment> fragments = adapter.getFragmentList();
        for (AbstractTaskSelectorFragment fragment:fragments) {
            fragment.taskDeleted(position);
        }
    }

    private class TaskViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<AbstractTaskSelectorFragment> fragmentList;
        private ArrayList<String> fragmentTitleList;

        TaskViewPagerAdapter(ArrayList<Task> list, FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
            fragmentTitleList = new ArrayList<>();
            fragmentList.add(TaskListFragment.newInstance(list));
            fragmentTitleList.add("LIST");
            fragmentList.add(TaskCalendarFragment.newInstance(list));
            fragmentTitleList.add("CALENDAR");
        }

        ArrayList<AbstractTaskSelectorFragment> getFragmentList(){
            return fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return fragmentTitleList.get(position);
        }
    }
}
