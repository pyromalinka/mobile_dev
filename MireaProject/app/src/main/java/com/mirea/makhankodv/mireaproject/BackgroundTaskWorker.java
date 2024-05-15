package com.mirea.makhankodv.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundTaskWorker extends Worker {

    public BackgroundTaskWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        int a = getInputData().getInt("a", 0);
        int b = getInputData().getInt("b", 0);
        int c = getInputData().getInt("c", 0);

        double discriminant = Math.pow(b, 2) - 4 * a * c;
        String result;

        if (discriminant > 0) {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            result = "Корни: " + root1 + " и " + root2;
        } else if (discriminant == 0) {
            double root = -b / (2 * a);
            result = "Корень: " + root;
        } else {
            result = "Нет корней";
        }

        Log.d("WorkerTest", result);

        Data outputData = new Data.Builder()
                .putString("result", result)
                .build();

        return Result.success(outputData);
    }
}
