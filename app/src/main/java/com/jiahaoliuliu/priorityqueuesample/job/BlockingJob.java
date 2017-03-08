package com.jiahaoliuliu.priorityqueuesample.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

/**
 * Created by jiahaoliu on 3/8/17.
 */

public class BlockingJob extends Job {

    private static final String TAG = "BlockingJob";
    private static final String GROUP_ID = "BlockingGroup";

    public static final int PRIORITY = 1;
    private String id;

    public BlockingJob(String id) {
        super(new Params(PRIORITY).requireNetwork().persist().setGroupId(GROUP_ID));
        this.id = id;
    }

    @Override
    public void onAdded() {
        // Job has been saved  to disk
        Log.v(TAG, "Job added " + id);
    }

    @Override
    public void onRun() throws Throwable {
        // Running the thread
        Log.v(TAG, "Blocking the thread ... " + id);
        // Sleep for 5 seconds
        Thread.sleep(20*1000);
        Log.v(TAG, "Releasing the block " + id);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        // Job has exceeded retry attempts or shouldReRunOnThrowable() has decided to cancel
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return RetryConstraint.createExponentialBackoff(runCount, 1000);
    }
}
