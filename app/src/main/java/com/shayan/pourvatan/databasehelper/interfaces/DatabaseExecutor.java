package com.shayan.pourvatan.databasehelper.interfaces;

/**
 * Date: 2017 May 1
 *
 * @author ShayanPourvatan
 */

public interface DatabaseExecutor<E> {
    E doInDatabaseThread();
}
