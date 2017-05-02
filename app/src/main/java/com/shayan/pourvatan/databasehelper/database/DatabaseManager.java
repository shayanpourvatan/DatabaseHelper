package com.shayan.pourvatan.databasehelper.database;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.shayan.pourvatan.databasehelper.concurrent.MaxPriorityBlockingQueue;
import com.shayan.pourvatan.databasehelper.concurrent.PriorityAwareRunnable;
import com.shayan.pourvatan.databasehelper.concurrent.TaskPriority;


/**
 * Date: 2017 May 1
 *
 * @author ShayanPourvatan
 */

public class DatabaseManager {

    ///////////////////////////////  properties  /////////////////////////////////////
    //region properties
    private static DatabaseManager databaseManager;
    private Handler uiHandler;
    private Handler backThreadHandler;
    private MaxPriorityBlockingQueue<PriorityAwareRunnable> databaseQueue = null;
    //endregion

    ///////////////////////////////   singleton  /////////////////////////////////////
    //region singleton

    public static DatabaseManager getInstance() {

        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = new DatabaseManager();
                }
            }
        }
        return databaseManager;
    }

    // prevent create instance from outSide
    private DatabaseManager() {
        uiHandler = new Handler(Looper.getMainLooper());


        HandlerThread handlerThread = new HandlerThread("databaseHandlerBackThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        backThreadHandler = new Handler(looper);
    }

    //endregion

    ///////////////////////////////  initializer ////////////////////////////////////
    //region initializer

    void initDatabaseQueue(MaxPriorityBlockingQueue<PriorityAwareRunnable> databaseQueue) {

        if (this.databaseQueue != null) {
            throw new AssertionError("You start databaseThread twice, or manually call this method, DON'T do both, start it in Application Class.");
        }

        this.databaseQueue = databaseQueue;

    }

    //endregion

    ///////////////////////////////   executor   ////////////////////////////////////
    //region executor

    public void execute(final Executor executor) {
        execute(TaskPriority.NORMAL, executor);
    }

    public void execute(@TaskPriority int taskPriority, final Executor executor) {
        execute(taskPriority, false, executor);
    }

    public void execute(final boolean responseOnBackThread, final Executor executor) {
        execute(TaskPriority.NORMAL, responseOnBackThread, executor);
    }

    @SuppressWarnings("unchecked")
    public void execute(@TaskPriority int taskPriority, final boolean responseOnBackThread, final Executor executor) {

        PriorityAwareRunnable executorRunnable = new PriorityAwareRunnable(taskPriority) {
            @Override
            public void run() {

                final Object returnVal = executor.doInDatabaseThread();

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        executor.onResponseExecute(returnVal);
                    }
                };

                if (responseOnBackThread) {
                    backThreadHandler.post(runnable);
                } else {
                    uiHandler.post(runnable);
                }
            }
        };

        databaseQueue.putWithoutException(executorRunnable);
    }
    //endregion

}
