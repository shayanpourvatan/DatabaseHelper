package com.shayan.pourvatan.databasehelper.interfaces;

/**
 * Date: 2017 May 1
 *
 * @author ShayanPourvatan
 */

public interface IExecutor<E> extends DatabaseExecutor<E> {
    void onResponseExecute(E object);

}
