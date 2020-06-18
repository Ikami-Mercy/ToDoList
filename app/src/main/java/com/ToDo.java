package com;

import android.app.Application;
import android.content.Context;

import com.myToDoList.BuildConfig;

public class ToDo extends Application {
    private static ToDo mInstance;
    private Context context;

    public static ToDo getInstance(){
        if (mInstance == null)
            mInstance = new ToDo();

        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){

            this.mInstance = this;
        }
        this.context = this;

    }
}
