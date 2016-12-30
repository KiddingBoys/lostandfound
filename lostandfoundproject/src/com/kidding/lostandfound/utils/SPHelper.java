package com.kidding.lostandfound.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPHelper {
	
	private static SharedPreferences sharedPrefer;
	private static Editor editor;
	private static Context context;
	private static SPHelper spHelper;
	
	public SPHelper(Context context) {
		SPHelper.context = context;
		getSharedPreferences();
		getEditor();
	}
	
	public static SPHelper instance(Context context){
		if(spHelper == null) {
			synchronized(SPHelper.class) {
				if(spHelper == null)
					spHelper = new SPHelper(context.getApplicationContext());
			}
		}
		return spHelper;
	}
	
	public SharedPreferences getSharedPreferences() {
		if(sharedPrefer == null) {
			sharedPrefer = context.getSharedPreferences(MyApplication.LOSTANDFOUND, 0);
		}
		return sharedPrefer;
	}
	
	public Editor getEditor() {
		if(editor == null) {
			editor = context.getSharedPreferences(MyApplication.LOSTANDFOUND, 0).edit();
		}
		return editor;
	}
	
	public void setString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}
	
	public void setInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void setLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}
	
	public String getString(String key) {
		return sharedPrefer.getString(key, "");
	}
	
	public long getLong(String key) {
		return sharedPrefer.getLong(key, 0);
	}
	
	public int getInt(String key) {
		return sharedPrefer.getInt(key, 0);
	}
	

}
