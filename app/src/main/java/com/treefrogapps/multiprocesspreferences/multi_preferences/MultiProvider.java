package com.treefrogapps.multiprocesspreferences.multi_preferences;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Multi Preference provider class
 */
public class MultiProvider extends ContentProvider {

    private static final String PROVIDER_NAME =
            "com.treefrogapps.multiprocesspreferences.multi_preferences.MultiProvider";

    /**
     * Define all Content Urls for each type, String, int, long & boolean
     */
    protected static final String URL_STRING = "content://" + PROVIDER_NAME + "/string/";
    protected static final String URL_INT = "content://" + PROVIDER_NAME + "/integer/";
    protected static final String URL_LONG = "content://" + PROVIDER_NAME + "/long/";
    protected static final String URL_BOOLEAN = "content://" + PROVIDER_NAME + "/boolean/";

    protected static final int CODE_STRING = 1;
    protected static final int CODE_INTEGER = 2;
    protected static final int CODE_LONG = 3;
    protected static final int CODE_BOOLEAN = 4;

    /**
     * Create UriMatcher to match all requests
     */
    private static final UriMatcher mUriMatcher;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // */* = wildcard  (key/value)
        mUriMatcher.addURI(PROVIDER_NAME, "string/*/*", CODE_STRING);
        mUriMatcher.addURI(PROVIDER_NAME, "integer/*/*", CODE_INTEGER);
        mUriMatcher.addURI(PROVIDER_NAME, "long/*/*", CODE_LONG);
        mUriMatcher.addURI(PROVIDER_NAME, "boolean/*/*", CODE_BOOLEAN);
    }

    protected static final String KEY = "key";
    protected static final String VALUE = "value";

    /**
     * Map to hold all current Inter actors with shared preferences
     */
    private Map<String, PreferenceInteractor> mPreferenceMap = new HashMap<>();

    @Override
    public boolean onCreate() {
        return true;
    }

    public static Uri createQueryUri(String prefFileName, String key, int prefType) {

        switch (prefType) {

            case CODE_STRING:
                return Uri.parse(URL_STRING + prefFileName + "/" + key);
            case CODE_INTEGER:
                return Uri.parse(URL_INT + prefFileName + "/" + key);
            case CODE_LONG:
                return Uri.parse(URL_LONG + prefFileName + "/" + key);
            case CODE_BOOLEAN:
                return Uri.parse(URL_BOOLEAN + prefFileName + "/" + key);

            default:
                throw new IllegalStateException("Not Supported Type : " + prefType);
        }
    }

    /**
     * Get a new Preference Interactor, or return a previously used Interactor
     * @param preferenceName the name of the preference file
     * @return a new interactor, or current one in the map
     */
    public PreferenceInteractor getPreferenceInteractor(String preferenceName) {

        if (mPreferenceMap.containsKey(preferenceName)) {

            return mPreferenceMap.get(preferenceName);
        } else {
            PreferenceInteractor interactor = new PreferenceInteractor(getContext(), preferenceName);
            mPreferenceMap.put(preferenceName, interactor);
            return interactor;

        }
    }

    /**
     * Convert a value into a cursor object using a Matrix Cursor
     * @param value the value to be converetd
     * @param <T> generic object type
     * @return a Cursor object
     */
    private <T> MatrixCursor preferenceToCursor(T value) {

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{MultiProvider.VALUE}, 1);
        MatrixCursor.RowBuilder builder = matrixCursor.newRow();
        builder.add(value);
        return matrixCursor;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        /**
         * Create a new Preference Interactor class based on the Preference File Name, of return the existing one
         * from the map  - Preference (File) name comes form the Uri segment 2
         */
        PreferenceInteractor interactor = getPreferenceInteractor(uri.getPathSegments().get(2));

        switch (mUriMatcher.match(uri)) {

            case CODE_STRING:
                return preferenceToCursor(interactor.getString(uri.getPathSegments().get(2), ""));

            case CODE_INTEGER:
                return preferenceToCursor(interactor.getInt(uri.getPathSegments().get(2), -1));

            case CODE_LONG:
                return preferenceToCursor(interactor.getLong(uri.getPathSegments().get(2), -1));

            case CODE_BOOLEAN:
                return preferenceToCursor(interactor.getBoolean(uri.getPathSegments().get(2), false) ? 1 : 0);

        }

        return null;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values != null) {

            /**
             * Create a new Preference Interactor class based on the Preference File Name, of return the existing one
             * from the map  - Preference (File) name comes form the Uri segment 2
             */

            for(String item : uri.getPathSegments()){
                Log.e("URI ", item);
            }

            PreferenceInteractor interactor = getPreferenceInteractor(uri.getPathSegments().get(2));

            final String key = values.getAsString(KEY);

            switch (mUriMatcher.match(uri)) {

                case CODE_STRING:
                    String sValue = values.getAsString(VALUE);
                    interactor.setString(key, sValue);
                    break;

                case CODE_INTEGER:
                    int iValue = values.getAsInteger(VALUE);
                    interactor.setInt(key, iValue);
                    break;

                case CODE_LONG:
                    long lValue = values.getAsLong(VALUE);
                    interactor.setLong(key, lValue);
                    break;

                case CODE_BOOLEAN:
                    boolean bValue = values.getAsBoolean(VALUE);
                    interactor.setBoolean(key, bValue);
                    break;
            }

        } else {
            throw new IllegalArgumentException(" Content Values are null!");
        }
        return 0;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        switch (mUriMatcher.match(uri)) {
            case CODE_STRING:
            case CODE_INTEGER:
            case CODE_LONG:
            case CODE_BOOLEAN:
                /**
                 * Create a new Preference Interactor class based on the Preference File Name, of return the existing one
                 * from the map  - Preference (File) name comes form the Uri segment 1
                 */
                PreferenceInteractor interactor = getPreferenceInteractor(uri.getPathSegments().get(1));
                interactor.removePref(uri.getPathSegments().get(2));
                break;
            default:
                throw new IllegalStateException(" unsupported uri : " + uri);
        }
        return 0;
    }




    /**
     *  NOT SUPPORTED
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        return null;
    }




}
