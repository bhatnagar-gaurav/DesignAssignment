package com.medtronic.assignment.designtask.utils;


import android.content.SharedPreferences;
/**

 *  The class to be used for all the utility functions used by different components
 *  of the application.
 *  Developer Name : Gaurav Bhatnagar
    Created Date : Dec 3,2017
 */
public class Utilityfunctions {


    public static void clearPreferences(SharedPreferences appPreferences){
        SharedPreferences.Editor editor = appPreferences.edit();
        editor.remove(Constants.DIVISION_ANSWER);
        editor.remove(Constants.MULTIPLICATION_ANSWER);
        editor.apply();
    }
}
