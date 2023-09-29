package com.example.krishokbondhu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "krishokbondh";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_ID = "keyid";
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private SharedPrefManager(Context context) {
        mCtx = context;
    }
    public SharedPrefManager(){

    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_NAME, user.getName());
        editor.apply();
    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHONE, null) != null;
    }
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt(KEY_ID, -1), sharedPreferences.getString(KEY_PHONE, null), sharedPreferences.getString(KEY_NAME, null), sharedPreferences.getString(KEY_PASSWORD, null));
    }
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, loginpage.class));
    }
    public void update(String s,String s2){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, s);
        editor.putString(KEY_PASSWORD, s2);
        editor.apply();
    }
}