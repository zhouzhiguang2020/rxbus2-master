package com.threshold.rxbus3.util;


import com.threshold.rxbus3.BaseBus;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Thread for listen event.
 */

public enum EventThread {
    /**
     * Main Thread(UI Thread)
     */
    MAIN,
    /**
     * New Thread
     */
    NEW,
    /**
     * Read/Write Thread
     */
    IO,
    /**
     * Computation thread. No io block.
     */
    COMPUTATION,
    /**
     * Running at current thread by sequence
     */
    TRAMPOLINE,
    /**
     *
     */
    SINGLE;


    private static Scheduler mainThreadScheduler;

    /**
     * Set {@link #MAIN} {@link Scheduler} in your current environment.<br>
     * For example in Android,you probably set @{code AndroidSchedulers.mainThread()}.
     * <p>
     *     Note: {@link BaseBus#setMainScheduler(Scheduler)}  is a handy method for this.
     * </p>
     * @param scheduler {@link #MAIN} {@link Scheduler}
     */
    public static void setMainThreadScheduler(Scheduler scheduler) {
        mainThreadScheduler = scheduler;
    }

    /**
     * This factory method produce {@link Scheduler} for use.
     * <p>
     *     Please be careful if you use  {@link EventThread#MAIN} .
     *     You should provide your runtime environment main thread(UI thread) before use it.
     * </p>
     * @param threadMode {@link EventThread} type
     * @return {@link Scheduler}
     */
    public static Scheduler getScheduler(EventThread threadMode){
        Scheduler scheduler;
        switch (threadMode){
            default:
            case MAIN:
                if (mainThreadScheduler == null) {
                    throw new IllegalStateException("pass the main thread scheduler for your current runtime environment before use");
                }
                scheduler = mainThreadScheduler;
                break;
            case NEW:scheduler= Schedulers.newThread();break;
            case IO:scheduler=Schedulers.io();break;
            case COMPUTATION:scheduler=Schedulers.computation();break;
            case TRAMPOLINE:scheduler=Schedulers.trampoline();break;
            case SINGLE:scheduler=Schedulers.single();
        }
        return scheduler;
    }
}
