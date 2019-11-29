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
    private static final String LONG_KEY = "LONG_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MultiPreferences preferences = new MultiPreferences(PREFS, getApplicationContext());
        preferences.clearPreferences();

        Log.i(TAG, "========= functionality  tests ===============");
        cleaningTest(preferences);
        booleanTests(preferences);
        stringTests(preferences);
        intTests(preferences);
        longTests(preferences);


        Log.i(TAG, "========= Performance tests ===============");

        long timebefore = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++){

            preferences.setString("string_key", "string_value");
        }

        long after = System.currentTimeMillis() - timebefore;

        Log.e(TAG, "time to insert 1000 strings = " + after);

        timebefore = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++){

            preferences.getString("string_key", "not working");
        }


        after = System.currentTimeMillis() - timebefore;

        Log.e(TAG, "time to retrieve 1000 strings = " + after);


        preferences.setBoolean("boolean", false);

        Log.e(TAG, preferences.getBoolean("boolean", true) + "");

        preferences.clearPreferences();
    }

    private void cleaningTest(MultiPreferences preferences) {
        Log.i(TAG, "================= cleaningTest ===============");
        preferences.clearPreferences();

        int val1 = 0;
        int anInt;
        val1 = 100;
        Log.i(TAG, "Check cleaning setting value " + val1);
        preferences.setInt(INTEGER_KEY, val1);
        preferences.clearPreferences();

        int defaultValue = 200;
        anInt = preferences.getInt(INTEGER_KEY, defaultValue);
        if ( anInt != defaultValue) {
            Log.e(TAG, "cleaning did not work , return value is " + anInt + ", while it should have been " + defaultValue);
        } else {
            Log.i(TAG, "Test pass");
        }
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

    private void longTests(MultiPreferences preferences) {
        Log.i(TAG, "================= longTests ===============");

        // getBoolean test
        long aLong = 0L;
        long val1 = 100L;

        Log.i(TAG, "Check get default value " + val1);
        aLong = preferences.getLong(LONG_KEY, val1);
        if ( aLong != val1) {
            Log.e(TAG, "long should have default value " + val1 + ",was " + aLong);
        } else {
            Log.i(TAG, "Test pass");
        }
        preferences.clearPreferences();

        val1 = -1L;
        Log.i(TAG, "Check get default value " + val1);
        aLong = preferences.getLong(LONG_KEY, val1);
        if (aLong != val1) {
            Log.e(TAG, "long should have default value " + val1 + ",was " + aLong);
        } else {
            Log.i(TAG, "Test pass");
        }
        preferences.clearPreferences();

        val1 = 100L;
        Log.i(TAG, "Check set value " + val1);
        preferences.setLong(LONG_KEY, val1);
        aLong = preferences.getLong(LONG_KEY, 200L);
        if ( aLong != val1) {
            Log.e(TAG, "long should have set value " + val1 + ", value was " + aLong);
        } else {
            Log.i(TAG, "Test pass");
        }

        val1 = -1L;
        Log.i(TAG, "Check set -1 long (defalut value of shared pref) ");
        preferences.setLong(LONG_KEY, val1);
        aLong = preferences.getLong(LONG_KEY, 300L);
        if (aLong != val1) {
            Log.e(TAG, "long should have set value " + val1 + ", value was " + aLong);
        } else {
            Log.i(TAG, "Test pass");
        }
    }
}
