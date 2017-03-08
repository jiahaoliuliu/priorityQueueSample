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

public class PostTweetJob extends Job {

    private static final String TAG = "PostTweetJob";

    public static final int PRIORITY = 1;
    private String text;

    public PostTweetJob(String text) {
        super(new Params(PRIORITY));
        this.text = text;
    }

    @Override
    public void onAdded() {
        // Job has been saved  to disk
    }

    @Override
    public void onRun() throws Throwable {
        // Running the thread
        Log.v(TAG, "Trying to post the text to twitter " + text);
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
