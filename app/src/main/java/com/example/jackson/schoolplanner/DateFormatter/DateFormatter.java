package com.example.jackson.schoolplanner.DateFormatter;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jackson on 8/15/17.
 */

public class DateFormatter {

    private static final String DATE_FORMATTER_TAG = "date_formatter_tag";

    public static Date parseStringToDate(String stringDate){
        SimpleDateFormat format = getDateFormat();
        Date date = new Date();
        try{
            date = format.parse(stringDate);
        }
        catch (Exception e){
            Log.i(DATE_FORMATTER_TAG, "Could not turn string into date");
        }
        return date;
    }

    public static Date parseStringToTime(String stringTime){
        SimpleDateFormat format = getTimeFormat();
        Date date = new Date();
        try{
            date = format.parse(stringTime);
        }
        catch (Exception e){
            Log.i(DATE_FORMATTER_TAG, "Could not turn string into time");
        }
        return date;
    }

    public static String formatDateToString(Date date){
        SimpleDateFormat format = getDateFormat();
        String stringDate = "";
        try{
            stringDate = format.format(date);
        }
        catch (Exception e){
            Log.i(DATE_FORMATTER_TAG, "Could not turn date into string");
        }
        return stringDate;
    }

    public static String formatTimeToString(Date date){
        SimpleDateFormat format = getTimeFormat();
        String stringTime = "";
        try{
            stringTime = format.format(date);
        }
        catch (Exception e){
            Log.i(DATE_FORMATTER_TAG, "Could not turn date into string");
        }
        return stringTime;
    }

    private static SimpleDateFormat getDateFormat(){
        return new SimpleDateFormat("dd-MM-yyyy");
    }

    private static SimpleDateFormat getTimeFormat(){
        return new SimpleDateFormat("hh:mm a");
    }
}
