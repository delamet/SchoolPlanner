package com.example.jackson.schoolplanner.SchoolPlanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;


import com.example.jackson.schoolplanner.DateFormatter.DateFormatter;
import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.SchoolPlanner.HwInfo.HwInfoActivity;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;
import com.example.jackson.schoolplanner.Task.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jackson on 6/24/17.
 */

public class TaskCalendarFragment extends AbstractTaskSelectorFragment {

    private ArrayList<HomeworkAssignment> non_complete_list;
    private RecyclerView taskList;
    private PlannerSelectorHWTaskListAdapter adapter;
    private CalendarView calendarView;

    private static final String NON_COMPLETE_LIST = "non_complete_list";

    public static TaskCalendarFragment newInstance(ArrayList<Task> nonCompleteList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(NON_COMPLETE_LIST, nonCompleteList);
        TaskCalendarFragment fragment = new TaskCalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.planner_selector_calendar, container, false);
        Bundle args = getArguments();
        non_complete_list = args.getParcelableArrayList(NON_COMPLETE_LIST);
        taskList = (RecyclerView) v.findViewById(R.id.task_list);
        calendarView = (CalendarView) v.findViewById(R.id.calendar_view);
        Calendar cal = Calendar.getInstance();
        calendarView.setDate(cal.getTimeInMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date dueDate = cal.getTime();
                ArrayList<HomeworkAssignment> tasks = getSelectedHwTasks(dueDate);
                adapter.setData(tasks);
                adapter.notifyDataSetChanged();
            }
        });
        taskList.setLayoutManager(new LinearLayoutManager(getContext()));
        Date dueDate = getCalendarViewDate();
        ArrayList<HomeworkAssignment> selectedHwTasks = getSelectedHwTasks(dueDate);
        adapter = new PlannerSelectorHWTaskListAdapter(selectedHwTasks, getContext());
        taskList.setAdapter(adapter);
        return v;
    }

    private ArrayList<HomeworkAssignment> getSelectedHwTasks(Date selectedDueDate){
        // Determines the selected date on the calendar view and
        // gets the hw tasks for that data
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedDueDate);
        int selectedYear = cal.get(Calendar.YEAR);
        int selectedMonth = cal.get(Calendar.MONTH);
        int selectedDay = cal.get(Calendar.DAY_OF_MONTH);
        ArrayList<HomeworkAssignment> selectedHWTasks = new ArrayList<>();
        for (HomeworkAssignment hw:non_complete_list) {
            Date nonCompleteDueDate = hw.getEndDate();
            cal.setTime(nonCompleteDueDate);
            int hwYear = cal.get(Calendar.YEAR);
            int hwMonth = cal.get(Calendar.MONTH);
            int hwDay = cal.get(Calendar.DAY_OF_MONTH);
            if(selectedYear == hwYear && selectedMonth == hwMonth && selectedDay == hwDay){
                selectedHWTasks.add(hw);
            }
        }
        return selectedHWTasks;
    }

    @Override
    public void taskDeleted(int position) {
        Date dueDate = getCalendarViewDate();
        ArrayList<HomeworkAssignment> hwList = getSelectedHwTasks(dueDate);
        adapter.setData(hwList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void taskAdded(int position) {
        Date dueDate = getCalendarViewDate();
        ArrayList<HomeworkAssignment> hwList = getSelectedHwTasks(dueDate);
        adapter.setData(hwList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void taskUpdated(int position) {
        Date dueDate = getCalendarViewDate();
        ArrayList<HomeworkAssignment> hwList = getSelectedHwTasks(dueDate);
        adapter.setData(hwList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void taskCompleted(int position) {
        Date dueDate = getCalendarViewDate();
        ArrayList<HomeworkAssignment> hwList = getSelectedHwTasks(dueDate);
        adapter.setData(hwList);
        adapter.notifyDataSetChanged();
    }

    private Date getCalendarViewDate(){
        // Gets the selected calendar view date
        long dateMilli = calendarView.getDate();
        return new Date(dateMilli);
    }

    private class PlannerSelectorHWTaskListAdapter extends RecyclerView.Adapter{

        private ArrayList<HomeworkAssignment> hwList;
        private Context activityContext;

        PlannerSelectorHWTaskListAdapter(ArrayList<HomeworkAssignment> hwList, Context activityContext){
            this.hwList = hwList;
            this.activityContext = activityContext;
        }

        public void setData(ArrayList<HomeworkAssignment> hwList){
            this.hwList = hwList;
        }

        public ArrayList<HomeworkAssignment> getData(){
            return hwList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(activityContext);
            View view = inflater.inflate(R.layout.planner_homework_single_cal, parent, false);
            return new HomeworkAssignmentListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            HomeworkAssignmentListViewHolder hwHolder = (HomeworkAssignmentListViewHolder) holder;
            HomeworkAssignment hw = hwList.get(position);
            hwHolder.setHomeworkAssignment(hw);

        }

        @Override
        public int getItemCount() {
            return hwList.size();
        }

        private class HomeworkAssignmentListViewHolder extends RecyclerView.ViewHolder{

            private TextView homeworkTitleView;
            private TextView dueTimeView;
            private HomeworkAssignment assignment;

            private HomeworkAssignmentListViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = HwInfoActivity.getActivityIntent(activityContext, assignment);
                        startActivityForResult(intent, HW_INFO_CODE);
                    }
                });
                homeworkTitleView = (TextView) itemView.findViewById(R.id.task_name);
                dueTimeView = (TextView) itemView.findViewById(R.id.due_time_view);
            }

            private void setHomeworkTitle(String title){
                homeworkTitleView.setText(title);
            }

            private void setDueDate(Date date){
                dueTimeView.setText(DateFormatter.formatTimeToString(date));
            }

            private void setHomeworkAssignment(HomeworkAssignment hw){
                assignment = hw;
                setHomeworkTitle(assignment.getTaskName());
                setDueDate(assignment.getEndDate());
            }
        }
    }

}
