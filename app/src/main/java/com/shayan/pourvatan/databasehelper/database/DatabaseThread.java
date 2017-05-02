package com.shayan.pourvatan.databasehelper.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.shayan.pourvatan.databasehelper.concurrent.MaxPriorityBlockingQueue;
import com.shayan.pourvatan.databasehelper.concurrent.PriorityAwareRunnable;
import com.shayan.pourvatan.databasehelper.interfaces.ErrorHandler;
import com.shayan.pourvatan.databasehelper.interfaces.Initializer;


/**
 * Date: 2017 May 1
 *
 * @author ShayanPourvatan
 */


public class DatabaseThread extends Thread {

    //region properties

    private MaxPriorityBlockingQueue<PriorityAwareRunnable> databaseQueue;
    private Initializer initializer;

    //endregion

    // region constructor
    public DatabaseThread() {
        this(null, null);
    }

    public DatabaseThread(@NonNull Initializer initializer) {
        this(null, initializer);
    }

    public DatabaseThread(@NonNull ErrorHandler errorHandler) {
        this(errorHandler, null);
    }

    public DatabaseThread(@Nullable ErrorHandler errorHandler,@Nullable Initializer initializer) {
        super();
        initDatabaseThread(errorHandler, initializer);
    }
    //endregion

    //region initializer
    private void initDatabaseThread(@Nullable ErrorHandler errorHandler,@Nullable Initializer initializer) {
        this.databaseQueue = new MaxPriorityBlockingQueue<>(errorHandler);
        this.initializer = initializer;

        DatabaseManager.getInstance().initDatabaseQueue(databaseQueue);
    }
    //endregion

    @Override
    public void run() {
        super.run();

        if (initializer != null) {
            initializer.init();
        }

        while (true) {
            try {
                Runnable runnable = databaseQueue.take();
                runnable.run();

            } catch (Exception e) {
                databaseQueue.printError(e);
            }
        }
    }
}
