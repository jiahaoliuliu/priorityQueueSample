package com.jiahaoliuliu.priorityqueuesample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.jiahaoliuliu.priorityqueuesample.job.BlockingJob;
import com.jiahaoliuliu.priorityqueuesample.job.PostTweetJob;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String JOB_MANAGER_TAG = "jobManager";
    private JobManager jobManager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        jobManager = new JobManager(configureJobManager());
        jobManager.addJobInBackground(new BlockingJob("1"));
        jobManager.addJobInBackground(new PostTweetJob("Tweet this! 1"));

        jobManager.addJobInBackground(new BlockingJob("2"));
        jobManager.addJobInBackground(new PostTweetJob("Tweet this! 2"));

        jobManager.addJobInBackground(new BlockingJob("3"));
        jobManager.addJobInBackground(new PostTweetJob("Tweet this! 3"));
    }

    private Configuration configureJobManager() {
        return new Configuration.Builder(mContext)
                .customLogger(new CustomLogger() {
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(JOB_MANAGER_TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(JOB_MANAGER_TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(JOB_MANAGER_TAG, String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {
                        Log.v(JOB_MANAGER_TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(2)//always keep at least one consumer alive
                .maxConsumerCount(4)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute.
                .build();
    }
}
