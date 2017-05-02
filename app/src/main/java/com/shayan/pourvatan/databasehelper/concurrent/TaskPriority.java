package com.shayan.pourvatan.databasehelper.concurrent;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Date: 2017 May 1
 *
 * @author ShayanPourvatan
 */

@IntDef({
        TaskPriority.LOW,
        TaskPriority.NORMAL,
        TaskPriority.HIGH,
        TaskPriority.IMMEDIATE,
})
@Retention(RetentionPolicy.SOURCE)
public @interface TaskPriority {
    int LOW = 0;
    int NORMAL = 1;
    int HIGH = 2;
    int IMMEDIATE = 3;
}
