package com.shayan.pourvatan.databasehelper.concurrent;

import android.support.annotation.NonNull;

/**
 * Date: 2017 May 1
 *
 * @author ShayanPourvatan
 */


public abstract class PriorityAwareRunnable implements Runnable, Comparable<PriorityAwareRunnable> {

    @TaskPriority
    private int priority;

    /**
     * Sequence and sequence tracker ensure the PriorityQueue polling to be fair.
     */
    private long sequence;
    private static long sequenceTracker = 0;

    public PriorityAwareRunnable() {
        this(TaskPriority.NORMAL);
    }

    public PriorityAwareRunnable(@TaskPriority int priority) {
        this.priority = priority;
        sequence = sequenceTracker++;
    }

    @Override
    public int compareTo(@NonNull PriorityAwareRunnable other) {

        if (this.priority == other.priority) {
            /*
             * item with lesser sequence has higher priority (fairness)
             */
            return this.sequence < other.sequence ? 1 : -1;
        }
        return this.priority > other.priority ? 1 : -1;
    }

}
