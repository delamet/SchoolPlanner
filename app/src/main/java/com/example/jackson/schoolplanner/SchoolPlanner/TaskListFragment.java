package com.example.jackson.schoolplanner.SchoolPlanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.jackson.schoolplanner.DateFormatter.DateFormatter;
import com.example.jackson.schoolplanner.R;
import com.example.jackson.schoolplanner.SchoolPlanner.HwInfo.HwInfoActivity;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;
import com.example.jackson.schoolplanner.Task.Task;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jackson on 6/24/17.
 */

public class TaskListFragment extends AbstractTaskSelectorFragment {

    private RecyclerView mTaskView;
    private ArrayList<HomeworkAssignment> nonCompleteHwList;
    private PlannerSelectorHWTaskListAdapter taskViewAdapter;

    private static final String NON_COMPLETE_HW_LIST = "non_complete_hw_list";

    public static TaskListFragment newInstance(ArrayList<Task> nonCompleteHwList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(NON_COMPLETE_HW_LIST, nonCompleteHwList);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.planner_selector_list, container, false);
        Bundle args = getArguments();
        nonCompleteHwList = args.getParcelableArrayList(NON_COMPLETE_HW_LIST);
        mTaskView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        mTaskView.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskViewAdapter = new PlannerSelectorHWTaskListAdapter(nonCompleteHwList, getContext());
        mTaskView.setAdapter(taskViewAdapter);
        return v;
    }

    @Override
    public void taskDeleted(int position) {
        taskViewAdapter.notifyItemRemoved(position);
        taskViewAdapter.notifyItemRangeRemoved(position, taskViewAdapter.getItemCount());
    }

    @Override
    public void taskAdded(int position) {
        taskViewAdapter.notifyDataSetChanged();
        //taskViewAdapter.notifyDataSetChanged();
        //taskViewAdapter.notifyItemInserted(position);
        //taskViewAdapter.notifyItemRangeInserted(position, taskViewAdapter.getItemCount());
    }

    @Override
    public void taskUpdated(int position) {
        taskViewAdapter.notifyItemChanged(position);
    }

    @Override
    public void taskCompleted(int position) {
        taskDeleted(position);
    }

    private class PlannerSelectorHWTaskListAdapter extends RecyclerView.Adapter{

        private ArrayList<HomeworkAssignment> hwList;
        private Context activityContext;

        PlannerSelectorHWTaskListAdapter(ArrayList<HomeworkAssignment> hwList, Context activityContext){
            this.hwList = hwList;
            this.activityContext = activityContext;
        }

        public ArrayList<HomeworkAssignment> getData(){
            return hwList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(activityContext);
            View view = inflater.inflate(R.layout.planner_homework_single_list, parent, false);
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
            private TextView dueDateView;
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
                homeworkTitleView = (TextView) itemView.findViewById(R.id.homework_title);
                dueDateView = (TextView) itemView.findViewById(R.id.due_date_text_view);
                dueTimeView = (TextView) itemView.findViewById(R.id.time_text_view);
            }

            private void setHomeworkTitle(String title){
                homeworkTitleView.setText(title);
            }

            private void setDueDate(Date date){
                dueDateView.setText(DateFormatter.formatDateToString(date));
            }

            private void setDueTime(Date date){
                dueTimeView.setText(DateFormatter.formatTimeToString(date));
            }

            private void setHomeworkAssignment(HomeworkAssignment hw){
                assignment = hw;
                Date dueDate = hw.getEndDate();
                setHomeworkTitle(assignment.getTaskName());
                setDueDate(dueDate);
                setDueTime(dueDate);
            }
        }
    }
}
