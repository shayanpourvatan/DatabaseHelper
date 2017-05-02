package com.shayan.pourvatan.databasehelper;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shayan.pourvatan.databasehelper.concurrent.TaskPriority;
import com.shayan.pourvatan.databasehelper.database.DatabaseManager;
import com.shayan.pourvatan.databasehelper.database.DatabaseThread;
import com.shayan.pourvatan.databasehelper.database.Executor;
import com.shayan.pourvatan.databasehelper.interfaces.ErrorHandler;
import com.shayan.pourvatan.databasehelper.interfaces.Initializer;


/**
 * Date: 2017 May 1
 *
 * @author ShayanPourvatan
 */

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        // execute simple database query
        DatabaseManager.getInstance().execute(new Executor<Void>() {
            @Override
            public Void doInDatabaseThread() {
                Log.d(TAG, "simple database thread work0");
                return null;
            }
        });

        // execute database query and return  value in UI thread
        DatabaseManager.getInstance().execute(true, new Executor<String>() {
            @Override
            public String doInDatabaseThread() {
                Log.d(TAG, "simple database thread work1");
                return null;
            }

            @Override
            public void onResponseExecute(String string) {
                super.onResponseExecute(string);
                Log.d(TAG, "return work1 thread is mainThread ? " + (Looper.getMainLooper() == Looper.myLooper()));
                // this method will call in UI thread as you set true.
            }
        });

        // execute database query and return  value in UI thread with highest priority.
        DatabaseManager.getInstance().execute(TaskPriority.IMMEDIATE, false, new Executor<String>() {
            @Override
            public String doInDatabaseThread() {
                Log.d(TAG, "simple database thread work2");
                return null;
            }

            @Override
            public void onResponseExecute(String string) {
                super.onResponseExecute(string);
                // this method will call in back thread as you set responseOnBackThread to false.
                Log.d(TAG, "return work2 thread is mainThread ? " + (Looper.getMainLooper() == Looper.myLooper()));
            }
        });
    }
}
