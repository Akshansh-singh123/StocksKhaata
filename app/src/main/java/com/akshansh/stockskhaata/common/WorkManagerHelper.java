package com.akshansh.stockskhaata.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class WorkManagerHelper {
    private final AppCompatActivity activity;

    @Inject
    public WorkManagerHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    private Constraints createConstraints(){
        return new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(false)
                .build();
    }

    private PeriodicWorkRequest createWorkRequest(){
        return new PeriodicWorkRequest.Builder(NotifyWorker.class,1, TimeUnit.DAYS)
                .setConstraints(createConstraints()).build();
    }

    public void startWork(){
        WorkManager.getInstance(activity)
                .enqueueUniquePeriodicWork("StocksKhaata Notify Work",
                        ExistingPeriodicWorkPolicy.KEEP,
                        createWorkRequest());
    }
}
