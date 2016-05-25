package com.example.sck.androidintership_task1.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * This class helps to store class object in the shared preferences
 *
 */
public class SharedComplexPrefs {
	public static final String COMPLEX_PREFS_NAME = "complex_preferences";
	private SharedPreferences mSharedPrefs;
	private SharedPreferences.Editor mEditor;
	private static Gson sGson = new Gson();

	private SharedComplexPrefs(Context context, String namePreferences, int mode) {
		if (namePreferences == null || namePreferences.equals("")) {
			namePreferences = COMPLEX_PREFS_NAME;
		}
		mSharedPrefs = context.getSharedPreferences(namePreferences, mode);
		mEditor = mSharedPrefs.edit();
	}

	public static SharedComplexPrefs getComplexPrefs(Context context, String namePreferences, int mode) {
		return new SharedComplexPrefs(context, namePreferences, mode);
	}

	public void putObject(String key, Object object) {
		if(object != null || !key.equals("")) {
			mEditor.putString(key, sGson.toJson(object));
		}
	}

	public void commit() {
		mEditor.commit();
	}

    public void clearObject() {
        mEditor.clear();
    }

	public <T> T getObject(String key, Class<T> tClass) {
		String gson = mSharedPrefs.getString(key, null);
		if (gson != null) {
			return sGson.fromJson(gson, tClass);
		} else {
			return null;
		}
	}
}