package com.treefrogapps.multiprocesspreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.treefrogapps.multiprocesspreferences.multi_preferences.MultiPreferences;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String PREFS = "APP_PREFS";

    private static final String STRING_KEY = "STRING_KEY";
    private static final String BOOLEAN_KEY = "BOOLEAN_KEY";
    private static final String INTEGER_KEY = "INTEGER_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MultiPreferences preferences = new MultiPreferences(PREFS, getApplicationContext());
        preferences.clearPreferences();

        booleanTests(preferences);
        stringTests(preferences);
        intTests(preferences);

//        String val1;
//        String str;
//        val1 = "xxx";
//        Log.i(TAG, "Check set value " + val1);
//        preferences.setString(STRING_KEY, val1);
//        str = preferences.getString(STRING_KEY, "yyy");
//        if (!str.equals(val1)) {
//            Log.e(TAG, "str should have set value " + val1 + ", value was " + str);
//        } else {
//            Log.i(TAG, "Test pass");
//        }

//
//        long timebefore = System.currentTimeMillis();
//
//        for (int i = 0; i < 1000; i++){
//
//            preferences.setString("string_key", "string_value");
//        }
//
//        long after = System.currentTimeMillis() - timebefore;
//
//        Log.e(TAG, "time to insert 1000 strings = " + after);
//
//        timebefore = System.currentTimeMillis();
//
//        for (int i = 0; i < 1000; i++){
//
//            preferences.getString("string_key", "not working");
//        }
//
//
//        after = System.currentTimeMillis() - timebefore;
//
//        Log.e(TAG, "time to retrieve 1000 strings = " + after);
//
//
//        preferences.setBoolean("boolean", false);
//
//        Log.e(TAG, preferences.getBoolean("boolean", true) + "");
//
        preferences.clearPreferences();
    }

    private void booleanTests(MultiPreferences preferences) {
        Log.i(TAG, "================= booleanTests ===============");

        // getBoolean test
        boolean myboolean;
        Log.i(TAG, "Check get default value true");
        myboolean = preferences.getBoolean(BOOLEAN_KEY, true);
        if (myboolean != true) {
            Log.e(TAG, "myboolean should have default value true, was " + myboolean);
        } else {
            Log.i(TAG, "Test pass");
        }
        preferences.clearPreferences();

        Log.i(TAG, "Check get default value false");
        myboolean = preferences.getBoolean(BOOLEAN_KEY, false);
        if (myboolean != false) {
            Log.e(TAG, "myboolean should have default value false, was " + myboolean);
        } else {
            Log.i(TAG, "Test pass");
        }
        preferences.clearPreferences();


        Log.i(TAG, "Check set value true");
        preferences.setBoolean(BOOLEAN_KEY, true);
        myboolean = preferences.getBoolean(BOOLEAN_KEY, false);
        if (myboolean != true) {
            Log.e(TAG, "myboolean should have set value true, was " + myboolean);
        } else {
            Log.i(TAG, "Test pass");
        }

        Log.i(TAG, "Check set value false");
        preferences.setBoolean(BOOLEAN_KEY, false);
        myboolean = preferences.getBoolean(BOOLEAN_KEY, true);
        if (myboolean != false) {
            Log.e(TAG, "myboolean should have set value false, was " + myboolean);
        } else {
            Log.i(TAG, "Test pass");
        }
    }

    private void stringTests(MultiPreferences preferences) {
        Log.i(TAG, "================= stringTests ===============");

        // getBoolean test
        String str;
        String val1 = "value1";
        Log.i(TAG, "Check get default value " + val1);
        str = preferences.getString(STRING_KEY, val1);
        if (!str.equals(val1)) {
            Log.e(TAG, "string should have default value " + val1 + ",was " + str);
        } else {
            Log.i(TAG, "Test pass");
        }
        preferences.clearPreferences();

        val1 = "";
        Log.i(TAG, "Check get default value " + val1);
        str = preferences.getString(STRING_KEY, val1);
        if (!str.equals(val1)) {
            Log.e(TAG, "string should have default value " + val1 + ",was " + str);
        } else {
            Log.i(TAG, "Test pass");
        }
        preferences.clearPreferences();

        val1 = "xxx";
        Log.i(TAG, "Check set value " + val1);
        preferences.setString(STRING_KEY, val1);
        str = preferences.getString(STRING_KEY, "yyy");
        if (!str.equals(val1)) {
            Log.e(TAG, "str should have set value " + val1 + ", value was " + str);
        } else {
            Log.i(TAG, "Test pass");
        }

        val1 = "";
        Log.i(TAG, "Check set empty string ");
        preferences.setString(STRING_KEY, val1);
        str = preferences.getString(STRING_KEY, "zzz");
        if (!str.equals(val1)) {
            Log.e(TAG, "str should have set value " + val1 + ", value was " + str);
        } else {
            Log.i(TAG, "Test pass");
        }
    }

    private void intTests(MultiPreferences preferences) {
        Log.i(TAG, "================= intTests ===============");

        // getBoolean test
        int anInt = 0;
        int val1 = 100;

        Log.i(TAG, "Check get default value " + val1);
        anInt = preferences.getInt(INTEGER_KEY, val1);
        if ( anInt != val1) {
            Log.e(TAG, "int should have default value " + val1 + ",was " + anInt);
        } else {
            Log.i(TAG, "Test pass");
        }
        preferences.clearPreferences();

        val1 = -1;
        Log.i(TAG, "Check get default value " + val1);
        anInt = preferences.getInt(INTEGER_KEY, val1);
        if (anInt != val1) {
            Log.e(TAG, "int should have default value " + val1 + ",was " + anInt);
        } else {
            Log.i(TAG, "Test pass");
        }
        preferences.clearPreferences();

        val1 = 100;
        Log.i(TAG, "Check set value " + val1);
        preferences.setInt(INTEGER_KEY, val1);
        anInt = preferences.getInt(INTEGER_KEY, 200);
        if ( anInt != val1) {
            Log.e(TAG, "int should have set value " + val1 + ", value was " + anInt);
        } else {
            Log.i(TAG, "Test pass");
        }

        val1 = -1;
        Log.i(TAG, "Check set -1 int (defalut value of shared pref) ");
        preferences.setInt(INTEGER_KEY, val1);
        anInt = preferences.getInt(INTEGER_KEY, 300);
        if (anInt != val1) {
            Log.e(TAG, "int should have set value " + val1 + ", value was " + anInt);
        } else {
            Log.i(TAG, "Test pass");
        }
    }
}
