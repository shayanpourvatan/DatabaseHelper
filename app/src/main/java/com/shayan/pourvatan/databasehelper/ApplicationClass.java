package com.shayan.pourvatan.databasehelper;

import android.app.Application;
import android.util.Log;

import com.shayan.pourvatan.databasehelper.database.DatabaseThread;
import com.shayan.pourvatan.databasehelper.interfaces.ErrorHandler;
import com.shayan.pourvatan.databasehelper.interfaces.Initializer;

/**
 * Created by shayanpourvatan on 5/2/17.
 */

public class ApplicationClass extends Application {

    private final String TAG = "ApplicationClass";

    @Override
    public void onCreate() {
        super.onCreate();


        ErrorHandler errorHandler = new ErrorHandler() {
            @Override
            public void onFailure(Throwable e) {
                Log.d(TAG, "Error happened ", e);
            }
        };


        Initializer initializer = new Initializer() {
            @Override
            public void init() {
                // do whatever you need in database thread while creation,
                // sometimes you need init some properties in database thread, like Realm library,
                // in realm you can query where you create instance of your Realm, so you can create that in this part.
                Log.d(TAG, "initCalled");
            }
        };


        // you can don't pass parameter if you don't need those.
        // you need start your thread only ONCE.
        new DatabaseThread(errorHandler, initializer).start();

    }
}
