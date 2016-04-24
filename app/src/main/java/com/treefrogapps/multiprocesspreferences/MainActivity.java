package com.treefrogapps.multiprocesspreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.treefrogapps.multiprocesspreferences.multi_preferences.MultiPreferences;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String PREFS = "com.treefrogapps.multiprocesspreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MultiPreferences preferences = new MultiPreferences(PREFS, getApplicationContext());

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




    }


}
