package com.mirea.makhankodv.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;

public class BackgroundTaskWorker extends Worker {

    public BackgroundTaskWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        simulateDataLoading();
        return Result.success();
    }

    private void simulateDataLoading() {
        String[] names = {"Alice", "Bob", "Charlie", "David", "Eva", "Fiona", "George", "Hannah", "Ivan", "Julia"};
        Random random = new Random();
        int index = random.nextInt(names.length);

        Log.d("WorkerTest", "Загружено имя: " + names[index]);
    }
}