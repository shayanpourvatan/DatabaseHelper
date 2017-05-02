package com.shayan.pourvatan.databasehelper.concurrent;


import android.support.annotation.Nullable;

import com.shayan.pourvatan.databasehelper.interfaces.ErrorHandler;

import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Date: 2017 May 1
 *
 * @author ShayanPourvatan
 */


public class MaxPriorityBlockingQueue<E> extends PriorityBlockingQueue<E> {

    private ErrorHandler errorHandler;

    public MaxPriorityBlockingQueue() {
        this(1);
    }

    public MaxPriorityBlockingQueue(ErrorHandler errorHandler) {
        this(1, errorHandler);
    }

    public MaxPriorityBlockingQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    public MaxPriorityBlockingQueue(int initialCapacity, @Nullable ErrorHandler errorHandler) {
        super(initialCapacity, Collections.<E>reverseOrder());
        this.errorHandler = errorHandler;
    }

    public void putWithoutException(E e) {
        try {
            put(e);
        } catch (Exception x) {
            printError(x);
        }
    }

    public void printError(Throwable t) {
        if (errorHandler != null) {
            errorHandler.onFailure(t);
        }
    }
}
