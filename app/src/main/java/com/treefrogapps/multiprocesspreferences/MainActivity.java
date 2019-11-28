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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MultiPreferences preferences = new MultiPreferences(PREFS, getApplicationContext());

        //String res = preferences.getString(STRING_KEY, "default value");

        boolean myboolean = preferences.getBoolean(BOOLEAN_KEY, true);
        Log.i(TAG, "myboolean=" + myboolean);
//        Log.i(TAG, "res=" + res);
//        String input1= "user input 1";
//        Log.i(TAG, "Setting STRING_KEY with =" + input1);
//        preferences.setString(STRING_KEY, input1);
//        res = preferences.getString(STRING_KEY, "default value");
//        Log.i(TAG, "res=" + res);
//        String input2= "";
//        Log.i(TAG, "Setting STRING_KEY with =" + input2);
//        preferences.setString(STRING_KEY, input2);
//        res = preferences.getString(STRING_KEY, "default value");
//        Log.i(TAG, "res=" + res);


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


}
