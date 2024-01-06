package com.devhp.workmanagerjavasample;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DemoWorker extends Worker {
    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data  = getInputData();
        int countLimit = data.getInt(MainActivity.KEY_INPUT_DATA, 0);
        for(int i = 0; i<countLimit;i++){
            Log.i("MyTag", "Thread: " + Thread.currentThread().getName() + " - Count is " + i );
        }

        Data dataToSend = new Data.Builder().putString(MainActivity.KEY_OUTPUT_DATA, "Task Done Successfully").build();

        return Result.success(dataToSend);
    }
}
