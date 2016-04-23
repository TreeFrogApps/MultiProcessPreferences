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

        long timebefore = System.nanoTime();

        for (int i = 0; i < 1000; i++){

            preferences.setInt("integer_" + i, i);
        }

        for (int i = 0; i < 1000; i++){

            preferences.getInt("integer_" + i, -1);
        }

        long after = System.nanoTime() - timebefore;

        Log.e(TAG, "time to insert and retrieve 1000 ints = " + after);




    }


}
