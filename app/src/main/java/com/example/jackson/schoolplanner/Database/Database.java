package com.example.jackson.schoolplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jackson.schoolplanner.SchoolClass.SchoolClass;
import com.example.jackson.schoolplanner.SchoolSemester.SchoolSemester;
import com.example.jackson.schoolplanner.Settings.Settings;
import com.example.jackson.schoolplanner.Task.HomeworkAssignment;
import com.example.jackson.schoolplanner.Task.StudyTime;
import com.example.jackson.schoolplanner.Task.Task;
import com.example.jackson.schoolplanner.Task.TaskType;
import com.example.jackson.schoolplanner.Task.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created by Jackson on 6/28/17.
 */
public class Database extends SQLiteOpenHelper {

    private static Database database;
    private static final String PLANNER_SQL_HELPER_TAG = "planner_sql_helper_tag";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;

    public static Database getDatabase(Context context){
        if(database == null){
            database = new Database(context);
        }
        return database;
    }

    private Database(Context context){
        super(context, DatabaseContract.DATABASE_NAME, null, DATABASE_VERSION);
        readableDatabase = getReadableDatabase();
        writableDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HW_TABLE = "CREATE TABLE " + DatabaseContract.TasksTable.TABLE_NAME_TASKS + " (" +
                DatabaseContract.TasksTable.COLUMN_NAME_TASK_ID + " TEXT PRIMARY KEY, " +
                DatabaseContract.TasksTable.COLUMN_NAME_CLASS_ID + " TEXT, " +
                DatabaseContract.TasksTable.COLUMN_NAME_SEMESTER_ID + " TEXT, " +
                DatabaseContract.TasksTable.COLUMN_NAME_NAME + " TEXT, " +
                DatabaseContract.TasksTable.COLUMN_NAME_START_DATE + " INTEGER, " +
                DatabaseContract.TasksTable.COLUMN_NAME_COMPLETED_STATUS + " INTEGER," +
                DatabaseContract.TasksTable.COLUMN_NAME_TYPE + " TEXT, " +
                DatabaseContract.TasksTable.COLUMN_NAME_LOCATION + " TEXT, " +
                DatabaseContract.TasksTable.COLUMN_NAME_END_DATE + " INTEGER )";
        String CREATE_SCHOOL_CLASS_TABLE = "CREATE TABLE " + DatabaseContract.SchoolClassTable.TABLE_NAME_SCHOOL_CLASSES + " (" +
                DatabaseContract.SchoolClassTable.COLUMN_NAME_SEMESTER_ID + " TEXT, " +
                DatabaseContract.SchoolClassTable.COLUMN_NAME_CLASS_ID + " TEXT PRIMARY KEY, " +
                DatabaseContract.SchoolClassTable.COLUMN_NAME_CLASS_NAME + " TEXT )";
        String CREATE_SCHOOL_SEMESTER_TABLE = "CREATE TABLE " + DatabaseContract.SchoolSemesterTable.TABLE_NAME_SCHOOL_SEMESTERS + " (" +
                DatabaseContract.SchoolSemesterTable.COLUMN_NAME_SEMESTER_ID + " TEXT PRIMARY KEY, " +
                DatabaseContract.SchoolSemesterTable.COLUMN_NAME_SEMESTER_ACTIVE + " INTEGER, " +
                DatabaseContract.SchoolSemesterTable.COLUMN_NAME_START_DATE + " INTEGER, " +
                DatabaseContract.SchoolSemesterTable.COLUMN_NAME_END_DATE + " INTEGER, " +
                DatabaseContract.SchoolSemesterTable.COLUMN_NAME_SEMESTER_NAME + " TEXT )";
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + DatabaseContract.SettingsTable.TABLE_NAME_SETTINGS + " (" +
                DatabaseContract.SettingsTable.COLUMN_NAME_HAS_SETUP + " INTEGER )";
        db.execSQL(CREATE_HW_TABLE);
        db.execSQL(CREATE_SCHOOL_CLASS_TABLE);
        db.execSQL(CREATE_SCHOOL_SEMESTER_TABLE);
        db.execSQL(CREATE_SETTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SchoolSemester getActiveSchoolSemester(){
        Cursor cursor = readableDatabase.query(DatabaseContract.SchoolSemesterTable.TABLE_NAME_SCHOOL_SEMESTERS,
                null, DatabaseContract.SchoolSemesterTable.COLUMN_NAME_SEMESTER_ACTIVE + " =? ",
                new String[]{"1"}, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            String semesterId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SchoolSemesterTable.COLUMN_NAME_SEMESTER_ID));
            String semesterName = cursor.getString(cursor.getColumnIndex(DatabaseContract.SchoolSemesterTable.COLUMN_NAME_SEMESTER_NAME));
            Date startDate = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseContract.SchoolSemesterTable.COLUMN_NAME_START_DATE)));
            Date endDate = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseContract.SchoolSemesterTable.COLUMN_NAME_END_DATE)));
            SchoolSemester semester = new SchoolSemester(semesterName, semesterId, startDate, endDate);
            cursor.close();
            return semester;
        }
        return null;
    }

    public void saveSemester(SchoolSemester semester){
        ContentValues values = getSemesterContentValues(semester);
        writableDatabase.insert(DatabaseContract.SchoolSemesterTable.TABLE_NAME_SCHOOL_SEMESTERS,
                null, values);
    }

    public void saveClass(SchoolClass schoolClass){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.SchoolClassTable.COLUMN_NAME_CLASS_NAME, schoolClass.getName());
        values.put(DatabaseContract.SchoolClassTable.COLUMN_NAME_CLASS_ID, schoolClass.getClassId());
        values.put(DatabaseContract.SchoolClassTable.COLUMN_NAME_SEMESTER_ID, schoolClass.getSemesterId());
        writableDatabase.insert(DatabaseContract.SchoolClassTable.TABLE_NAME_SCHOOL_CLASSES, null, values);
    }

    private ContentValues getSemesterContentValues(SchoolSemester semester){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.SchoolSemesterTable.COLUMN_NAME_SEMESTER_ID, semester.getId());
        values.put(DatabaseContract.SchoolSemesterTable.COLUMN_NAME_SEMESTER_ACTIVE, semester.isActive());
        values.put(DatabaseContract.SchoolSemesterTable.COLUMN_NAME_SEMESTER_NAME, semester.getName());
        values.put(DatabaseContract.SchoolSemesterTable.COLUMN_NAME_START_DATE, semester.getStartDate().getTime());
        values.put(DatabaseContract.SchoolSemesterTable.COLUMN_NAME_END_DATE, semester.getDueDate().getTime());
        return values;
    }

    public Settings getSettings(){
        // Returns the users settings
        Cursor cursor = readableDatabase.query(DatabaseContract.SettingsTable.TABLE_NAME_SETTINGS, null,
                null, null, null, null, null);
        if(cursor.getCount() != 0){
            boolean setup = cursor.getInt(cursor.getColumnIndex(DatabaseContract.SettingsTable.COLUMN_NAME_HAS_SETUP)) > 0;
            Settings settings = new Settings(setup);
            cursor.close();
            return settings;
        }
        return null;
    }


    public ArrayList<SchoolClass> getSchoolClasses(String semesterId){
        ArrayList<SchoolClass> classes = new ArrayList<>();
        Cursor cursor = readableDatabase.query(DatabaseContract.SchoolClassTable.TABLE_NAME_SCHOOL_CLASSES,
                null, DatabaseContract.SchoolClassTable.COLUMN_NAME_SEMESTER_ID + " =? ", new String[]{semesterId}, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String classId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SchoolClassTable.COLUMN_NAME_CLASS_ID));
            String className = cursor.getString(cursor.getColumnIndex(DatabaseContract.SchoolClassTable.COLUMN_NAME_CLASS_NAME));
            SchoolClass schoolClass = new SchoolClass(className, classId, semesterId);
            classes.add(schoolClass);
        }
        cursor.close();
        return classes;
    }

    public ArrayList<Task> getCompleteTasks(String semesterId, String classId){
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String selection = DatabaseContract.TasksTable.COLUMN_NAME_COMPLETED_STATUS + "=? AND" +
                DatabaseContract.TasksTable.COLUMN_NAME_CLASS_ID + " =? AND " +
                DatabaseContract.TasksTable.COLUMN_NAME_SEMESTER_ID + " =? ";
        String[] selectionArgs = {"1", classId, semesterId};
        Cursor cursor = readableDatabase.query(DatabaseContract.TasksTable.TABLE_NAME_TASKS,
                null, selection, selectionArgs, null, null, DatabaseContract.TasksTable.COLUMN_NAME_END_DATE);
        cursor.moveToFirst();
        ArrayList<Task> tasks = getTasksFromCursor(cursor);
        cursor.close();
        return tasks;
    }

    public ArrayList<Task> getNonCompletedTasks(String semesterId, String classId){
        ArrayList<HomeworkAssignment> hwList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String selection = DatabaseContract.TasksTable.COLUMN_NAME_COMPLETED_STATUS + "=? AND" +
                DatabaseContract.TasksTable.COLUMN_NAME_CLASS_ID + " =? AND " +
                DatabaseContract.TasksTable.COLUMN_NAME_SEMESTER_ID + " =? ";
        String[] selectionArgs = {"0", classId, semesterId};
        Cursor cursor = readableDatabase.query(DatabaseContract.TasksTable.TABLE_NAME_TASKS,
                null, selection, selectionArgs, null, null, DatabaseContract.TasksTable.COLUMN_NAME_END_DATE);
        cursor.moveToFirst();
        ArrayList<Task> tasks = getTasksFromCursor(cursor);
        cursor.close();
        return tasks;
    }

    private ArrayList<Task> getTasksFromCursor(Cursor cursor){
        ArrayList<Task> tasks = new ArrayList<>();
        while(!cursor.isAfterLast()){
            String taskId = cursor.getString(cursor.getColumnIndex(DatabaseContract.TasksTable.COLUMN_NAME_CLASS_ID));
            String classId = cursor.getString(cursor.getColumnIndex(DatabaseContract.TasksTable.COLUMN_NAME_CLASS_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.TasksTable.COLUMN_NAME_NAME));
            long startTime = cursor.getLong(cursor.getColumnIndex(DatabaseContract.TasksTable.COLUMN_NAME_START_DATE));
            boolean completed = cursor.getInt(cursor.getColumnIndex(DatabaseContract.TasksTable.COLUMN_NAME_COMPLETED_STATUS)) > 0;
            long dueTime = cursor.getLong(cursor.getColumnIndex(DatabaseContract.TasksTable.COLUMN_NAME_END_DATE));
            String location = cursor.getString(cursor.getColumnIndex(DatabaseContract.TasksTable.COLUMN_NAME_LOCATION));
            TaskType type = TaskType.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseContract.TasksTable.COLUMN_NAME_TYPE)));
            Calendar startCal = Calendar.getInstance();
            startCal.setTimeInMillis(startTime);
            Calendar dueCal = Calendar.getInstance();
            dueCal.setTimeInMillis(dueTime);
            Date startDate = startCal.getTime();
            Date dueDate = dueCal.getTime();
            if(type.equals(TaskType.HOMEWORK_ASSIGNMENT)){
                HomeworkAssignment assignment = new HomeworkAssignment(taskId, classId, name, startDate, dueDate, completed);
                tasks.add(assignment);
            }
            else if(type.equals(TaskType.STUDY_TIME)){
                StudyTime time = new StudyTime(taskId, classId, name, startDate, dueDate, completed, location);
                tasks.add(time);
            }
            else if(type.equals(TaskType.TEST)){
                Test test = new Test(taskId, classId, name, startDate, dueDate, completed, location);
                tasks.add(test);
            }
            cursor.moveToNext();
        }
        return tasks;
    }

    public void saveTask(Task task){
        ContentValues values = getTaskContentValues(task);
        writableDatabase.insert(DatabaseContract.TasksTable.TABLE_NAME_TASKS,
                null, values);
    }

    public void updateTask(Task task){
        String[] whereArgs = {task.getTaskId()};
        ContentValues values = getTaskContentValues(task);
        writableDatabase.update(DatabaseContract.TasksTable.TABLE_NAME_TASKS, values,
                DatabaseContract.TasksTable.COLUMN_NAME_TASK_ID + " =? ", whereArgs);
    }

    private ContentValues getTaskContentValues(Task task){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TasksTable.COLUMN_NAME_NAME, task.getTaskName());
        values.put(DatabaseContract.TasksTable.COLUMN_NAME_COMPLETED_STATUS, task.isCompleted());
        values.put(DatabaseContract.TasksTable.COLUMN_NAME_START_DATE, task.getStartDate().getTime());
        values.put(DatabaseContract.TasksTable.COLUMN_NAME_END_DATE, task.getEndDate().getTime());
        values.put(DatabaseContract.TasksTable.COLUMN_NAME_TASK_ID, task.getTaskId());
        values.put(DatabaseContract.TasksTable.COLUMN_NAME_TYPE, task.getType().toString());
        values.put(DatabaseContract.TasksTable.COLUMN_NAME_CLASS_ID, task.getClassId());
        if(task instanceof HomeworkAssignment){

        }
        else if(task instanceof StudyTime){
            StudyTime study = (StudyTime) task;
            values.put(DatabaseContract.TasksTable.COLUMN_NAME_LOCATION, study.getLocation());
        }
        else if(task instanceof Test){
            Test test = (Test) task;
            values.put(DatabaseContract.TasksTable.COLUMN_NAME_LOCATION, test.getLocation());
        }
        return values;
    }

    public void deleteTask(Task hw){
        writableDatabase.execSQL("DELETE FROM " + DatabaseContract.TasksTable.TABLE_NAME_TASKS + " WHERE " +
                DatabaseContract.TasksTable.COLUMN_NAME_TASK_ID + " = '" + hw.getTaskId() + "';");
    }
}
