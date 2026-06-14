package com.example.chronofocus.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsManager {
    //uma única thread é o suficiente para I/O
    private  static ExecutorService executorThreads = Executors.newSingleThreadExecutor();;


    private ThreadsManager() {};
    public static void startTask(Runnable runTask){
        executorThreads.execute(runTask);
    }

}
