package com.sankalpchauhan.flipkartgridchallenge.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sankalpchauhan.flipkartgridchallenge.MainActivity;
import com.sankalpchauhan.flipkartgridchallenge.config.Constants;

import timber.log.Timber;

public class CompleteUploadBrodcast extends BroadcastReceiver {
    MainActivity mainActivity;

    public CompleteUploadBrodcast(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Constants.BROADCAST_ACTION_SUCCESS)){
            mainActivity.uploadSuccess(intent.getStringExtra(Constants.IMAGE_URI));
        } else if(intent.getAction().equals(Constants.BROADCAST_ACTION_FAIL)){
            mainActivity.uploadFail();
        }
    }
}