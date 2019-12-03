package com.treefrogapps.multipreferences;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Multi Preference provider class
 */
public class MultiProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "com.treefrogapps.multipreferences.MultiProvider";

    /**
     * Define all Content Urls for each type, String, int, long & boolean
     */
    private static final String URL_STRING = "content://" + PROVIDER_NAME + "/string/";
    private static final String URL_INT = "content://" + PROVIDER_NAME + "/integer/";
    private static final String URL_LONG = "content://" + PROVIDER_NAME + "/long/";
    private static final String URL_BOOLEAN = "content://" + PROVIDER_NAME + "/boolean/";
    // Special URL just for clearing preferences
    private static final String URL_PREFERENCES = "content://" + PROVIDER_NAME + "/prefs/";

    static final int CODE_STRING = 1;
    static final int CODE_INTEGER = 2;
    static final int CODE_LONG = 3;
    static final int CODE_BOOLEAN = 4;
    static final int CODE_PREFS = 5;
    static final int CODE_REMOVE_KEY = 6;
    static final String KEY = "key";
    static final String VALUE = "value";

    /**
     * Create UriMatcher to match all requests
     */
    private static final UriMatcher mUriMatcher;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // */* = wildcard  (name or file name / key)
        mUriMatcher.addURI(PROVIDER_NAME, "string/*/*", CODE_STRING);
        mUriMatcher.addURI(PROVIDER_NAME, "integer/*/*", CODE_INTEGER);
        mUriMatcher.addURI(PROVIDER_NAME, "long/*/*", CODE_LONG);
        mUriMatcher.addURI(PROVIDER_NAME, "boolean/*/*", CODE_BOOLEAN);
        mUriMatcher.addURI(PROVIDER_NAME, "prefs/*/", CODE_PREFS);
    }

    /**
     * Map to hold all current Inter actors with shared preferences
     */
    private Map<String, PreferenceInteractor> mPreferenceMap = new HashMap<>();

    @Override public boolean onCreate() {
        return true;
    }

    /**
     * Get a new Preference Interactor, or return a previously used Interactor
     *
     * @param preferenceName the name of the preference file
     * @return a new interactor, or current one in the map
     */
    PreferenceInteractor getPreferenceInteractor(String preferenceName) {
        if (mPreferenceMap.containsKey(preferenceName)) {
            return mPreferenceMap.get(preferenceName);
        } else {
            final PreferenceInteractor interactor = new PreferenceInteractor(getContext(), preferenceName);
            mPreferenceMap.put(preferenceName, interactor);
            return interactor;
        }
    }

    @Nullable @Override public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final PreferenceInteractor interactor = getPreferenceInteractor(uri.getPathSegments().get(1));

        switch (mUriMatcher.match(uri)) {
            case CODE_STRING:
                final String s = uri.getPathSegments().get(2);
                return interactor.hasKey(s) ? preferenceToCursor(interactor.getString(s)) : null;
            case CODE_INTEGER:
                final String i = uri.getPathSegments().get(2);
                return interactor.hasKey(i) ? preferenceToCursor(interactor.getInt(i)) : null;
            case CODE_LONG:
                final String l = uri.getPathSegments().get(2);
                return interactor.hasKey(l) ? preferenceToCursor(interactor.getLong(l)) : null;
            case CODE_BOOLEAN:
                final String b = uri.getPathSegments().get(2);
                return interactor.hasKey(b) ? preferenceToCursor(interactor.getBoolean(b) ? 1 : 0) : null;
        }
        return null;
    }


    @Override public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values != null) {
            final PreferenceInteractor interactor = getPreferenceInteractor(uri.getPathSegments().get(1));
            final String key = values.getAsString(KEY);

            switch (mUriMatcher.match(uri)) {
                case CODE_STRING:
                    final String s = values.getAsString(VALUE);
                    interactor.setString(key, s);
                    break;
                case CODE_INTEGER:
                    final int i = values.getAsInteger(VALUE);
                    interactor.setInt(key, i);
                    break;
                case CODE_LONG:
                    final long l = values.getAsLong(VALUE);
                    interactor.setLong(key, l);
                    break;
                case CODE_BOOLEAN:
                    final boolean b = values.getAsBoolean(VALUE);
                    interactor.setBoolean(key, b);
                    break;
            }

        } else {
            throw new IllegalArgumentException("Content Values are null!");
        }
        return 0;
    }

    @Override public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final PreferenceInteractor interactor = getPreferenceInteractor(uri.getPathSegments().get(1));

        switch (mUriMatcher.match(uri)) {
            case CODE_REMOVE_KEY:
                interactor.removePref(uri.getPathSegments().get(2));
                break;
            case CODE_PREFS:
                interactor.clearPreference();
                break;
            default:
                throw new IllegalStateException(" unsupported uri : " + uri);
        }
        return 0;
    }

    @Nullable @Override public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("not supported");
    }

    @Nullable @Override public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("not supported");
    }

    static String extractStringFromCursor(Cursor cursor, String defaultVal) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(MultiProvider.VALUE));
            }
            cursor.close();
        }
        return defaultVal;
    }

    static int extractIntFromCursor(Cursor cursor, int defaultVal) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(MultiProvider.VALUE));
            }
            cursor.close();
        }
        return defaultVal;
    }

    static long extractLongFromCursor(Cursor cursor, long defaultVal) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndex(MultiProvider.VALUE));
            }
            cursor.close();
        }
        return defaultVal;
    }

    static boolean extractBooleanFromCursor(Cursor cursor, boolean defaultVal) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(MultiProvider.VALUE)) == 1;
            }
            cursor.close();
        }
        return defaultVal;
    }

    static Uri createQueryUri(String prefFileName, String key, int prefType) {
        switch (prefType) {
            case CODE_STRING:
                return Uri.parse(URL_STRING + prefFileName + "/" + key);
            case CODE_INTEGER:
                return Uri.parse(URL_INT + prefFileName + "/" + key);
            case CODE_LONG:
                return Uri.parse(URL_LONG + prefFileName + "/" + key);
            case CODE_BOOLEAN:
                return Uri.parse(URL_BOOLEAN + prefFileName + "/" + key);
            case CODE_PREFS:
                return Uri.parse(URL_PREFERENCES + prefFileName + "/" + key);
            default:
                throw new IllegalArgumentException("Not Supported Type : " + prefType);
        }
    }

    static <T> ContentValues createContentValues(String key, T value) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(MultiProvider.KEY, key);

        if (value instanceof String) {
            contentValues.put(MultiProvider.VALUE, (String) value);
        } else if (value instanceof Integer) {
            contentValues.put(MultiProvider.VALUE, (Integer) value);
        } else if (value instanceof Long) {
            contentValues.put(MultiProvider.VALUE, (Long) value);
        } else if (value instanceof Boolean) {
            contentValues.put(MultiProvider.VALUE, (Boolean) value);
        } else {
            throw new IllegalArgumentException("Unsupported type " + value.getClass());
        }
        return contentValues;
    }

    @Nullable static Cursor performQuery(Uri uri, ContentResolver resolver) {
        return resolver.query(uri, null, null, null, null, null);
    }

    /**
     * Convert a value into a cursor object using a Matrix Cursor
     *
     * @param value the value to be converetd
     * @param <T>   generic object type
     * @return a Cursor object
     */
    private <T> MatrixCursor preferenceToCursor(T value) {
        final MatrixCursor matrixCursor = new MatrixCursor(new String[]{MultiProvider.VALUE}, 1);
        final MatrixCursor.RowBuilder builder = matrixCursor.newRow();
        builder.add(value);
        return matrixCursor;
    }
}
