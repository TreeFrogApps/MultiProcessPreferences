package com.treefrogapps.multiprocesspreferences.multi_preferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Multi Preference class
 *
 * - allows access to Shared Preferences across processes through a
 *   Content Provider
 */
public class MultiPreferences {

    private Context mContext;
    private String mName;

    public MultiPreferences(String prefFileName, Context context){

        this.mName = prefFileName;
        this.mContext = context;
    }
    


    public void setString(final String key, final String value) {

        Uri updateUri = MultiProvider.createQueryUri(mName, key, MultiProvider.CODE_STRING);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MultiProvider.KEY, key);
        contentValues.put(MultiProvider.VALUE, value);

        mContext.getContentResolver().update(updateUri, contentValues, null, null);
    }

    public String getString(final String key, final String defaultValue) {

        String value = defaultValue;

        Uri queryUri = MultiProvider.createQueryUri(mName, key, MultiProvider.CODE_STRING);

        Cursor cursor = mContext.getContentResolver().query(queryUri, null, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            String tempValue = cursor.getString(cursor.getColumnIndexOrThrow(MultiProvider.VALUE));
            if(!tempValue.equals("")){
                value = tempValue;
            }
            cursor.close();
        }

        return value;
    }

    public void setInt(final String key, final int value) {

        Uri updateUri = MultiProvider.createQueryUri(mName, key, MultiProvider.CODE_INTEGER);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MultiProvider.KEY, key);
        contentValues.put(MultiProvider.VALUE, value);

        mContext.getContentResolver().update(updateUri, contentValues, null, null);
    }

    public int getInt(final String key, final int defaultValue) {

        int value = defaultValue;

        Uri queryUri = MultiProvider.createQueryUri(mName, key, MultiProvider.CODE_INTEGER);

        Cursor cursor = mContext.getContentResolver().query(queryUri, null, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            int tempValue = cursor.getInt(cursor.getColumnIndexOrThrow(MultiProvider.VALUE));
            if(tempValue != -1) {
                value = tempValue;
            }
            cursor.close();
        }

        return value;
    }

    public void setLong(final String key, final long value) {

        Uri updateUri = MultiProvider.createQueryUri(mName, key, MultiProvider.CODE_LONG);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MultiProvider.KEY, key);
        contentValues.put(MultiProvider.VALUE, value);

        mContext.getContentResolver().update(updateUri, contentValues, null, null);
    }

    public long getLong(final String key, final long defaultValue) {

        long value = defaultValue;

        Uri queryUri = MultiProvider.createQueryUri(mName, key, MultiProvider.CODE_LONG);

        Cursor cursor = mContext.getContentResolver().query(queryUri, null, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            int tempValue = cursor.getInt(cursor.getColumnIndexOrThrow(MultiProvider.VALUE));
            if(tempValue != -1) {
                value = tempValue;
            }
            cursor.close();
        }

        return value;
    }


    public void setBoolean(final String key, final boolean value) {

        Uri updateUri = MultiProvider.createQueryUri(mName, key, MultiProvider.CODE_BOOLEAN);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MultiProvider.KEY, key);
        contentValues.put(MultiProvider.VALUE, value);

        mContext.getContentResolver().update(updateUri, contentValues, null, null);
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {

        int value = defaultValue ? 1 : 0;

        Uri queryUri = MultiProvider.createQueryUri(mName, key, MultiProvider.CODE_BOOLEAN);

        Cursor cursor = mContext.getContentResolver().query(queryUri, null, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            int tempValue = cursor.getInt(cursor.getColumnIndexOrThrow(MultiProvider.VALUE));
            if(tempValue != value) {
                value = tempValue;
            }
            cursor.close();
        }

        return value == 1;
    }

    public void removePreference(final String key) {

        Uri deleteUri = MultiProvider.createQueryUri(mName, key, MultiProvider.CODE_INTEGER);
        mContext.getContentResolver().delete(deleteUri, null, null);
    }

    public void clearPreferences(){

        Uri clearPrefsUri = MultiProvider.createQueryUri(mName, "", MultiProvider.CODE_PREFS);
        mContext.getContentResolver().delete(clearPrefsUri, null, null);
    }
}
