package com.shayan.pourvatan.databasehelper.database;


import android.support.annotation.MainThread;

import com.shayan.pourvatan.databasehelper.interfaces.IExecutor;

/**
 * Date: 2017 May 1
 *
 * @author ShayanPourvatan
 */


public abstract class Executor<T> implements IExecutor<T> {

    @Override
    @MainThread
    public void onResponseExecute(T object) {

    }
}
