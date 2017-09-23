package com.example.jackson.schoolplanner.Database;

import android.provider.BaseColumns;

/**
 * Created by Jackson on 6/28/17.
 */

class DatabaseContract {

     static final String DATABASE_NAME = "Tasks";

     static class TasksTable implements BaseColumns{
         static final String TABLE_NAME_TASKS = "tasks";
         static final String COLUMN_NAME_TASK_ID = "task_id";
         static final String COLUMN_NAME_CLASS_ID = SchoolClassTable.COLUMN_NAME_CLASS_ID;
         static final String COLUMN_NAME_SEMESTER_ID = SchoolClassTable.COLUMN_NAME_SEMESTER_ID;
         static final String COLUMN_NAME_NAME = "name";
         static final String COLUMN_NAME_START_DATE = "start_date";
         static final String COLUMN_NAME_COMPLETED_STATUS = "status";
         static final String COLUMN_NAME_END_DATE = "end_date";
         static final String COLUMN_NAME_LOCATION = "location";
         static final String COLUMN_NAME_TYPE = "type";
    }

    static class SchoolClassTable implements BaseColumns{
        static final String TABLE_NAME_SCHOOL_CLASSES = "school_classes";
        static final String COLUMN_NAME_CLASS_ID = "class_id";
        static final String COLUMN_NAME_SEMESTER_ID = SchoolSemesterTable.COLUMN_NAME_SEMESTER_ID;
        static final String COLUMN_NAME_CLASS_NAME = "class_name";
    }

    static class SchoolSemesterTable implements BaseColumns{
        static final String TABLE_NAME_SCHOOL_SEMESTERS = "school_semesters";
        static final String COLUMN_NAME_SEMESTER_ACTIVE = "semester_active";
        static final String COLUMN_NAME_SEMESTER_ID = "semester_id";
        static final String COLUMN_NAME_START_DATE = "start_data";
        static final String COLUMN_NAME_END_DATE = "end_date";
        static final String COLUMN_NAME_SEMESTER_NAME = "semester_name";
    }

    static class SettingsTable implements BaseColumns{
        static final String TABLE_NAME_SETTINGS = "settings";
        static final String COLUMN_NAME_HAS_SETUP = "has_setup";
    }

}
