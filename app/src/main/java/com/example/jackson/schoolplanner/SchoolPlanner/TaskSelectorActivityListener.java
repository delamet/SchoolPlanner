package com.example.jackson.schoolplanner.SchoolPlanner;


import com.example.jackson.schoolplanner.Task.HomeworkAssignment;

/**
 * Created by Jackson on 8/9/17.
 */

interface TaskSelectorActivityListener {

    void deleteTask(HomeworkAssignment hw);
    void updateTask(HomeworkAssignment hw);
    void completeTask(HomeworkAssignment hw);
}
